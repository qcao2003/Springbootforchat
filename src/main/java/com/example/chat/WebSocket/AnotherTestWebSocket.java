package com.example.chat.WebSocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * 客户端访问该链接即可和服务端建立连接
 */
@ServerEndpoint("/login_connect/{userId}")
@Component
public class AnotherTestWebSocket {

    //使用session向连接的用户发送信息
    private Session session;
    //记录连接到此socke的用户id
    private String userId = "";

    // 用来存放所有的连接，静态
    private static ConcurrentHashMap<String, AnotherTestWebSocket> webSocketMap = new ConcurrentHashMap<>();

    //用户连接成功后会自动调用该方法
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws
            IOException {
        this.session = session;
        this.userId = userId;
        System.out.println("连接成功，用户ID为：" + userId);
        //连接成功后向连接的用户返回 “连接成功” 信息
        this.session.getBasicRemote().sendText("连接成功");
        // 然后将该连接存入ChatController
        webSocketMap.put(this.userId, this);

    }
    //关闭连接后自动调用
    @OnClose
    public void onClose() {
        System.out.println("close connect");
    }
    // 收到用户发来的消息时调用
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println(message);
        JSONObject json = JSON.parseObject(message);
        System.out.println(json.getString("receiveuserid") + "||" + json.getString("message"));
        webSocketMap.get(json.getString("receiveuserid"))
                .sendmessage("用户:" + this.userId + "说:" + json.getString("message"));
    }
    //错误时调用
    @OnError
    public void onError(Session session, Throwable error) {
    }

    // 发送消息给此客户端
    public void sendmessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}

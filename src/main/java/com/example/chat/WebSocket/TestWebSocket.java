package com.example.chat.WebSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.util.concurrent.ConcurrentHashMap;

@Component/*组件*/
/* 主要是将目前的类定义成一个websocket服务器端 */
@ServerEndpoint("/test")
public class TestWebSocket {

    private Session session;

    private String userId;

    // 用来存放所有的连接，静态
    private static ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>();
    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        this.userId = session.getId();
        webSocketMap.put(this.userId, session);
        System.out.println("用户"+this.userId+ "WebSocket 连接已经建立");
//        HashMap里面的value，返回的是集合视图
        for (Session session_for_loop : webSocketMap.values()) {
            session_for_loop.getAsyncRemote().sendText("用户"+this.userId+"已经上线了");
        }
//        session.getAsyncRemote().sendText("用户"+this.userId+ "WebSocket 连接已经建立");
    }
    //关闭连接后自动调用
    @OnClose
    public void onClose() {
        webSocketMap.remove(this.userId);
        System.out.println("close connect");
    }
    // 收到用户发来的消息时调用
    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        System.out.println("用户"+this.userId+"发送的信息是"+message);
        for (Session session_for_loop : webSocketMap.values()) {
            session_for_loop.getAsyncRemote().sendText("用户"+this.userId+"发送的信息是"+message);
        }
//        session.getAsyncRemote().sendText("用户"+this.userId+"发送的信息是"+message);
    }
    //错误时调用
    @OnError
    public void onError(Session session, Throwable error) {
    }

}

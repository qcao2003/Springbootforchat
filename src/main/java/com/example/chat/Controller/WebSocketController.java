package com.example.chat.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class WebSocketController {

    @RequestMapping("/websocket/{name}")
    public String webSocket(@PathVariable String name, Model model) {
        try {
            log.info("跳转到websocket的页面上");
            /* 把name传入到username中 */
            model.addAttribute("username", name);
            /* 返回信息到websocket网页中 */
            return "websocket";
        } catch (Exception e) {
            log.info("跳转到websocket的页面上发生异常，异常信息是：" + e.getMessage());
            return "error";
        }
    }

    @RequestMapping("/test")
    public String testwebSocket() {
        try {
            log.info("跳转到websocket的页面上");
            return "JS_test";
        } catch (Exception e) {
            log.info("跳转到websocket的页面上发生异常，异常信息是：" + e.getMessage());
            return "error";
        }
    }

    @RequestMapping("/login_connect/{userId}")
    public String AnotherwebSocket() {
        try {
            log.info("跳转到websocket的页面上");

            return "websocket_test";
        } catch (Exception e) {
            log.info("跳转到websocket的页面上发生异常，异常信息是：" + e.getMessage());
            return "error";
        }
    }
}

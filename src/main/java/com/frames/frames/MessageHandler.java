package com.frames.frames;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageHandler {
    @MessageMapping("/hello")
    @SendTo("/topic/response")
    public Response response(Message message) {
        //Thread.sleep(1000); // simulated delay
        return new Response("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
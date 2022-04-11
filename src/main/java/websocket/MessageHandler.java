package websocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class MessageHandler {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/response")
    public Response response(Message message) {
        //Thread.sleep(1000); // simulated delay
        System.out.println("pinged: " + message.getName() + "!");
        return new Response("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/client")
    public void clientMessage(@Payload String message, Principal user,
                                @Header("simpSessionId") String sessionId) {
        System.out.println("In /client: " + sessionId);
        simpMessagingTemplate.convertAndSendToUser(sessionId, "/user/queue/bep",
            "Client-specific reply!");
    }
}
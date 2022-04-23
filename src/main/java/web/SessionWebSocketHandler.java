package web;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import web.message.Initial;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {
  private final SessionManager sessionManager;

  public SessionWebSocketHandler(SessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    this.sessionManager.addSession(session);
    this.sessionManager.sendMessage(session.getId(), new Initial(session.getId()));
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws Exception {

  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    System.out.println("Transport error: " + exception.getMessage());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
      throws Exception {
    this.sessionManager.endSession(session.getId(), closeStatus);
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }

  @Override
  public List<String> getSubProtocols() {
    return Collections.singletonList("subprotocol.demo.websocket");
  }
}

package web.config;

import session.UserSession;
import util.Data;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import web.message.IncomingMessage;
import web.message.common.ErrorMessage;
import web.message.common.SessionCreationMessage;
import session.SessionManager;

public class SessionWebSocketHandler extends TextWebSocketHandler {
  private final SessionManager sessionManager;

  public SessionWebSocketHandler() {
    this.sessionManager = SessionManager.getInstance();
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    UserSession userSession = this.sessionManager.createUserSession(session);
    userSession.sendMessage(new SessionCreationMessage(session.getId()));
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws Exception {
    UserSession userSession = this.sessionManager.getSession(session.getId());
    try {
      IncomingMessage incomingMessage = Data.deserialize(message.getPayload(), IncomingMessage.class);
      incomingMessage.setSender(userSession);
      userSession.acceptMessage(incomingMessage);
    } catch (Exception e) {
      userSession.sendMessage(new ErrorMessage(e.getMessage()));
    }
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    System.out.println("Transport error: " + exception.getMessage());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
      throws Exception {
    UserSession userSession = this.sessionManager.getSession(session.getId());
    userSession.putInRoom(null);
    userSession.close();
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }
/*
  @Override
  public List<String> getSubProtocols() {
    return Collections.singletonList("subprotocol.demo.websocket");
  }*/
}

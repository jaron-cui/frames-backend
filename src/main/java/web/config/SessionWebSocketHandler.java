package web.config;

import util.Data;
import web.data.GameHandler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import web.message.incoming.IncomingMessage;
import web.message.outgoing.SessionCreationMessage;
import web.controller.GameController;
import web.service.SessionManager;

public class SessionWebSocketHandler extends TextWebSocketHandler {
  private final SessionManager sessionManager;

  public SessionWebSocketHandler(SessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    this.sessionManager.addSession(session);
    this.sessionManager.sendMessage(session.getId(), new SessionCreationMessage(session.getId()));
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws Exception {
    GameHandler gameHandler = GameController.sessionToGameHandler.get(session.getId());
    gameHandler.acceptMessage(session.getId(), Data.deserialize(new String(message.asBytes()),
        IncomingMessage.class));
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    System.out.println("Transport error: " + exception.getMessage());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
      throws Exception {
    GameHandler gameHandler = GameController.sessionToGameHandler.get(session.getId());
    gameHandler.acceptMessage(session.getId(), new IncomingMessage(IncomingMessage.Type.QUIT));
    this.sessionManager.endSession(session.getId(), closeStatus);
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

package web;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import util.Data;
import web.data.Lobby;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {
  private static final Map<String, WebSocketSession> sessions = new HashMap<>();

  public SessionManager() {
  }

  public WebSocketSession getSession(String sessionId) {
    return sessions.get(sessionId);
  }

  public void addSession(WebSocketSession session) {
    sessions.put(session.getId(), session);
  }

  public void endSession(String sessionId, CloseStatus closeStatus) {
    try {
      sessions.get(sessionId).close(closeStatus);
    } catch (IOException ignored) {

    }
    sessions.remove(sessionId);
  }

  public boolean isActiveSession(String sessionId) {
    return sessions.containsKey(sessionId) && sessions.get(sessionId).isOpen();
  }

  public void sendMessage(String sessionId, Object message) {
    try {
      sessions.get(sessionId).sendMessage(new TextMessage(Data.serialize(message)));
    } catch (IOException e) {
      throw new RuntimeException("Could not send message.");
    }
  }
}

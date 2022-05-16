package session;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
  private static final SessionManager singleton = new SessionManager();
  private final Map<String, UserSession> sessions;

  private SessionManager() {
    this.sessions = new HashMap<>();
  }

  protected void removeSession(UserSession session) {
    this.sessions.remove(session.getId());
  }

  public UserSession createUserSession(WebSocketSession session) {
    UserSession userSession = new UserSession(session);
    this.sessions.put(session.getId(), userSession);
    return userSession;
  }

  public UserSession getSession(String sessionId) {
    return this.sessions.get(sessionId);
  }

  public static SessionManager getInstance() {
    return singleton;
  }
}

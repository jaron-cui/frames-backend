package web.message;

public class SessionCreationMessage extends WebSocketMessage {
  private final String sessionId;

  public SessionCreationMessage(String sessionId) {
    super("initial");
    this.sessionId = sessionId;
  }

  public String getSessionId() {
    return this.sessionId;
  }
}

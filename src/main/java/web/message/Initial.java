package web.message;

public class Initial extends WebSocketMessage {
  private final String sessionId;

  public Initial(String sessionId) {
    super("initial");
    this.sessionId = sessionId;
  }

  public String getSessionId() {
    return this.sessionId;
  }
}

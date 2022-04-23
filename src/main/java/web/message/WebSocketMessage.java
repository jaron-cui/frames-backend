package web.message;

public abstract class WebSocketMessage {
  private final String type;

  public WebSocketMessage(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }
}

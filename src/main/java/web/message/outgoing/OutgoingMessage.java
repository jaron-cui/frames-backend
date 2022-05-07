package web.message.outgoing;

public abstract class OutgoingMessage {
  private final String type;

  public OutgoingMessage(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }
}

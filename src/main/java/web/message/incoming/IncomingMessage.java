package web.message.incoming;

public class IncomingMessage {
  public enum Type {
    START, QUIT, MOVE
  }

  private Type type;
  private String content;

  public IncomingMessage() {

  }

  public IncomingMessage(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}

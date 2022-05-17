package web.message.common;

public class ErrorMessage extends NoticeMessage {
  public ErrorMessage(String message) {
    super("error", message);
  }
}

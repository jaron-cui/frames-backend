package web.message.common;

import web.message.OutgoingMessage;

public class NoticeMessage extends OutgoingMessage {
  private final String message;

  public NoticeMessage(String message) {
    this("notice", message);
  }

  protected NoticeMessage(String type, String message) {
    super(type);
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}

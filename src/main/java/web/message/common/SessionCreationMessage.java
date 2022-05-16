package web.message.common;

import web.message.OutgoingMessage;

public class SessionCreationMessage extends OutgoingMessage {
  private final String sessionId;

  public SessionCreationMessage(String sessionId) {
    super("initial");
    this.sessionId = sessionId;
  }

  public String getSessionId() {
    return this.sessionId;
  }
}

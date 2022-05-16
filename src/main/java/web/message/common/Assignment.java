package web.message.common;

import session.Room;
import web.message.OutgoingMessage;

import java.util.Map;

public class Assignment extends OutgoingMessage {
  private final Map<String, Object> room;

  public Assignment(Room lobby) {
    super("assignment");
    this.room = lobby == null ? null : lobby.retrieveFields();
  }

  public Map<String, Object> getRoom() {
    return this.room;
  }
}

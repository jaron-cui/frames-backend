package web.message.common;

import session.UserSession;
import web.message.OutgoingMessage;

public abstract class PlayerStatusMessage extends OutgoingMessage {
  private final String player;

  public PlayerStatusMessage(String type, UserSession player) {
    super(type);
    this.player = player.getId();
  }

  public String getPlayer() {
    return this.player;
  }
}

package web.message.common;

import session.UserSession;
import web.message.OutgoingMessage;

public class ReadyMessage extends OutgoingMessage {
  private final String player;

  public ReadyMessage(UserSession player) {
    super("ready");
    this.player = player.getId();
  }

  public String getPlayer() {
    return this.player;
  }
}

package web.message.common;

import session.UserSession;
import web.message.OutgoingMessage;

public class PlayerTurnMessage extends OutgoingMessage {
  private final String playerId;

  public PlayerTurnMessage(UserSession player) {
    super("turn");
    this.playerId = player.getId();
  }

  public String getPlayerId() {
    return this.playerId;
  }
}

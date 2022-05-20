package web.message.common;

import session.UserSession;

public class PlayerLeft extends PlayerStatusMessage {
  public PlayerLeft(UserSession player) {
    super("player-left", player);
  }
}

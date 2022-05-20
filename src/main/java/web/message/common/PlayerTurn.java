package web.message.common;

import session.UserSession;

public class PlayerTurn extends PlayerStatusMessage {
  public PlayerTurn(UserSession player) {
    super("turn", player);
  }
}

package web.message.common;

import session.UserSession;

public class PlayerJoined extends PlayerStatusMessage {
  public PlayerJoined(UserSession player) {
    super("player-joined", player);
  }
}

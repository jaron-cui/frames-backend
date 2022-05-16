package web.message.common;

import web.message.OutgoingMessage;

public class PlayerJoined extends OutgoingMessage {
  private final String player;

  public PlayerJoined(String player) {
    super("playerJoined");
    this.player = player;
  }

  public String getPlayer() {
    return this.player;
  }
}

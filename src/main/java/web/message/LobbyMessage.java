package web.message;

import web.data.Lobby;

public abstract class LobbyMessage {
  public static class Assignment extends WebSocketMessage {
    private final Lobby lobby;

    public Assignment(Lobby lobby) {
      super("lobbyAssignment");
      this.lobby = lobby;
    }

    public Lobby getLobby() {
      return this.lobby;
    }
  }

  public static class PlayerJoined extends WebSocketMessage {
    private final String player;

    public PlayerJoined(String player) {
      super("lobbyPlayerJoined");
      this.player = player;
    }

    public String getPlayer() {
      return this.player;
    }
  }
}

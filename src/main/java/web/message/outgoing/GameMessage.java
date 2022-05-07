package web.message.outgoing;

import web.data.Lobby;

public abstract class GameMessage {
  public static class Assignment extends OutgoingMessage {
    private final Lobby lobby;

    public Assignment(Lobby lobby) {
      super("assignment");
      this.lobby = lobby;
    }

    public Lobby getLobby() {
      return this.lobby;
    }
  }

  public static class PlayerJoined extends OutgoingMessage {
    private final String player;

    public PlayerJoined(String player) {
      super("playerJoined");
      this.player = player;
    }

    public String getPlayer() {
      return this.player;
    }
  }

  public static class ReadyToStart extends OutgoingMessage {
    public ReadyToStart() {
      super("readyToStart");
    }
  }

  public static class GameStart extends OutgoingMessage {
    private final Object gameState;

    public GameStart(Object gameState) {
      super("gameStart");
      this.gameState = gameState;
    }

    public Object getGameState() {
      return this.gameState;
    }
  }

  public static class GameOver extends OutgoingMessage {
    private final Object results;

    public GameOver(Object results) {
      super("gameOver");
      this.results = results;
    }

    public Object getResults() {
      return this.results;
    }
  }
}

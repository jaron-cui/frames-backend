package web.data;

import web.SessionManager;
import web.message.incoming.IncomingMessage;
import web.message.outgoing.GameMessage;

public abstract class GameHandler {
  protected final Lobby lobby;
  protected final SessionManager sessionManager;

  public GameHandler(String id) {
    this.lobby = new Lobby();
    this.sessionManager = new SessionManager();
  }

  public final void addPlayer(String playerId) {
    this.messageAll(new GameMessage.PlayerJoined(playerId));
    this.lobby.addPlayer(playerId);
    this.message(playerId, new GameMessage.Assignment(this.lobby));
    if (this.readyToStart()) {
      this.messageAll(new GameMessage.ReadyToStart());
    }
  }

  protected void message(String playerId, Object message) {
    this.sessionManager.sendMessage(playerId, message);
  }

  protected void messageAll(Object message) {
    for (String playerId : this.lobby.getPlayers()) {
      this.message(playerId, message);
    }
  }

  public void acceptMessage(String sessionId, IncomingMessage message) {
    switch (message.getType()) {
      case START:
        this.handleStart(sessionId);
        break;
      case QUIT:
        throw new IllegalArgumentException("Quitting not yet supported.");
      case MOVE:
        this.handleMove(sessionId, message.getContent());
        break;
      default:
        throw new IllegalArgumentException("Unsupported message type.");
    }
  }

  protected void handleStart(String sessionId) {
    if (this.readyToStart()) {
      this.start();
    } else {
      throw new IllegalStateException("Not ready to start.");
    }
  }

  protected abstract void handleMove(String from, String move);

  protected abstract void start();

  public abstract boolean readyToStart();

  public abstract Object getGameState();
}

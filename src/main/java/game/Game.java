package game;

import game.settings.Settings;
import session.Room;
import session.UserSession;

import java.util.List;

public abstract class Game {
  private final String name;

  protected Game(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public abstract GameRoom createRoom(Settings settings, List<UserSession> players);

  public abstract class GameRoom extends Room {
    public static final String MOVE = "move";

    protected GameRoom(List<UserSession> players) {
      super(name, players);
    }
  }
}

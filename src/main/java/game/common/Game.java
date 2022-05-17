package game.common;

import game.common.settings.Settings;
import session.Room;

public abstract class Game<T extends Roster> {
  private final String name;

  protected Game(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public abstract T getRoster();

  public abstract GameRoom createRoom(Settings settings, T roster);

  public abstract class GameRoom extends Room {
    public static final String MOVE = "move";

    protected GameRoom(Roster roster) {
      super(name, roster.getUsers());
    }
  }
}

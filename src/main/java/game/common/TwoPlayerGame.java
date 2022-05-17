package game.common;

public abstract class TwoPlayerGame extends Game<TwoPlayerRoster> {
  protected TwoPlayerGame(String name) {
    super(name);
  }

  @Override
  public TwoPlayerRoster getRoster() {
    return new TwoPlayerRoster();
  }
}

package game.common.message;

public class PlayerMove {
  private final String player;

  public PlayerMove(String player) {
    this.player = player;
  }

  public String getPlayer() {
    return this.player;
  }
}

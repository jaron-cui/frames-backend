package game.chess.model;

public class Position {
  public final int x, y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public boolean isInBounds() {
    return this.x >= 0 && this.x < 8 && this.y >= 0 && this.y < 8;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Position)) {
      return false;
    }
    Position that = (Position) object;
    return this.x == that.x && this.y == that.y;
  }

  @Override
  public int hashCode() {
    return this.x * 13 + this.y * 17;
  }
}

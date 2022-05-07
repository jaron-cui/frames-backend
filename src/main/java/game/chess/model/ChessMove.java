package game.chess.model;

public class ChessMove {
  private Position from;
  private Position to;

  public Position getFrom() {
    return from;
  }

  public void setFrom(MutablePosition from) {
    this.from = from.toPosition();
  }

  public Position getTo() {
    return to;
  }

  public void setTo(MutablePosition to) {
    this.to = to.toPosition();
  }

  public static class MutablePosition {
    public int x, y;

    public Position toPosition() {
      return new Position(x, y);
    }
  }
}

package game.chess.piece;

import game.chess.Board;
import game.chess.Board.Position;
import game.chess.piece.Piece.Color;

public abstract class MoveSet {
  protected final String name;

  public MoveSet(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

  protected abstract boolean canMove(Color color, boolean moved, Position from, Position to,
                                     Board board);

  public static class Pawn extends MoveSet {

    public Pawn(Color color) {
      super("Pawn");
    }

    @Override
    protected boolean canMove(Color color, boolean moved, Position from, Position to, Board board) {
      Position far = color.forward(from);
      Position step = color.forward(from);
      if (to.equals(far)) {
        return !moved && board.emptyAt(step) && board.emptyAt(far);
      }
      if (to.equals(step)) {
        return board.emptyAt(step);
      }
      Position right = color.right(step);
      Position left = color.left(step);
      if (to.equals(right)) {
        return board.emptyAt(right) || board.enemyAt(right, color);
      }
      if (to.equals(left)) {
        return board.emptyAt(left) || board.enemyAt(left, color);
      }
      return false;
    }
  }
}

package game.chess.piece;

import game.chess.Board;
import game.chess.Position;

import java.util.Set;

public abstract class Piece {
  public enum Color {
    WHITE, BLACK;
  }

  protected final Color color;
  protected final Board board;
  protected Position position;
  protected boolean moved;

  protected Piece(Board board, Color color, Position position) {
    this.board = board;
    this.color = color;
    this.position = position;
    this.moved = false;
  }

  public abstract Set<Position> possibleMoves();

  public Color getColor() {
    return this.color;
  }

  protected Position getRelativePosition(int horizontal, int vertical) {
    final int factor = this.color == Color.WHITE ? 1 : -1;
    int x = this.position.x + horizontal * factor;
    int y = this.position.y + vertical * factor;
    return new Position(x, y);
  }

  protected boolean isEmptyAt(Position position) {
    return position.isInBounds() && this.board.getPieceAt(position) == null;
  }

  protected boolean isEnemyAt(Position position) {
    if (!position.isInBounds()) {
      return false;
    }
    Piece piece = this.board.getPieceAt(position);
    return piece != null && piece.getColor() != this.color;
  }
}

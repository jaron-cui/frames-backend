package game.chess.model.piece;

import game.chess.model.Board;
import game.chess.model.Position;

import java.util.HashSet;
import java.util.Set;

public abstract class Piece {
  public enum Color {
    WHITE, BLACK;

    public Color other() {
      return this == WHITE ? BLACK : WHITE;
    }
  }

  protected final String name;
  protected final Color color;
  protected final Board board;
  protected Position position;
  protected boolean moved;

  protected Piece(String name, Board board, Color color, Position position) {
    this.name = name;
    this.board = board;
    this.color = color;
    this.position = position;
    this.moved = false;
  }

  public abstract Set<Position> possibleMoves();

  public Set<Position> conditionalMoves() {
    return new HashSet<>();
  }

  public Color getColor() {
    return this.color;
  }

  public Position getPosition() {
    return this.position;
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

  public void moveTo(Position to) {
    this.moved = true;
    this.position = to;
  }

  public String getName() {
    return this.name;
  }
}

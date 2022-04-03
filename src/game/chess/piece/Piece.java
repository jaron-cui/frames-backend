package game.chess.piece;

import game.chess.Board;
import game.chess.Board.Position;

public class Piece {
  public enum Color {
    WHITE, BLACK;

    public Position forward(Position position) {
      return new Position(position.x, position.y + (this == WHITE ? 1 : -1));
    }

    public Position backward(Position position) {
      return new Position(position.x, position.y + (this == WHITE ? -1 : 1));
    }

    public Position left(Position position) {
      return new Position(position.x - 1, position.y);
    }

    public Position right(Position position) {
      return new Position(position.x + 1, position.y);
    }
  }

  private final Color color;
  private final MoveSet moveSet;
  private boolean moved;
  private final Board board;

  protected Piece(Color color, MoveSet moveSet, Board board) {
    this.color = color;
    this.moveSet = moveSet;
    this.moved = false;
    this.board = board;
  }

  public Color getColor() {
    return this.color;
  }

  public boolean canMove(Position from, Position to) {
    return this.moveSet.canMove(this.color, this.moved, from, to, this.board);
  }
}

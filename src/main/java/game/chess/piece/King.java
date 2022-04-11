package game.chess.piece;

import game.chess.Board;
import game.chess.Position;

import java.util.Set;

public class King extends Enumerated {
  public King(Board board, Color color, Position position) {
    super(board, color, position);
  }

  @Override
  public Set<Position> possibleMoves() {
    Position[] enumeration = new Position[] {
        this.getRelativePosition(0, -1), this.getRelativePosition(0, 1),
        this.getRelativePosition(-1, 0), this.getRelativePosition(1, 0),
        this.getRelativePosition(-1, -1), this.getRelativePosition(-1, 1),
        this.getRelativePosition(1, -1), this.getRelativePosition(1, 1)
    };

    return this.getAttackable(enumeration);
  }
}
package game.chess.piece;

import game.chess.model.Board;
import game.chess.model.Position;

import java.util.Set;

public class Knight extends Enumerated {
  public Knight(Board board, Color color, Position position) {
    super("knight", board, color, position);
  }

  @Override
  public Set<Position> possibleMoves() {
    Position[] enumeration = new Position[] {
        this.getRelativePosition(1, 2), this.getRelativePosition(-1, 2),
        this.getRelativePosition(1, -2), this.getRelativePosition(-1, -2),
        this.getRelativePosition(2, 1), this.getRelativePosition(-2, 1),
        this.getRelativePosition(2, -1), this.getRelativePosition(-2, -1)
    };

    return this.getAttackable(enumeration);
  }
}

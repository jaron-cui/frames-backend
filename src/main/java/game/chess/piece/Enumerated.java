package game.chess.piece;

import game.chess.Board;
import game.chess.Position;

import java.util.HashSet;
import java.util.Set;

public abstract class Enumerated extends Piece {
  protected Enumerated(String name, Board board, Color color, Position position) {
    super(name, board, color, position);
  }

  protected Set<Position> getAttackable(Position[] enumeration) {
    Set<Position> positions = new HashSet<>();
    for (Position position : enumeration) {
      if (this.isEmptyAt(position) || this.isEnemyAt(position)) {
        positions.add(position);
      }
    }

    return positions;
  }
}

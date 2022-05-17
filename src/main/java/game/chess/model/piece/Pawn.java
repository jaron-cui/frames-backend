package game.chess.model.piece;

import game.chess.model.Board;
import game.chess.model.Position;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {
  public Pawn(Board board, Color color, Position position) {
    super("pawn", board, color, position);
  }

  @Override
  public Set<Position> possibleMoves() {
    Set<Position> positions = new HashSet<>();

    // forward moves
    Position step = this.getRelativePosition(0, 1);
    if (this.isEmptyAt(step)) {
      positions.add(step);
      if (!this.moved) {
        Position leap = this.getRelativePosition(0, 2);
        if (this.isEmptyAt(leap)) {
          positions.add(leap);
        }
      }
    }

    // flank moves
    Position leftFlank = this.getRelativePosition(-1, 1);
    if (this.isEnemyAt(leftFlank)) {
      positions.add(leftFlank);
    }
    Position rightFlank = this.getRelativePosition(1, 1);
    if (this.isEnemyAt(rightFlank)) {
      positions.add(rightFlank);
    }

    return positions;
  }

  @Override
  public Set<Position> conditionalMoves() {
    Set<Position> positions = new HashSet<>();
    Position leftFlank = this.getRelativePosition(-1, 1);
    Position rightFlank = this.getRelativePosition(1, 1);
    if (leftFlank.isInBounds()) {
      positions.add(leftFlank);
    }
    if (rightFlank.isInBounds()) {
      positions.add(rightFlank);
    }
    return positions;
  }
}

package game.chess.piece;

import game.chess.model.Board;
import game.chess.model.Position;

import java.util.HashSet;
import java.util.Set;

public class Queen extends Linear {
  public Queen(Board board, Color color, Position position) {
    super("queen", board, color, position);
  }

  @Override
  public Set<Position> possibleMoves() {
    Set<Position> positions = new HashSet<>();
    this.addOrthogonalMoves(positions);
    this.addDiagonalMoves(positions);
    return positions;
  }
}

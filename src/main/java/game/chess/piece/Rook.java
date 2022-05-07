package game.chess.piece;

import game.chess.model.Board;
import game.chess.model.Position;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Linear {
  public Rook(Board board, Color color, Position position) {
    super("rook", board, color, position);
  }

  @Override
  public Set<Position> possibleMoves() {
    Set<Position> positions = new HashSet<>();
    this.addOrthogonalMoves(positions);
    return positions;
  }
}

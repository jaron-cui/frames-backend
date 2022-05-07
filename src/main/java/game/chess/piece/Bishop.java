package game.chess.piece;

import game.chess.model.Board;
import game.chess.model.Position;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Linear {
  public Bishop(Board board, Color color, Position position) {
    super("bishop", board, color, position);
  }

  @Override
  public Set<Position> possibleMoves() {
    Set<Position> positions = new HashSet<>();
    this.addDiagonalMoves(positions);
    return positions;
  }
}

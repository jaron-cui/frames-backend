package game.chess.piece;

import game.chess.Board;
import game.chess.Position;

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

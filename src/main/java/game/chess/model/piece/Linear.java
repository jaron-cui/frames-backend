package game.chess.model.piece;

import game.chess.model.Board;
import game.chess.model.Position;

import java.util.Set;

public abstract class Linear extends Piece {
  protected Linear(String name, Board board, Color color, Position position) {
    super(name, board, color, position);
  }

  protected void addOrthogonalMoves(Set<Position> positions) {
    this.addLinearMoves(positions, -1, 0);
    this.addLinearMoves(positions, 1, 0);
    this.addLinearMoves(positions, 0, -1);
    this.addLinearMoves(positions, 0, 1);
  }

  protected void addDiagonalMoves(Set<Position> positions) {
    this.addLinearMoves(positions, -1, -1);
    this.addLinearMoves(positions, 1, -1);
    this.addLinearMoves(positions, -1, 1);
    this.addLinearMoves(positions, 1, 1);
  }

  protected void addLinearMoves(Set<Position> positions, int dx, int dy) {
    int x = dx;
    int y = dy;
    Position next = this.getRelativePosition(x, y);
    while (this.isEmptyAt(next) || this.isEnemyAt(next)) {
      positions.add(next);
      if (this.isEnemyAt(next)) {
        break;
      }
      x += dx;
      y += dy;
      next = this.getRelativePosition(x, y);
    }
  }
}

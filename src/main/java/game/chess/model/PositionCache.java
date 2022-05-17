package game.chess.model;

import game.chess.model.piece.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PositionCache {
  private final Map<Piece, Set<Position>> positions;
  private final PieceSet[][] positionGrid;

  public PositionCache() {
    this.positions = new HashMap<>();
    this.positionGrid = new PieceSet[8][8];

    for (int y = 0; y < 8; y += 1) {
      for (int x = 0; x < 8; x += 1) {
        this.positionGrid[y][x] = new PieceSet();
      }
    }
  }

  public Set<Piece> getAssociatedPieces(Position position) {
    return new HashSet<Piece>(this.positionGrid[position.y][position.x]);
  }

  public Set<Position> getPositions(Piece piece) {
    return new HashSet<>(this.positions.get(piece));
  }

  public Map<Piece, Set<Position>> getPositionMap() {
    return Collections.unmodifiableMap(this.positions);
  }

  public void setPositions(Piece piece, Set<Position> newPositions) {
    if (this.positions.containsKey(piece)) {
      for (Position position : this.positions.get(piece)) {
        this.positionGrid[position.y][position.x].remove(piece);
      }
    }

    this.positions.put(piece, newPositions);
    for (Position position : newPositions) {
      if (!position.isInBounds()) {
        System.out.println("outta bounds");
      }
      this.positionGrid[position.y][position.x].add(piece);
    }
  }

  public void removePiece(Piece piece) {
    for (Position position : this.positions.get(piece)) {
      this.positionGrid[position.y][position.x].remove(piece);
    }

    this.positions.remove(piece);
  }

  private static class PieceSet extends HashSet<Piece> {

  }
}

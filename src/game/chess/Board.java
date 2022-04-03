package game.chess;

import game.chess.piece.MoveSet;
import game.chess.piece.Piece;

public class Board {
  private final Piece[][] board;

  public Board() {
    this.board = new Piece[8][8];
    this.setBoard();
  }

  public void setBoard() {
    this.clearBoard();
    this.setSide(Piece.Color.WHITE, 0, 1);
    this.setSide(Piece.Color.BLACK, 7, -1);
  }

  private void setSide(Piece.Color color, int home, int increment) {
    this.board[home][0] = this.board[home][7] = null;//rook
    this.board[home][1] = this.board[home][6] = null;//knight
    this.board[home][2] = this.board[home][5] = null;//bishop
    this.board[home][increment * 3] = null;//queen
    this.board[home][increment * 4] = null;//king
    for (int i = 0; i < 8; i += 1) {
      this.board[home + increment][i] = null;//pawn
    }
  }

  private Piece makePiece(Piece.Color color, MoveSet moveSet) {
    return new Piece(color, moveSet, this);
  }

  public void clearBoard() {
    for (int y = 0; y < 8; y += 1) {
      for (int x = 0; x < 8; x += 1) {
        this.board[y][x] = null;
      }
    }
  }

  public boolean emptyAt(Position position) {
    return this.validPosition(position) && this.getPiece(position) == null;
  }

  public boolean enemyAt(Position position, Piece.Color color) {
    return this.validPosition(position) && this.getPiece(position).getColor() != color;
  }

  private Piece getPiece(Position position) {
    return this.board[position.y][position.x];
  }

  private boolean validPosition(Position position) {
    return position.x >= 0 && position.x < 8 && position.y >= 0 && position.y < 8;
  }

  public static class Position {
    public final int x, y;

    public Position(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object object) {
      if (!(object instanceof Position)) {
        return false;
      }
      Position that = (Position) object;
      return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
      return this.x * 13 + this.y * 17;
    }
  }
}

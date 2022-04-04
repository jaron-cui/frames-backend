package game.chess;

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

  public void clearBoard() {
    for (int y = 0; y < 8; y += 1) {
      for (int x = 0; x < 8; x += 1) {
        this.board[y][x] = null;
      }
    }
  }

  public Piece getPieceAt(Position position) {
    return this.board[position.y][position.x];
  }
}

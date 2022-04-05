package game.chess;

import game.chess.piece.Bishop;
import game.chess.piece.King;
import game.chess.piece.Knight;
import game.chess.piece.Pawn;
import game.chess.piece.Piece;
import game.chess.piece.Queen;
import game.chess.piece.Rook;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
  private final Piece[][] board;
  private final Set<Piece> pieces;
  private final Map<Piece.Color, Set<Piece>> colorPieces;
  private King whiteKing;
  private King blackKing;

  public Board() {
    this.board = new Piece[8][8];
    this.pieces = new HashSet<>();
    this.colorPieces = new HashMap<>();
    this.setBoard();
  }

  public void setBoard() {
    this.clearBoard();
    this.setSide(Piece.Color.WHITE);
    this.setSide(Piece.Color.BLACK);
  }

  private void setSide(Piece.Color color) {
    boolean isWhite = color == Piece.Color.WHITE;
    int home = isWhite ? 0 : 7;
    int forward = isWhite ? 1 : -1;

    Set<Piece> pieces = new HashSet<>();

    pieces.add(new Rook(this, color, new Position(0, home)));
    pieces.add(new Knight(this, color, new Position(1, home)));
    pieces.add(new Bishop(this, color, new Position(2, home)));

    pieces.add(new Queen(this, color, new Position(forward * 3, home)));
    King king = new King(this, color, new Position(forward * 4, home));
    pieces.add(king);

    pieces.add(new Bishop(this, color, new Position(5, home)));
    pieces.add(new Knight(this, color, new Position(6, home)));
    pieces.add(new Rook(this, color, new Position(7, home)));

    for (int i = 0; i < 8; i += 1) {
      pieces.add(new Pawn(this, color, new Position(i, home + forward)));
    }

    for (Piece piece : pieces) {
      this.putPiece(piece);
    }

    this.pieces.addAll(pieces);
    this.colorPieces.put(color, pieces);

    if (isWhite) {
      this.whiteKing = king;
    } else {
      this.blackKing = king;
    }
  }

  public void clearBoard() {
    for (int y = 0; y < 8; y += 1) {
      for (int x = 0; x < 8; x += 1) {
        this.board[y][x] = null;
      }
    }
    this.pieces.clear();
  }

  public void putPiece(Piece piece) {
    Position position = piece.getPosition();
    if (!position.isInBounds()) {
      throw new IllegalArgumentException("Piece is out of bounds.");
    }

    this.board[position.y][position.x] = piece;
  }

  public Piece movePiece(Piece piece, Position to) {
    if (!to.isInBounds()) {
      throw new IllegalArgumentException("Destination is out of bounds.");
    }

    Piece victim = this.getPieceAt(to);
    if (victim != null) {
      this.pieces.remove(victim);
      this.colorPieces.get(victim.getColor()).remove(victim);
    }
    this.board[to.y][to.x] = null;
    piece.moveTo(to);
    this.putPiece(piece);
    return victim;
  }

  public Set<Piece> getPieces() {
    return new HashSet<>(this.pieces);
  }

  public Set<Piece> getPieces(Piece.Color color) {
    return new HashSet<>(this.colorPieces.get(color));
  }

  public Piece getPieceAt(Position position) {
    return this.board[position.y][position.x];
  }

  public King getKing(Piece.Color color) {
    return color == Piece.Color.WHITE ? this.whiteKing : this.blackKing;
  }
}

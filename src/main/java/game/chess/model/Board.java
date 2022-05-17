package game.chess.model;

import game.common.IllegalMoveException;
import game.chess.model.piece.Bishop;
import game.chess.model.piece.King;
import game.chess.model.piece.Knight;
import game.chess.model.piece.Pawn;
import game.chess.model.piece.Piece;
import game.chess.model.piece.Queen;
import game.chess.model.piece.Rook;

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
  private final PositionCache updateTracker;
  private final Map<Piece.Color, PositionCache> possibleMoves;
  private Piece.Color checkmate;

  public Board() {
    this.board = new Piece[8][8];
    this.pieces = new HashSet<>();
    this.colorPieces = new HashMap<>();
    this.colorPieces.put(Piece.Color.WHITE, new HashSet<>());
    this.colorPieces.put(Piece.Color.BLACK, new HashSet<>());
    this.updateTracker = new PositionCache();
    this.possibleMoves = new HashMap<>();
    this.possibleMoves.put(Piece.Color.WHITE, new PositionCache());
    this.possibleMoves.put(Piece.Color.BLACK, new PositionCache());
    this.setBoard();
    for (Piece piece : this.pieces) {
      this.updateCacheFor(piece);
    }
  }

  public void setBoard() {
    this.clearBoard();
    this.setSide(Piece.Color.WHITE);
    this.setSide(Piece.Color.BLACK);
    this.checkmate = null;
  }

  private void setSide(Piece.Color color) {
    boolean isWhite = color == Piece.Color.WHITE;
    int home = isWhite ? 0 : 7;
    int forward = isWhite ? 1 : -1;

    Set<Piece> pieces = new HashSet<>();

    pieces.add(new Rook(this, color, new Position(0, home)));
    pieces.add(new Knight(this, color, new Position(1, home)));
    pieces.add(new Bishop(this, color, new Position(2, home)));

    pieces.add(new Queen(this, color, new Position(forward * 3 + home, home)));
    King king = new King(this, color, new Position(forward * 4 + home, home));
    pieces.add(king);

    pieces.add(new Bishop(this, color, new Position(5, home)));
    pieces.add(new Knight(this, color, new Position(6, home)));
    pieces.add(new Rook(this, color, new Position(7, home)));

    for (int i = 0; i < 8; i += 1) {
      pieces.add(new Pawn(this, color, new Position(i, home + forward)));
    }

    for (Piece piece : pieces) {
      this.addPiece(piece);
    }

    this.pieces.addAll(pieces);
    this.colorPieces.put(color, pieces);

    if (isWhite) {
      this.whiteKing = king;
    } else {
      this.blackKing = king;
    }
  }

  public void updateCacheFor(Piece piece) {
    Set<Position> possibleMoves = piece.possibleMoves();
    Set<Position> updateRange = piece.conditionalMoves();
    updateRange.addAll(possibleMoves);
    this.updateTracker.setPositions(piece, updateRange);
    this.possibleMoves.get(piece.getColor()).setPositions(piece, possibleMoves);
  }

  public void updatePosition(Position position) {
    for (Piece obstructed : this.updateTracker.getAssociatedPieces(position)) {
      this.updateCacheFor(obstructed);
    }
  }

  public void clearBoard() {
    for (int y = 0; y < 8; y += 1) {
      for (int x = 0; x < 8; x += 1) {
        this.board[y][x] = null;
      }
    }
    System.out.println("nulled board");
    for (Piece piece : this.pieces) {
      this.updateTracker.removePiece(piece);
      this.possibleMoves.get(piece.getColor()).removePiece(piece);
    }
    System.out.println("cleared caches");
    this.pieces.clear();
    this.colorPieces.get(Piece.Color.WHITE).clear();
    this.colorPieces.get(Piece.Color.BLACK).clear();
  }

  private void addPiece(Piece piece) {
    Position position = piece.getPosition();
    if (!position.isInBounds()) {
      throw new IllegalArgumentException("Piece is out of bounds.");
    }

    this.board[position.y][position.x] = piece;
    this.pieces.add(piece);
    this.colorPieces.get(piece.getColor()).add(piece);
    this.updateCacheFor(piece);
    this.updatePosition(position);
  }

  private void removePiece(Piece piece) {
    Position position = piece.getPosition();
    this.board[position.y][position.x] = null;
    this.updateTracker.removePiece(piece);
    this.possibleMoves.get(piece.getColor()).removePiece(piece);
    this.pieces.remove(piece);
    this.colorPieces.get(piece.getColor()).remove(piece);
    this.updatePosition(position);
  }

  public boolean isInCheck(Piece.Color color) {
    return !this.possibleMoves.get(color.other())
        .getAssociatedPieces(this.getKing(color).getPosition()).isEmpty();
  }

  // check all of the king's possible moves
  // if else, if only 1 attacker, check all possible move positions
  // for possible interference
  // if multiple attackers and king is immobilized, then checkmate!
  private Set<Position> getKingEscapePositions(Piece.Color color) {
    King king = this.getKing(color);
    Set<Position> positions = new HashSet<>();
    for (Position position : this.possibleMoves.get(color).getPositions(king)) {
      Position from = king.getPosition();
      Piece displaced = this.movePiece(king, position);
      if (!this.isInCheck(color)) {
        positions.add(position);
      }

      this.movePiece(king, from);
      if (displaced != null) {
        this.addPiece(displaced);
      }
    }

    return positions;
  }

  private Map<Piece, Set<Position>> getKingSavingMoves(Piece.Color color) {
    King king = this.getKing(color);
    PositionCache enemyMoves = this.possibleMoves.get(color.other());
    Set<Piece> attackers = enemyMoves.getAssociatedPieces(king.getPosition());

    Map<Piece, Set<Position>> moves = new HashMap<>();
    Set<Position> kingMoves = this.getKingEscapePositions(color);
    if (!kingMoves.isEmpty()) {
      moves.put(king, kingMoves);
    }
    // I cannot think of a situation where there is more than one attacker on the king and
    // the king cannot move out of check where another piece can prevent all incoming attacks
    if (attackers.size() > 1) {
      return moves;
    }

    // search for positions which can prevent checkmate by blocking the attacker while not exposing
    // another
    Piece attacker = attackers.stream().iterator().next();
    Set<Position> lineOfFire = enemyMoves.getPositions(attacker);
    lineOfFire.add(attacker.getPosition());
    for (Position point : lineOfFire) {
      Set<Piece> saviors = this.possibleMoves.get(color).getAssociatedPieces(point);
      for (Piece savior : saviors) {
        Position from = savior.getPosition();
        Piece victim = this.movePiece(savior, point);
        if (!this.isInCheck(color)) {
          moves.putIfAbsent(savior, new HashSet<>());
          moves.get(savior).add(point);
        }
        this.movePiece(savior, from);
        if (victim != null) {
          this.addPiece(victim);
        }
      }
    }

    return moves;
  }

  public Piece makeMove(Piece piece, Position to) {
    if (!to.isInBounds()) {
      throw new IllegalMoveException("Destination is out of bounds.");
    }

    Piece.Color color = piece.getColor();

    Map<Piece, Set<Position>> legalMoves;
    if (this.isInCheck(color)) {
      legalMoves = this.getKingSavingMoves(color);
    } else {
      // TODO: cull illegal moves
      legalMoves = this.possibleMoves.get(color).getPositionMap();
    }

    if (legalMoves.get(piece).contains(to)) {
      if (this.isInCheck(color.other()) && this.getKingSavingMoves(color.other()).isEmpty()) {
        this.checkmate = color.other();
      }
      return this.movePiece(piece, to);
    } else {
      throw new IllegalMoveException("Illegal move.");
    }
  }

  private Piece movePiece(Piece piece, Position to) {
    Piece victim = this.getPieceAt(to);
    if (victim != null) {
      this.removePiece(victim);
    }
    this.removePiece(piece);
    piece.moveTo(to);
    this.addPiece(piece);
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

  public boolean isInCheckmate(Piece.Color color) {
    return this.checkmate == color;
  }
}

package game.chess;

import game.IllegalMoveException;
import game.chess.model.Board;
import game.chess.model.Position;
import game.chess.piece.Piece;
import game.chess.piece.Piece.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChessGame {
  private final Board board;
  private final Map<Color, Set<Piece>> captures;

  public ChessGame() {
    this(new Board());
  }

  public ChessGame(Board board) {
    this.board = board;
    this.captures = new HashMap<>();

    this.captures.put(Color.WHITE, new HashSet<>());
    this.captures.put(Color.BLACK, new HashSet<>());
  }

  public Board getBoard() {
    return this.board;
  }

  public Set<Piece> getCaptures(Color color) {
    return this.captures.get(color);
  }

  public void move(Color playerColor, Position from, Position to) {
    Piece piece = this.board.getPieceAt(from);
    if (piece == null) {
      throw new IllegalMoveException(
          "There must be a piece at the specified position in order to move."
      );
    } else if (piece.getColor() != playerColor) {
      throw new IllegalMoveException(
          "A piece must have the same color as the player in order to be moved."
      );
    }

    Piece victim = this.board.makeMove(piece, to);
    if (victim != null) {
      this.captures.get(playerColor).add(victim);
    }

    // TODO: proper response messaging
    if (this.board.isInCheckmate(playerColor.other())) {

    } else if (this.board.isInCheck(playerColor.other())) {

    } else {

    }
  }
}

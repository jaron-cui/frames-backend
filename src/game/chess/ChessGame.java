package game.chess;

import game.Game;
import game.chess.piece.Piece;
import game.chess.piece.Piece.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChessGame extends Game<Move> {
  private final Board board;
  private final Map<Color, Set<Piece>> captures;

  public ChessGame() {
    this(new Board());
  }

  public ChessGame(Board board) {
    super(Move.class);
    this.board = board;
    this.captures = new HashMap<>();

    this.captures.put(Color.WHITE, new HashSet<>());
    this.captures.put(Color.BLACK, new HashSet<>());
  }

  @Override
  public void onMessage(Move move) {
    Color playerColor = move.getColor();
    Piece piece = this.board.getPieceAt(move.getFrom());
    if (piece == null) {
      throw new IllegalArgumentException(
          "There must be a piece at the specified position in order to move."
      );
    } else if (piece.getColor() != playerColor) {
      throw new IllegalArgumentException(
          "A piece must have the same color as the player in order to be moved."
      );
    }

    Piece victim = this.board.makeMove(piece, move.getTo());
    if (victim != null) {
      this.captures.get(playerColor).add(victim);
    }

    // TODO: proper response messaging
    if (this.board.isInCheckmate(playerColor.other())) {

    } else if (this.board.isInCheck(playerColor.other())) {

    } else {

    }
  }

  @Override
  public void start() {

  }

  @Override
  public void end() {

  }

  @Override
  public boolean inProgress() {
    return false;
  }

  @Override
  public boolean isOver() {
    return false;
  }

  @Override
  public String getWinner() {
    return null;
  }
}

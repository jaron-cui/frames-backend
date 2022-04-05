package game.chess;

import game.Game;
import game.chess.piece.Piece;
import game.chess.piece.Piece.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Model extends Game<Move> {
  private final Board board;
  private final Map<Color, Set<Piece>> captures;
  private final List<List<Set<Piece>>> updateGrid;
  private final Map<Piece, Set<Position>> updateRanges;
  private final Map<Piece, Set<Position>> possibleMoves;

  public Model() {
    this(new Board());
  }

  public Model(Board board) {
    super(Move.class);
    this.board = board;
    this.captures = new HashMap<>();
    this.updateGrid = new ArrayList<>();
    this.updateRanges = new HashMap<>();
    this.possibleMoves = new HashMap<>();

    this.captures.put(Color.WHITE, new HashSet<>());
    this.captures.put(Color.BLACK, new HashSet<>());

    for (int i = 0; i < 8; i += 1) {
      List<Set<Piece>> row = new ArrayList<>();
      for (int j = 0; j < 8; j += 1) {
        row.add(new HashSet<>());
      }
      this.updateGrid.add(row);
    }

    for (Piece piece : this.board.getPieces()) {
      this.updateCache(piece);
    }
  }

  private void updateCache(Piece piece) {
    Set<Position> possible = piece.possibleMoves();
    Set<Position> conditional = piece.conditionalMoves();
    Set<Position> all = new HashSet<>(possible);
    all.addAll(conditional);

    if (this.updateRanges.containsKey(piece)) {
      for (Position position : this.updateRanges.get(piece)) {
        this.updateGrid.get(position.y).get(position.x).remove(piece);
      }
    }

    this.updateRanges.put(piece, all);
    for (Position position : all) {
      this.updateGrid.get(position.y).get(position.x).add(piece);
    }

    this.possibleMoves.put(piece, possible);
  }

  @Override
  public void onMessage(Move move) {
    // TODO: check for check
    Color playerColor = move.getColor();
    

    Position from = move.getFrom();
    Position to = move.getTo();
    Piece piece = this.board.getPieceAt(from);
    if (!this.possibleMoves.get(piece).contains(to)) {
      // TODO: failed move
    }

    Piece victim = this.board.movePiece(piece, to);
    if (victim != null) {
      this.captures.get(playerColor).add(victim);
      this.updateCache(victim);
    }
    this.updateCache(piece);

    // TODO: check for checkmate
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

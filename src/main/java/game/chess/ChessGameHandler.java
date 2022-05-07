package game.chess;

import game.IllegalMoveException;
import game.chess.ChessGame;
import game.chess.model.ChessMove;
import game.chess.model.Position;
import game.chess.piece.Piece;
import util.Data;
import web.data.GameHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChessGameHandler extends GameHandler {
  private final ChessGame game;
  private final Map<String, Piece.Color> players;

  public ChessGameHandler(String id) {
    super(id);
    this.game = new ChessGame();
    this.players = new HashMap<>();
  }

  @Override
  public void handleMove(String from, String message) {
    if (!this.players.containsKey(from)) {
      throw new IllegalMoveException("A non-player cannot make a move.");
    }

    ChessMove move = Data.deserialize(message, ChessMove.class);
    this.game.move(this.players.get(from), move.getFrom(), move.getTo());

    Object gameState = this.getGameState();
    for (String player : this.players.keySet()) {
      this.sessionManager.sendMessage(player, gameState);
    }
  }

  @Override
  protected void start() {
    this.players.put(this.lobby.getPlayers().get(0), Piece.Color.WHITE);
    this.players.put(this.lobby.getPlayers().get(1), Piece.Color.BLACK);
  }

  @Override
  public boolean readyToStart() {
    return this.lobby.getPlayers().size() >= 2;
  }

  @Override
  public Object getGameState() {
    return new GameState(this.game);
  }

  public static class PieceInfo {
    public String name;
    public Piece.Color color;

    public PieceInfo(Piece piece) {
      this.name = piece.getName();
      this.color = piece.getColor();
    }
  }

  public static class GameState {
    public PieceInfo[][] board;
    public Map<Piece.Color, List<PieceInfo>> captures;

    public GameState(ChessGame game) {
      this.board = new PieceInfo[8][8];
      for (Piece piece : game.getBoard().getPieces()) {
        Position position = piece.getPosition();
        this.board[position.y][position.x] = new PieceInfo(piece);
      }
      this.captures = new HashMap<>();
      this.captures.put(Piece.Color.WHITE, toPieceInfo(game.getCaptures(Piece.Color.WHITE)));
      this.captures.put(Piece.Color.BLACK, toPieceInfo(game.getCaptures(Piece.Color.BLACK)));
    }

    private static List<PieceInfo> toPieceInfo(Set<Piece> pieces) {
      List<PieceInfo> pieceInfos = new ArrayList<>();
      for (Piece piece : pieces) {
        pieceInfos.add(new PieceInfo(piece));
      }
      return pieceInfos;
    }
  }
}

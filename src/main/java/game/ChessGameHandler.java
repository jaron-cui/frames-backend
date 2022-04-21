package game;

import game.chess.ChessGame;
import game.chess.ChessMove;
import game.chess.Position;
import game.chess.piece.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChessGameHandler extends GameHandler<ChessGame> {
  private final ChessGame game;
  private final Map<String, Piece.Color> players;

  public ChessGameHandler(String whitePlayer, String blackPlayer) {
    this.game = new ChessGame();
    this.players = new HashMap<>();
    this.players.put(whitePlayer, Piece.Color.WHITE);
    this.players.put(blackPlayer, Piece.Color.BLACK);
  }

  @Override
  public void acceptMessage(Message message) {
    if (!this.players.containsKey(message.getSender())) {
      throw new IllegalMoveException("A non-player cannot make a move.");
    }

    ChessMove move = this.deserialize(message.getContent(), ChessMove.class);
    this.game.move(this.players.get(message.getSender()), move.getFrom(), move.getTo());

    Object gameState = this.getGameState();
    for (String player : this.players.keySet()) {
      this.messenger.sendMessage(player, gameState);
    }
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

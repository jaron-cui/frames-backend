package game.chess;

import game.chess.model.ChessModel;
import game.chess.model.ChessMove;
import game.chess.model.Position;
import game.chess.model.piece.Piece;
import game.common.IllegalMoveException;
import game.common.Roster;
import game.common.TwoPlayerGame;
import game.common.TwoPlayerRoster;
import game.common.settings.Settings;
import session.UserSession;
import util.Data;
import web.message.IncomingMessage;
import web.message.OutgoingMessage;
import web.message.common.ErrorMessage;
import web.message.common.PlayerTurn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static game.chess.model.piece.Piece.Color.BLACK;
import static game.chess.model.piece.Piece.Color.WHITE;

public class Chess extends TwoPlayerGame {
  public Chess() {
    super("chess-multiplayer");
  }

  @Override
  public GameRoom createRoom(Settings settings, TwoPlayerRoster roster) {
    return new ChessRoom(roster);
  }

  public class ChessRoom extends GameRoom {
    private final ChessModel model;
    private final UserSession white;
    private final UserSession black;
    private boolean whiteMove;

    private ChessRoom(TwoPlayerRoster roster) {
      super(roster);
      List<UserSession> players = roster.getUsersByRole(Roster.Role.PLAYER);
      this.model = new ChessModel();
      this.white = players.get(0);
      this.black = players.get(1);
      this.whiteMove = true;
    }

    private UserSession getTurnPlayer() {
      return whiteMove ? white : black;
    }

    private Piece.Color getTurnColor() {
      return whiteMove ? WHITE : BLACK;
    }

    @Override
    protected void handleCustomMessage(IncomingMessage message) {
      switch (message.getType()) {
        case MOVE:
          ChessMove move = Data.deserialize(message.getContent(), ChessMove.class);
          if (message.getSender() == this.getTurnPlayer()) {
            try {
              this.model.move(this.getTurnColor(), move.getFrom(), move.getTo());
              this.whiteMove = !this.whiteMove;
              messageUsers(this.getUsers(), new MoveMessage(move.getFrom(), move.getTo()));
              messageUsers(this.getUsers(), new PlayerTurn(this.getTurnPlayer()));
            } catch (IllegalMoveException e) {
              // TODO: something
              message.getSender().sendMessage(new ErrorMessage(e.getMessage()));
            }
          } else {
            message.getSender().sendMessage(new ErrorMessage("you are not the one!"));
          }
          break;
        default:
          System.out.println("Got weird message: " + message.getType());
      }
    }
/*
    @Override
    public Map<String, Object> retrieveFields() {
      Map<String, Object> fields = super.retrieveFields();
      fields.put("white", this.white.getId());
      fields.put("black", this.black.getId());

      return fields;
    }*/
  }

  private static class MoveMessage extends OutgoingMessage {
    private final Position from;
    private final Position to;

    public MoveMessage(Position from, Position to) {
      super("move");
      this.from = from;
      this.to = to;
    }

    public Position getFrom() {
      return this.from;
    }

    public Position getTo() {
      return this.to;
    }
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

    public GameState(ChessModel game) {
      this.board = new PieceInfo[8][8];
      for (Piece piece : game.getBoard().getPieces()) {
        Position position = piece.getPosition();
        this.board[position.y][position.x] = new PieceInfo(piece);
      }
      this.captures = new HashMap<>();
      this.captures.put(WHITE, toPieceInfo(game.getCaptures(WHITE)));
      this.captures.put(BLACK, toPieceInfo(game.getCaptures(BLACK)));
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

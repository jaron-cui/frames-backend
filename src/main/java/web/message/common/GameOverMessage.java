package web.message.common;

import session.UserSession;
import web.message.OutgoingMessage;

public class GameOverMessage extends OutgoingMessage {
  private final String winner;

  public GameOverMessage(UserSession winner) {
    super("game-over");
    this.winner = winner.getId();
  }

  public String getWinner() {
    return this.winner;
  }
}

package game.clickrace;

import game.common.Roster;
import game.common.TwoPlayerGame;
import game.common.TwoPlayerRoster;
import game.common.settings.Settings;
import session.UserSession;
import web.message.IncomingMessage;
import web.message.common.GameOverMessage;
import web.message.common.ScoreUpdateMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ClickRace extends TwoPlayerGame {
  public ClickRace() {
    super("clickrace");
  }

  @Override
  public GameRoom createRoom(Settings settings, TwoPlayerRoster roster) {
    return new ClickRaceRoom(roster);
  }

  public class ClickRaceRoom extends GameRoom {
    private final Map<UserSession, Integer> players;
    private final int maxDelay;
    private final int winMin;
    private final int winBy;
    private final int[] scores;
    private boolean clickable;

    private ClickRaceRoom(TwoPlayerRoster roster) {
      super(roster);
      List<UserSession> players = roster.getUsersByRole(Roster.Role.PLAYER);
      this.players = new HashMap<>();
      this.players.put(players.get(0), 0);
      this.players.put(players.get(1), 1);

      this.scores = new int[2];
      this.clickable = false;
      this.maxDelay = 7000;
      this.winMin = 3;
      this.winBy = 2;
    }

    private void startNextRound() {
      this.clickable = false;
      int delay = (int) (Math.random() * this.maxDelay);
      new Timer().schedule(new TimerTask() {
        @Override
        public void run() {
          clickable = true;
          messageAll(new ClickableMessage());
        }
      }, delay);
    }

    @Override
    protected void handleCustomMessage(IncomingMessage message) {
      if (!message.getType().equals(MOVE)) {
        return;
      }

      UserSession player = message.getSender();
      int scoreChange = this.clickable ? 1 : -1;
      int newScore = this.scores[this.players.get(player)] += scoreChange;
      this.messageAll(new ScoreUpdateMessage(player, newScore));

      int otherScore = this.scores[this.players.get(player) == 0 ? 1 : 0];
      if (this.clickable && newScore > this.winMin && newScore - otherScore >= this.winBy) {
        this.messageAll(new GameOverMessage(player));
        // TODO: handle game over
        return;
      }

      this.startNextRound();
    }
  }
}

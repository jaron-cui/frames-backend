package game.clickrace;

import game.common.Game;
import game.common.Roster;
import game.common.TwoPlayerGame;
import game.common.TwoPlayerRoster;
import game.common.settings.Settings;
import session.UserSession;
import web.message.IncomingMessage;
import web.message.common.GameOverMessage;
import web.message.common.ScoreUpdateMessage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
      this.winMin = 3;
      this.winBy = 2;
    }

    @Override
    protected void handleCustomMessage(IncomingMessage message) {
      if (this.clickable && message.getType().equals(MOVE)) {
        this.handleMove(message.getSender());
      }
    }

    private void handleMove(UserSession sender) {
      if (this.players.containsKey(sender)) {
        int playerNumber = this.players.get(sender);
        int score1 = this.scores[playerNumber] += 1;
        int score2 = this.scores[playerNumber == 0 ? 1 : 0];
        messagePlayers(this.getPlayers(), new ScoreUpdateMessage(sender, score1));
        if (score1 > this.winMin && score1 - score2 >= this.winBy) {
          messagePlayers(this.getPlayers(), new GameOverMessage(sender));
          // TODO: handle game end
        }
      }
    }

    @Override
    protected void onPlayerJoin(UserSession player) {

    }

    @Override
    protected void onPlayerLeave(UserSession player) {

    }
  }
}

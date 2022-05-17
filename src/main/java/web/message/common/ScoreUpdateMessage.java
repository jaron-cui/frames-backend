package web.message.common;

import session.UserSession;
import web.message.OutgoingMessage;

import java.util.HashMap;
import java.util.Map;

public class ScoreUpdateMessage extends OutgoingMessage {
  private final Map<String, Integer> scores;

  public ScoreUpdateMessage() {
    super("score-update");
    this.scores = new HashMap<>();
  }

  public ScoreUpdateMessage(UserSession player, int score) {
    this();
    this.addScore(player, score);
  }

  public void addScore(UserSession player, int score) {
    this.scores.put(player.getId(), score);
  }

  public Map<String, Integer> getScores() {
    return this.scores;
  }
}

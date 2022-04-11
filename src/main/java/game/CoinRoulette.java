package game;

import game.common.message.PlayerMove;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CoinRoulette extends Game<PlayerMove> {
  private Set<String> players;
  private Map<String, Integer> scores;
  private final int winMin, winBy;
  private final Random random;
  private boolean inProgress;

  public CoinRoulette(int winMin, int winBy, String... players) {
    super(PlayerMove.class);
    this.players = new HashSet<>(Arrays.asList(players));
    this.winMin = winMin;
    this.winBy = winBy;
    this.random = new Random();
    this.inProgress = false;
  }

  @Override
  public void onMessage(PlayerMove move) {
    if (!this.inProgress() || this.isOver()) {
      return;
    }

    String player = move.getPlayer();
    if (random.nextBoolean() && this.players.contains(player)) {
      this.scores.put(player, this.scores.get(player) + 1);
      if (this.isOver()) {
        this.end();
      }
    }
  }

  @Override
  public void start() {
    for (String player : this.players) {
      this.scores.put(player, 0);
    }
    this.inProgress = true;
  }

  @Override
  public void end() {
    this.inProgress = false;
  }

  @Override
  public boolean inProgress() {
    return this.inProgress;
  }

  @Override
  public boolean isOver() {
    return this.getWinner() != null;
  }

  @Override
  public String getWinner() {
    String firstPlayer = null, secondPlayer = null;
    int firstScore = 0, secondScore = 0;
    for (String player : this.players) {
      int score = this.scores.get(player);
      if (firstPlayer == null || score > firstScore) {
        secondPlayer = firstPlayer;
        secondScore = firstScore;
        firstPlayer = player;
        firstScore = score;
      } else if (secondPlayer == null || score > secondScore) {
        secondPlayer = player;
        secondScore = score;
      }
    }

    if (firstScore > this.winMin && firstScore > secondScore + this.winBy) {
      return firstPlayer;
    } else {
      return null;
    }
  }
}

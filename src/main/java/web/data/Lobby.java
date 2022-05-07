package web.data;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
  private final List<String> players;

  public Lobby() {
    this.players = new ArrayList<>();
  }

  public List<String> getPlayers() {
    return this.players;
  }

  public void addPlayer(String player) {
    this.players.add(player);
  }
}

package web.service;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
  private final List<String> players;

  public Lobby() {
    this.players = new ArrayList<>();
  }

  public void addPlayer(String playerSessionId) {
    if (!players.contains(playerSessionId)) {
      this.players.add(playerSessionId);
    }

  }

  public List<String> getPlayers() {
    return this.players;
  }
}

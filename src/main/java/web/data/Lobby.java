package web.data;

import web.DoubleMap;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
  private final String lobbyId;
  private final List<String> players;

  public Lobby(String lobbyId) {
    this.lobbyId = lobbyId;
    this.players = new ArrayList<>();
  }

  public String getLobbyId() {
    return lobbyId;
  }

  public List<String> getPlayers() {
    return this.players;
  }

  public void addPlayer(String player) {
    this.players.add(player);
  }
}

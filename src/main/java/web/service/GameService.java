package web.service;

import game.Chess;
import game.Game;
import session.Lobby;
import session.UserSession;

import java.util.HashMap;
import java.util.Map;

public class GameService {
  private final Map<String, Game> gameSelection = initGameMap(new Chess());

  private static Map<String, Game> initGameMap(Game... games) {
    Map<String, Game> map = new HashMap<>();
    for (Game game : games) {
      map.put(game.getName(), game);
    }

    return map;
  }

  public GameService() {

  }

  public void createGame(String game, UserSession creator) {
    if (!this.gameSelection.containsKey(game)) {
      throw new IllegalArgumentException("Invalid game.");
    }

    creator.putInRoom(new Lobby(gameSelection.get(game)));
  }
}

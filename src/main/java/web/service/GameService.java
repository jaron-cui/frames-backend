package web.service;

import game.ChessGameHandler;
import game.GameHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameService {
  // map of game id to game handler
  private final static Map<String, GameHandler> gameHandlers = new HashMap<>();
  // map of player session id to game id
  private final static Map<String, String> gameSessions = new HashMap<>();
  // TODO: implement support for credential requirement/profile-tied games
  @PostMapping("/create/{game}")
  @ResponseBody
  public GameCreationResponse create(@PathVariable String game) {
    GameHandler gameHandler = new ChessGameHandler();
    String gameId = String.valueOf(gameHandlers.size());
    String playerSessionId = String.valueOf(gameSessions.size());
    gameHandlers.put(gameId, gameHandler);
    gameSessions.put(playerSessionId, gameId);

    GameCreationResponse response = new GameCreationResponse();
    response.setGameId(gameId);
    response.setPlayerSessionId(playerSessionId);
    System.out.println("Created game " + gameId + " with player " + playerSessionId + ".");
    return response;
  }

  @PostMapping("/join/{gameId}")
  @ResponseBody
  public String join(@PathVariable String gameId) {
    if (gameHandlers.containsKey(gameId)) {
      String playerSessionId = String.valueOf(gameSessions.size());
      gameSessions.put(playerSessionId, gameId);
      System.out.println("New player " + playerSessionId + " joined game " + gameId);
      return playerSessionId;
    } else {
      throw new InvalidGameSessionException();
    }
  }

  private static class GameCreationResponse {
    private String gameId;
    private String playerSessionId;

    public String getGameId() {
      return gameId;
    }

    public void setGameId(String gameId) {
      this.gameId = gameId;
    }

    public String getPlayerSessionId() {
      return playerSessionId;
    }

    public void setPlayerSessionId(String playerSessionId) {
      this.playerSessionId = playerSessionId;
    }
  }
}

package web.service;

import game.ChessGameHandler;
import game.GameHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import web.SessionManager;
import web.data.Lobby;
import web.message.LobbyMessage;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameService {
  private final SessionManager sessionManager;
  private final Map<String, Lobby> lobbies;

  public GameService() {
    this.sessionManager = new SessionManager();
    this.lobbies = new HashMap<>();
  }

  @PostMapping("/createLobby/{game}")
  public void createLobby(@RequestHeader("sessionId") String sessionId, @PathVariable String game) {
    this.checkSession(sessionId);

    String lobbyId = Integer.toString(this.lobbies.size());
    Lobby lobby = new Lobby(lobbyId);
    lobby.addPlayer(sessionId);
    this.lobbies.put(lobbyId, lobby);
    this.sessionManager.sendMessage(sessionId, new LobbyMessage.Assignment(lobby));
  }

  @PostMapping("/join/{lobbyId}")
  public void join(@RequestHeader("sessionId") String sessionId, @PathVariable String lobbyId) {
    this.checkSession(sessionId);
    Lobby lobby = this.lobbies.get(lobbyId);
    for (String player : lobby.getPlayers()) {
      this.sessionManager.sendMessage(player, new LobbyMessage.PlayerJoined(sessionId));
    }
    lobby.addPlayer(sessionId);
    this.sessionManager.sendMessage(sessionId, new LobbyMessage.Assignment(lobby));
  }

  private void checkSession(String sessionId) {
    if (!this.sessionManager.isActiveSession(sessionId)) {
      throw new UnauthorizedException("Missing or invalid sessionId.");
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

package web.service;

import game.ChessGameHandler;
import game.GameHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.SessionManager;
import web.data.Lobby;
import web.message.LobbyMessage;
import web.message.WebSocketMessage;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameService {
  private final SessionManager sessionManager;
  private final Map<String, GameHandler> gameHandlers;
  public static final Map<String, GameHandler> sessionToGameHandler = new HashMap<>();

  public GameService() {
    this.sessionManager = new SessionManager();
    this.gameHandlers = new HashMap<>();
  }

  @PostMapping("/create/{game}")
  public void create(@RequestHeader("sessionId") String sessionId, @PathVariable String game) {
    this.checkSession(sessionId);

    // TODO: make better lobbyId system with randomized characters
    String gameId = Integer.toString(this.gameHandlers.size());
    Lobby lobby = new Lobby(gameId);
    this.gameHandlers.put(gameId, new ChessGameHandler(lobby));

    this.addPlayerToLobby(gameId, sessionId);
  }

  @PostMapping("/join/{lobbyId}")
  public void join(@RequestHeader("sessionId") String sessionId, @PathVariable String lobbyId) {
    this.checkSession(sessionId);
    if (!this.gameHandlers.containsKey(lobbyId)) {
      throw new NotFoundException("Invalid lobby.");
    }

    this.addPlayerToLobby(lobbyId, sessionId);
  }

  private void checkSession(String sessionId) {
    if (!this.sessionManager.isActiveSession(sessionId)) {
      throw new UnauthorizedException("Invalid session.");
    }
  }

  private void messageLobby(Lobby lobby, WebSocketMessage message) {
    for (String player : lobby.getPlayers()) {
      this.sessionManager.sendMessage(player, message);
    }
  }

  private void addPlayerToLobby(String gameId, String sessionId) {
    Lobby lobby = this.gameHandlers.get(gameId).getLobby();

    // tell the lobby a new player has joined and then add the player
    this.messageLobby(lobby, new LobbyMessage.PlayerJoined(sessionId));
    lobby.addPlayer(sessionId);

    // tell the player that they have been assigned to the lobby
    this.sessionManager.sendMessage(sessionId, new LobbyMessage.Assignment(lobby));
    sessionToGameHandler.put(sessionId, this.gameHandlers.get(gameId));
    this.gameHandlers.get(gameId).start();
  }
}

package web.controller;

import game.chess.ChessGameHandler;
import org.springframework.beans.factory.annotation.Autowired;
import web.data.GameHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.service.SessionManager;
import web.config.NotFoundException;
import web.config.UnauthorizedException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameController {
  private final SessionManager sessionManager;
  private final Map<String, GameHandler> gameHandlers;
  public static final Map<String, GameHandler> sessionToGameHandler = new HashMap<>();

  @Autowired
  public GameController() {
    this.sessionManager = new SessionManager();
    this.gameHandlers = new HashMap<>();
  }

  @PostMapping("/create/{game}")
  public void create(@RequestHeader("sessionId") String sessionId, @PathVariable String game) {
    this.checkSession(sessionId);

    // TODO: make better gameId system with randomized characters
    String gameId = Integer.toString(this.gameHandlers.size());
    this.gameHandlers.put(gameId, new ChessGameHandler(gameId));

    this.addPlayerToGame(gameId, sessionId);
  }

  @PostMapping("/join/{gameId}")
  public void join(@RequestHeader("sessionId") String sessionId, @PathVariable String gameId) {
    this.checkSession(sessionId);
    if (!this.gameHandlers.containsKey(gameId)) {
      throw new NotFoundException("Invalid lobby.");
    }

    this.addPlayerToGame(gameId, sessionId);
  }

  private void checkSession(String sessionId) {
    if (!this.sessionManager.isActiveSession(sessionId)) {
      throw new UnauthorizedException("Invalid session.");
    }
  }

  private void addPlayerToGame(String gameId, String sessionId) {
    GameHandler gameHandler = this.gameHandlers.get(gameId);
    gameHandler.addPlayer(sessionId);
    sessionToGameHandler.put(sessionId, gameHandler);
  }
}

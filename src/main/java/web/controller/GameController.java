package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import session.RoomManager;
import session.SessionManager;
import session.UserSession;
import web.config.error.UnauthorizedException;
import web.service.GameService;

@RestController
@RequestMapping("/game")
public class GameController {
  private final SessionManager sessionManager;
  private final RoomManager roomManager;
  private final GameService gameService;

  @Autowired
  public GameController() {
    this.sessionManager = SessionManager.getInstance();
    this.roomManager = RoomManager.getInstance();
    this.gameService = new GameService();
  }

  @PostMapping("/create/{game}")
  public void create(@RequestHeader("sessionId") String sessionId, @PathVariable String game) {
    this.checkSession(sessionId);

    this.gameService.createGame(game, this.sessionManager.getSession(sessionId));
  }

  @PostMapping("/join/{gameId}")
  public void join(@RequestHeader("sessionId") String sessionId, @PathVariable String gameId) {
    this.checkSession(sessionId);

    // TODO: add better checking and stuff
    UserSession session = this.sessionManager.getSession(sessionId);
    session.putInRoom(this.roomManager.getRoom(gameId));
  }

  private void checkSession(String sessionId) {
    if (this.sessionManager.getSession(sessionId) == null) {
      throw new UnauthorizedException("Invalid session.");
    }
  }
}

package session;

import game.Game;
import game.settings.Settings;
import util.Data;
import web.message.IncomingMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Lobby extends Room {
  public static final String READY = "ready";
  public static final String UNREADY = "unready";
  public static final String UPDATE_SETTING = "updateSetting";

  private final Game game;
  private final Settings config;
  private final Set<UserSession> ready;

  public Lobby(Game game) {
    super("lobby");
    this.game = game;
    this.config = new Settings(new HashMap<>());//game.getConfigTemplate();
    this.ready = new HashSet<>();
  }

  private void start() {
    game.createRoom(this.config, this.getPlayers());
    this.close();
  }

  @Override
  protected void handleCustomMessage(IncomingMessage message) {
    switch (message.getType()) {
      case READY:
        this.ready.add(message.getSender());
        if (this.config.completed() && this.ready.size() == this.getPlayers().size()) {
          this.start();
        }
        break;
      case UNREADY:
        this.ready.remove(message.getSender());
        break;
      case UPDATE_SETTING:
        break;
      default:
        break;
    }
  }

  @Override
  protected void onPlayerJoin(UserSession player) {

  }

  @Override
  protected void onPlayerLeave(UserSession player) {
    this.ready.remove(player);
  }

  @Override
  public Map<String, Object> retrieveFields() {
    Map<String, Object> fields = super.retrieveFields();
    fields.put("ready", Data.map(this.ready, UserSession::getId));

    return fields;
  }
}

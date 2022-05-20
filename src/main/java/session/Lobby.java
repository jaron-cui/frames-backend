package session;

import game.common.Game;
import game.common.Roster;
import game.common.settings.Settings;
import util.Data;
import web.message.IncomingMessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Lobby<T extends Roster> extends Room {
  public static final String READY = "ready";
  public static final String UNREADY = "unready";
  public static final String UPDATE_SETTING = "updateSetting";

  private final Game<T> game;
  private final Settings config;
  private final T roster;
  private final Set<UserSession> ready;

  public Lobby(Game<T> game) {
    super("lobby");
    this.game = game;
    this.roster = game.getRoster();
    this.config = new Settings(new HashMap<>());//game.getConfigTemplate();
    this.ready = new HashSet<>();
  }

  private void start() {
    if (!this.roster.completed()) {
      throw new IllegalStateException("Cannot start game; roster incomplete.");
    }
    game.createRoom(this.config, this.roster);
    this.close();
  }

  @Override
  protected void handleCustomMessage(IncomingMessage message) {
    switch (message.getType()) {
      case READY:
        this.ready.add(message.getSender());
        if (this.ready.size() == this.getUsers().size()
            && this.config.completed() && this.roster.completed()) {
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
  protected void addUser(UserSession user) {
    super.addUser(user);
    this.roster.addUser(user);
  }

  @Override
  protected void removeUser(UserSession user) {
    super.removeUser(user);
    this.ready.remove(user);
    this.roster.remove(user);
  }

  @Override
  public Map<String, Object> retrieveFields() {
    Map<String, Object> fields = super.retrieveFields();
    fields.put("ready", Data.map(this.ready, UserSession::getId));
    fields.put("game", this.game.getName());

    return fields;
  }
}

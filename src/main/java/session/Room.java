package session;

import util.Data;
import util.FieldProvider;
import web.message.IncomingMessage;
import web.message.OutgoingMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class Room implements Iterable<UserSession>, FieldProvider {
  public static final String CHAT = "chatMessage";
  public static final String EMOTE = "emote";

  private final String type;
  private final String id;
  private final List<UserSession> players;

  protected Room(String type) {
    this(type, new ArrayList<>());
  }

  protected Room(String type, List<UserSession> players) {
    this.type = type;
    this.id = UUID.randomUUID().toString();
    this.players = new ArrayList<>();

    RoomManager.getInstance().addRoom(this);
    for (UserSession player : players) {
      player.putInRoom(this);
    }
  }

  public String getType() {
    return this.type;
  }

  public String getId() {
    return this.id;
  }

  public List<UserSession> getPlayers() {
    return new ArrayList<>(this.players);
  }

  public void close() {
    for (UserSession player : this) {
      if (player.getRoom() == this) {
        player.kick();
      }
    }

    RoomManager.getInstance().removeRoom(this.id);
  }

  protected static void messagePlayers(Collection<UserSession> players, OutgoingMessage message) {
    for (UserSession player : players) {
      player.sendMessage(message);
    }
  }

  public void handleMessage(IncomingMessage message) {
    switch (message.getType()) {
      case CHAT:
        // TODO: put a message in chat!
        break;
      case EMOTE:
        // TODO: maybe implement emotes?
        break;
      default:
        break;
    }

    this.handleCustomMessage(message);
  }

  protected abstract void handleCustomMessage(IncomingMessage message);

  protected void addPlayer(UserSession player) {
    this.players.add(player);
    this.onPlayerJoin(player);
  }

  protected void removePlayer(UserSession player) {
    this.players.remove(player);
    this.onPlayerLeave(player);
  }

  protected abstract void onPlayerJoin(UserSession player);

  protected abstract void onPlayerLeave(UserSession player);

  @Override
  public Map<String, Object> retrieveFields() {
    Map<String, Object> fields = new HashMap<>();
    fields.put("type", this.type);
    fields.put("id", this.id);
    fields.put("players", Data.map(this.players, UserSession::getId));

    return fields;
  }

  @Override
  public Iterator<UserSession> iterator() {
    return new ArrayList<>(this.players).iterator();
  }
}

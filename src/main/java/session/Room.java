package session;

import util.Data;
import util.FieldProvider;
import web.message.IncomingMessage;
import web.message.OutgoingMessage;
import web.message.common.Assignment;
import web.message.common.PlayerJoined;
import web.message.common.PlayerLeft;

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
  private final List<UserSession> users;

  protected Room(String type) {
    this(type, new ArrayList<>());
  }

  protected Room(String type, List<UserSession> users) {
    this.type = type;
    this.id = UUID.randomUUID().toString();
    this.users = new ArrayList<>();

    RoomManager.getInstance().addRoom(this);
    for (UserSession user : users) {
      user.putInRoom(this);
    }
  }

  public String getType() {
    return this.type;
  }

  public String getId() {
    return this.id;
  }

  public List<UserSession> getUsers() {
    return new ArrayList<>(this.users);
  }

  public void close() {
    for (UserSession user : this) {
      if (user.getRoom() == this) {
        user.kick();
      }
    }

    RoomManager.getInstance().removeRoom(this.id);
  }

  protected static void messageUsers(Collection<UserSession> users, OutgoingMessage message) {
    for (UserSession player : users) {
      player.sendMessage(message);
    }
  }

  protected void messageAll(OutgoingMessage message) {
    messageUsers(this.users, message);
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

  protected void addUser(UserSession user) {
    messageAll(new PlayerJoined(user));
    this.users.add(user);
    user.sendMessage(new Assignment(this));
  }

  protected void removeUser(UserSession user) {
    this.users.remove(user);
    messageAll(new PlayerLeft(user));
  }

  @Override
  public Map<String, Object> retrieveFields() {
    Map<String, Object> fields = new HashMap<>();
    fields.put("type", this.type);
    fields.put("id", this.id);
    fields.put("players", Data.map(this.users, UserSession::getId));

    return fields;
  }

  @Override
  public Iterator<UserSession> iterator() {
    return new ArrayList<>(this.users).iterator();
  }
}

package session;

import java.util.HashMap;
import java.util.Map;

public class RoomManager {
  private static final RoomManager instance = new RoomManager();
  private final Map<String, Room> rooms = new HashMap<>();

  private RoomManager() {

  }

  protected void addRoom(Room room) {
    this.rooms.put(room.getId(), room);
  }

  protected void removeRoom(String id) {
    this.rooms.remove(id);
  }

  public Room getRoom(String id) {
    return this.rooms.get(id);
  }

  public static RoomManager getInstance() {
    return instance;
  }
}

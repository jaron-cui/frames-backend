package session;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import util.Data;
import web.message.IncomingMessage;
import web.message.OutgoingMessage;
import web.message.common.Assignment;

import java.io.IOException;

public class UserSession {
  private final WebSocketSession session;
  private final String id;
  private Room room;

  protected UserSession(WebSocketSession session) {
    this.session = session;
    this.id = session.getId();
    this.room = null;
  }

  public String getId() {
    return this.id;
  }

  public Room getRoom() {
    return this.room;
  }

  public void putInRoom(Room room) {
    if (this.room != null) {
      this.room.removeUser(this);
    }

    if (room == null) {
      this.sendMessage(new Assignment(null));
    } else {
      room.addUser(this);
    }
    this.room = room;
  }

  public void kick() {
    this.putInRoom(null);
  }

  public void sendMessage(OutgoingMessage message) {
    try {
      this.session.sendMessage(new TextMessage(Data.serialize(message)));
    } catch (IOException e) {
      //throw new RuntimeException("Could not send message.");
      // log this error somewhere
    }
  }

  public void acceptMessage(IncomingMessage message) {
    message.setSender(this);
    this.room.handleMessage(message);
  }

  public void close() {
    this.room.removeUser(this);
    SessionManager.getInstance().removeSession(this);
    try {
      this.session.close();
    } catch (IOException e) {
      // TODO: log this
    }
  }
}

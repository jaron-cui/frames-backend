package game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import web.data.Lobby;

public abstract class GameHandler {
  protected final Lobby lobby;

  public GameHandler(Lobby lobby) {
    this.lobby = lobby;
  }

  public abstract void acceptMessage(String sessionId, String message);

  public abstract void start();

  public abstract Object getGameState();

  protected <T> T deserialize(String message, Class<T> format) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(message, format);
    } catch (JsonProcessingException e) {
      throw new InvalidMessageException("Invalid message structure.");
    }
  }

  public Lobby getLobby() {
    return this.lobby;
  }
}

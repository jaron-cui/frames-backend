package game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public abstract class GameHandler<GameType> {
  protected Messenger messenger;

  public GameHandler() {

  }

  public abstract void acceptMessage(Message message);

  public abstract Object getGameState();

  public void registerMessenger(Messenger messenger) {
    this.messenger = messenger;
  }

  protected <T> T deserialize(String message, Class<T> format) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(message, format);
    } catch (JsonProcessingException e) {
      throw new InvalidMessageException("Invalid message structure.");
    }
  }
}

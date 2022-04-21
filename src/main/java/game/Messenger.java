package game;

public interface Messenger {
  void sendMessage(String playerSessionId, Object message);
}

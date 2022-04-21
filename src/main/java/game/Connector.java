package game;

public class Connector<Message> {
  // TODO: class is incomplete - hook up to websocket to allow for actual communication handling
  private final DeprecatedGame<Message> game;

  public Connector(DeprecatedGame<Message> game) {
    this.game = game;
    this.game.registerConnector(this);
  }

  public void onMessage(Message message) {
    this.game.onMessage(message);
  }

  public void sendResponse(Response response) {
    for (String recipient : response.getRecipients()) {
      // TODO: see message at top of class
    }
  }
}

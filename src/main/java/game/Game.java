package game;

public abstract class Game<Message> {
  protected Connector<Message> connector;
  protected final Class<Message> responseClass;

  protected Game(Class<Message> responseClass) {
    this.responseClass = responseClass;
  }

  public abstract void onMessage(Message move);
  public abstract void start();
  public abstract void end();
  public abstract boolean inProgress();
  public abstract boolean isOver();
  public abstract String getWinner();

  public Class<?> getResponseClass() {
    return this.responseClass;
  }

  public void registerConnector(Connector<Message> connector) {
    this.connector = connector;
  }
}

package game;

public class InvalidMessageException extends IllegalArgumentException {
  public InvalidMessageException(String message) {
    super(message);
  }
}

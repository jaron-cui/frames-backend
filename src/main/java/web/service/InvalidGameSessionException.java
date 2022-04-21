package web.service;

public class InvalidGameSessionException extends RuntimeException {
  public InvalidGameSessionException() {
    super("Invalid game session.");
  }
}

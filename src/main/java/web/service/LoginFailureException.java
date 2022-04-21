package web.service;

public class LoginFailureException extends RuntimeException {
  public LoginFailureException() {
    super("Login failed.");
  }
}

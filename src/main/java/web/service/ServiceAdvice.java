package web.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ServiceAdvice {
  @ResponseBody
  @ExceptionHandler(LoginFailureException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public String loginFailure(Exception e) {
    return e.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(InvalidGameSessionException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String notFound(Exception e) {
    return e.getMessage();
  }
}

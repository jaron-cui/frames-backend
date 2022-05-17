package web.config.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

@ControllerAdvice
public class ServiceAdvice {
  @ResponseBody
  @ExceptionHandler(HttpException.class)
  public void exception(HttpException e, HttpServletResponse response) throws IOException {
    response.setStatus(e.getStatus().value());
    response.getWriter().write(e.getMessage());
  }
}

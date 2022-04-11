package websocket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/d")
public class Http {
  public Http() {

  }

  @GetMapping("/get")
  public String get() {
    return "HI there!";
  }

  @GetMapping("/startSession")
  public String getNewSessionKey() {
    return null;
  }
}

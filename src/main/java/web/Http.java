package web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/d")
public class Http {
  @PostMapping(path="/get",produces= MediaType.APPLICATION_JSON_VALUE)
  public String get() {
    return "HI there!";
  }

  @GetMapping("/startSession")
  public String getNewSessionKey() {
    return null;
  }
}

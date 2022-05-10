package web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
  @RequestMapping("/healthcheck")
  @ResponseBody
  public String healthCheck() {
    return "{\"text\":\"Hello there, the application is running!\"}";
  }
}

package web.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginService {
  private final static Map<String, String> passwords = initUsers();
  private final static Map<String, String> userSessions = new HashMap<>();

  private static Map<String, String> initUsers() {
    Map<String, String> passwords = new HashMap<>();
    passwords.put("admin", "password");
    return passwords;
  }

  @PostMapping("/login")
  @ResponseBody
  public String login(@RequestHeader("username") String username, @RequestHeader("password") String password) {
    System.out.println("Login attempted with credentials: " + username + ", " + password);
    if (passwords.containsKey(username) && password.equals(passwords.get(username))) {
      // return existing session
      for (String session : userSessions.keySet()) {
        if (userSessions.get(session).equals(username)) {
          return session;
        }
      }
      // create new session
      String jwt = String.valueOf(userSessions.size());
      userSessions.put(jwt, username);
      return jwt;
    } else {
      throw new LoginFailureException();
    }
  }
}

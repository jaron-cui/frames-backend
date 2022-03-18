import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    Map<String, String> parameters = getParameters(exchange);
    if (parameters == null) {
      return;
    }
    String response = "Fail";
    switch(exchange.getRequestMethod()) {
      case "GET":
        response = get(parameters);
        break;
      case "POST":
        break;
      default:
        break;
    }
    respond(exchange, response);
  }

  private static void respond(HttpExchange exchange, String response) throws IOException {
    exchange.sendResponseHeaders(200, response.length());
    OutputStream out = exchange.getResponseBody();
    out.write(response.getBytes());
    out.flush();
    out.close();
  }

  private static String get(Map<String, String> parameters) {
    if (parameters.containsKey("name")) {
      return "{\"result\":\"Jaron\"}";
    } else {
      System.out.println(parameters.toString());
      return "\"None\"";
    }
  }

  private static Map<String, String> getParameters(HttpExchange exchange) {
    String query = exchange.getRequestURI().toString().split("/")[1];
    System.out.println(query);
    if(query == null) {
      return null;
    }
    Map<String, String> result = new HashMap<>();
    for (String param : query.split("&")) {
      String[] entry = param.split("=");
      if (entry.length > 1) {
        result.put(entry[0], entry[1]);
      }else{
        result.put(entry[0], "");
      }
    }
    return result;
  }
}

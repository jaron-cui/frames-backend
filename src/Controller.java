import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Controller {
  public static void main(String[] args) {
    HttpServer server;
    try {
      server = HttpServer.create(new InetSocketAddress("localhost", 8000), 0);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    server.createContext("/test", new RequestHandler());
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    server.setExecutor(threadPoolExecutor);
    server.start();
    System.out.println(" Server started on port 8001");
  }
}

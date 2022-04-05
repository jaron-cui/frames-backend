package game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Response {
  private Object message;
  private Set<String> recipients;

  public Response() {
    this(null);
  }

  public Response(Object message, String... recipients) {
    this.message = message;
    this.recipients = new HashSet<>(Arrays.asList(recipients));
  }

  public Object getMessage() {
    return this.message;
  }

  public Set<String> getRecipients() {
    return this.recipients;
  }

  public void setMessage(Object message) {
    this.message = message;
  }

  public void addRecipients(String... recipients) {
    this.recipients.addAll(Arrays.asList(recipients));
  }
}

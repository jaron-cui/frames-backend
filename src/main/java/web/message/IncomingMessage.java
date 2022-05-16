package web.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import session.UserSession;
import util.Data;

public class IncomingMessage {
  @JsonIgnoreProperties(value = "sender")
  private String type;
  private String content;
  private UserSession sender;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(Object content) {
    this.content = Data.serialize(content);
  }

  public UserSession getSender() {
    return this.sender;
  }

  public void setSender(UserSession senderId) {
    this.sender = senderId;
  }
}

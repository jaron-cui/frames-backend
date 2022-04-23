package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Data {
  public static <T> T deserialize(String data, Class<T> form) {
    try {
      return new ObjectMapper().readValue(data, form);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Could not deserialize data:\n" + data);
    }
  }

  public static String serialize(Object o) {
    try {
      return new ObjectMapper().writeValueAsString(o);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Could not serialize object: " + o);
    }
  }
}

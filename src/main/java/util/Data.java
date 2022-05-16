package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
      throw new RuntimeException("Could not serialize object: " + e.getMessage());
    }
  }

  public static <T, R> Collection<R> map(Collection<T> list, Function<T, R> function) {
    return list.stream().map(function).collect(Collectors.toList());
  }
}

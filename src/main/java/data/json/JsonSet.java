package data.json;

import java.util.Arrays;
import java.util.HashSet;

public class JsonSet extends JsonCollection {
  public JsonSet(JsonObject... elements) {
    super("{", "}", new HashSet<>(Arrays.asList(elements)).toArray(new JsonObject[]{}));
  }
}

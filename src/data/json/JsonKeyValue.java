package data.json;

public class JsonKeyValue implements JsonObject {
  private final JsonValue key;
  private final JsonObject value;

  public JsonKeyValue(String key, String value) {
    this(new JsonString(key), new JsonString(value));
  }

  public JsonKeyValue(String key, double value) {
    this(new JsonString(key), new JsonNumber(value));
  }

  public JsonKeyValue(JsonValue key, JsonObject value) {
    this.key = key;
    this.value = value;
  }

  public JsonValue key() {
    return this.key;
  }

  public JsonObject value() {
    return this.value;
  }
}

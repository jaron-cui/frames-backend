package data.json;

public class JsonString implements JsonValue {
  private final String value;

  public JsonString(String value) {
    this.value = "\"" + value + "\"";
  }

  @Override
  public String toString() {
    return this.value;
  }
}

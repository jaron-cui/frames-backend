package data.json;

public class JsonNumber implements JsonValue {
  private final String value;

  public JsonNumber(int value) {
    this(Integer.toString(value));
  }

  public JsonNumber(double value) {
    this(Double.toString(value));
  }

  public JsonNumber(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}

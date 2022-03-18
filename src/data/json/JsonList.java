package data.json;

public class JsonList extends JsonCollection {
  public JsonList(JsonObject... elements) {
    super("[", "]", elements);
  }
}

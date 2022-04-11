package data.json;

import java.util.ArrayList;
import java.util.List;

public abstract class JsonCollection implements JsonObject {
  private final String open, close;
  protected final List<JsonObject> elements;

  protected JsonCollection(String open, String close, JsonObject... elements) {
    this.open = open;
    this.close = close;
    this.elements = new ArrayList<>(List.of(elements));
  }

  public void add(JsonObject element) {
    this.elements.add(element);
  }

  public List<JsonObject> getElements() {
    return new ArrayList<>(this.elements);
  }

  @Override
  public String toString() {
    StringBuilder json = new StringBuilder(this.open);
    List<JsonObject> elements = this.getElements();

    if (elements.size() > 0) {
      json.append(elements.remove(0).toString());
    }

    for (JsonObject element : this.getElements()) {
      json.append(",").append(element.toString());
    }

    json.append(this.close);
    return json.toString();
  }
}

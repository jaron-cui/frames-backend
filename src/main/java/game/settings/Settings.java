package game.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class Settings {
  private final Map<String, Field<?>> fields;

  public Settings(Map<String, Field<?>> fields) {
    this.fields = fields;
  }

  public <T> T getField(String identifier, Class<T> type) {
    this.checkIdentifier(identifier);
    try {
      return type.cast(this.fields.get(identifier).getValue());
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Incorrect type for field.");
    }
  }

  public void setField(String identifier, String value) {
    this.checkIdentifier(identifier);
    this.fields.get(identifier).setValue(value);
  }

  // TODO: implement along with settings implementations
  public boolean completed() {
    return true;
  }

  private void checkIdentifier(String identifier) {
    if (!this.fields.containsKey(identifier)) {
      throw new IllegalArgumentException("Invalid identifier.");
    }
  }

  private static class Builder {
    private final Map<String, Field<?>> fields;

    public Builder() {
      this.fields = new HashMap<>();
    }

    public <T> void addField(
        String name, Function<String, T> fromString, Predicate<T> condition, T defaultValue) {
      if (this.fields.containsKey(name)) {
        throw new IllegalArgumentException("A field with the name '" + name + "' already exists.");
      }

      this.fields.put(name, new Field<>(fromString, condition, defaultValue));
    }

    public Settings build() {
      Map<String, Field<?>> copy = new HashMap<>();
      for (String identifier : fields.keySet()) {
        copy.put(identifier, fields.get(identifier).copy());
      }

      return new Settings(copy);
    }
  }

  private static class Field<T> {
    private final Function<String, T> converter;
    private final Predicate<T> predicate;
    private T value;

    protected Field(Function<String, T> converter, Predicate<T> predicate, T defaultValue) {
      this.converter = converter;
      this.predicate = predicate;
      this.value = defaultValue;
    }

    protected Field<T> copy() {
      return new Field<>(converter, predicate, this.value);
    }

    protected T getValue() {
      return this.value;
    }

    protected void setValue(String value) {
      try {
        T casted = this.converter.apply(value);
        if (this.predicate.test(casted)) {
          this.value = casted;
        } else {
          throw new IllegalArgumentException("Illegal value for field.");
        }
      } catch (Exception e) {
        throw new IllegalArgumentException("Incorrect type for value.");
      }
    }
  }
}

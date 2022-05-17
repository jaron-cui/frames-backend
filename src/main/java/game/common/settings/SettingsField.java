package game.common.settings;

public abstract class SettingsField<T> {
  private final String type;

  protected SettingsField(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }
}

package data.animation;

import data.json.JsonKeyValue;
import data.json.JsonSet;

public class Animation {
  private final Easing easing;
  private final double duration;

  public Animation(Easing easing, double duration) {
    this.easing = easing;
    this.duration = duration;
  }

  public Easing easing() {
    return this.easing;
  }

  public double duration() {
    return this.duration;
  }

  @Override
  public String toString() {
    JsonSet json = new JsonSet(
        new JsonKeyValue("type", this.easing().toString()),
        new JsonKeyValue("duration", this.duration()));
    return json.toString();
  }

  public enum Easing {
    CONSTANT, LINEAR, GRADUAL, BOUNCE
  }
}

package data.animation;

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
    return "{type:" + this.easing() + ",duration:" + Double.toString(this.duration()) + "}";
  }

  public enum Easing {
    CONSTANT, LINEAR, GRADUAL, BOUNCE
  }
}

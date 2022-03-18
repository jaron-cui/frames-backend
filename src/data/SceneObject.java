package data;

public class SceneObject {
  private final String id;
  private int x, y;
  private double theta;

  public SceneObject(String id) {
    this(id, 0, 0);
  }

  public SceneObject(String id, int x, int y) {
    this(id, x, y, 0);
  }

  public SceneObject(String id, int x, int y, double theta) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.theta = theta;
  }

  public boolean deleted() {
    return false;
  }

  public boolean hasUpdate() {
    return false;
  }

  public String nextUpdate() {
    return "";
  }
}

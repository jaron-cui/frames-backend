package game.chess.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
  public final int x, y;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public Position(@JsonProperty("x") int x, @JsonProperty("y") int y) {
    this.x = x;
    this.y = y;
  }

  @JsonIgnore
  public boolean isInBounds() {
    return this.x >= 0 && this.x < 8 && this.y >= 0 && this.y < 8;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Position)) {
      return false;
    }
    Position that = (Position) object;
    return this.x == that.x && this.y == that.y;
  }

  @Override
  public int hashCode() {
    return this.x * 13 + this.y * 17;
  }
}

package game.chess;

import game.chess.piece.Piece;

public class Move {
  private Piece.Color color;
  private Position from;
  private Position to;

  public Piece.Color getColor() {
    return color;
  }

  public void setColor(Piece.Color color) {
    this.color = color;
  }

  public Position getFrom() {
    return from;
  }

  public void setFrom(Position from) {
    this.from = from;
  }

  public Position getTo() {
    return to;
  }

  public void setTo(Position to) {
    this.to = to;
  }
}

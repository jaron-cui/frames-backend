package game.chess;

import game.Game;

public class Chess implements Game<ChessMove> {

  public Chess() {

  }

  @Override
  public void move(String player, ChessMove chessMove) {

  }

  @Override
  public void start() {

  }

  @Override
  public void end() {

  }

  @Override
  public boolean inProgress() {
    return false;
  }

  @Override
  public boolean isOver() {
    return false;
  }

  @Override
  public String getWinner() {
    return null;
  }
}

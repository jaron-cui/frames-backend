public interface Game<Move> {
  void move(String player, Move move);
  void start();
  void end();
  boolean inProgress();
  boolean isOver();
  String getWinner();
}

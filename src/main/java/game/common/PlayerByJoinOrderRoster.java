package game.common;

import session.UserSession;

public abstract class PlayerByJoinOrderRoster extends Roster {
  private final int playerCount;

  public PlayerByJoinOrderRoster(int playerCount) {
    this.playerCount = playerCount;
  }

  @Override
  protected Role assignNewRole(UserSession session) {
    if (this.getUsersByRole(Role.PLAYER).size() < playerCount) {
      return Role.PLAYER;
    } else {
      return Role.SPECTATOR;
    }
  }

  @Override
  public boolean completed() {
    return this.getUsersByRole(Role.PLAYER).size() == playerCount;
  }
}

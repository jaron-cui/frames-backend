package game.common;

import session.UserSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Roster {
  public enum Role {
    PLAYER, SPECTATOR
  }

  private final List<UserSession> joinOrder;
  private final Map<UserSession, Role> roles;
  private final Map<Role, List<UserSession>> roleGroups;

  public Roster() {
    this.joinOrder = new ArrayList<>();
    this.roles = new HashMap<>();
    this.roleGroups = new HashMap<>();
    for (Role role : Role.values()) {
      this.roleGroups.put(role, new ArrayList<>());
    }
  }

  public Role getRole(UserSession session) {
    return this.roles.get(session);
  }

  public List<UserSession> getUsersByRole(Role role) {
    return new ArrayList<>(this.roleGroups.get(role));
  }

  public List<UserSession> getUsers() {
    return new ArrayList<>(this.joinOrder);
  }

  public Role addUser(UserSession session) {
    Role role = this.assignNewRole(session);
    if (role == null) {
      return null;
    }

    this.joinOrder.add(session);
    this.roles.put(session, role);
    this.roleGroups.get(role).add(session);

    return role;
  }

  public void removeUser(UserSession session) {
    this.joinOrder.remove(session);
    this.roleGroups.get(this.roles.remove(session)).remove(session);
  }

  protected abstract Role assignNewRole(UserSession session);

  public abstract boolean completed();
}

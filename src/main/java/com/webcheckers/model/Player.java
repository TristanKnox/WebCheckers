package com.webcheckers.model;

import com.webcheckers.model.checkers.Piece.PieceColor;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the player of the game. Each player is defined by a unique name
 *
 * @author Collin Bolles
 */
public class Player {

  public enum Badge {
    EASY_AI_DEFEATED("⭐"),
    HARD_AI_DEFEATED("⭐⭐");

    private String val;

    Badge(String badgeVal) {
      this.val = badgeVal;
    }

    public String getVal() {
      return val;
    }
  }

  /** The unique name of the player **/
  private final String name;
  /** The list of badges that a given player has won */
  private List<Badge> badges;


  /**
   * Initialize a player with a unique name
   * @param name The name of the player
   */
  public Player(String name) {
    this.name = name;
    this.badges = new LinkedList<>();
  }


  /**
   * Get the name of the player
   * @return The name of the player
   */
  public String getName() {
    return name;
  }

  /**
   * Create a hash code of the player based on the players name
   * @return The hash code of the players name
   */
  @Override
  public int hashCode() {
    return name.hashCode();
  }

  /**
   * Two players are equal if they have the same username
   * @param other The other player to compare to
   * @return If the other object is a player with the same username
   */
  @Override
  public boolean equals(Object other) {
    if(this == other)
      return true;
    if(other instanceof Player)
      return this.hashCode() == other.hashCode();
    return false;
  }

  /**
   * Add the given badge to the players list of badges. If the player has an
   * easy badge and the hard badge is added, then the easy badge is removed first.
   * @param badge The badge to add to the player
   */
  public void addBadge(Badge badge) {
    if(badge == Badge.HARD_AI_DEFEATED) {
      badges.remove(Badge.EASY_AI_DEFEATED);
      badges.add(Badge.HARD_AI_DEFEATED);
    }
    if(badge == Badge.EASY_AI_DEFEATED && !badges.contains(Badge.HARD_AI_DEFEATED))
      badges.add(badge);
  }

  public String toString() {
    StringBuilder builder = new StringBuilder(name);
    for(Badge badge: badges) {
      builder.append(" ");
      builder.append(badge.getVal());
    }
    return builder.toString();
  }
}

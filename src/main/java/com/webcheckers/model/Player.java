package com.webcheckers.model;

import com.webcheckers.model.checkers.Piece.PieceColor;

import java.io.Serializable;

/**
 * Represents the player of the game. Each player is defined by a unique name
 *
 * @author Collin Bolles
 */
public class Player implements Serializable {
  /** The unique name of the player **/
  private final String name;


  /**
   * Initialize a player with a unique name
   * @param name The name of the player
   */
  public Player(String name) {
    this.name = name;
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
}

package com.webcheckers.model;

import com.webcheckers.model.checkers.Piece.PieceColor;

/**
 * Represents the player of the game. Each player is defined by a unique name
 *
 * @author Collin Bolles
 */
public class Player {
  /** The unique name of the player **/
  private final String name;


  /**
   * Initialize a player with a unique name
   * @param name The name of the player
   */
  public Player(String name) {
    this.name = name;
  }


  public String getName() {
    return name;
  }
  
  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if(this == other)
      return true;
    if(other instanceof Player)
      return this.hashCode() == other.hashCode();
    return false;
  }
}

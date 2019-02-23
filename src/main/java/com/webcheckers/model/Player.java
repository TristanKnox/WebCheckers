package com.webcheckers.model;

import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Piece.PieceType;

/**
 * Represents the player of the game. Each player is defined by a unique name
 *
 * @author Collin Bolles
 */
public class Player {
  /** The unique name of the player **/
  private final String name;

  private PieceColor color;

  /**
   * Initialize a player with a unique name
   * @param name The name of the player
   */
  public Player(String name, PieceColor color) {
    this.name = name;
    this.color = color;
  }

  public String getName() {
    return name;
  }

  public PieceColor getColor() {
    return color;
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

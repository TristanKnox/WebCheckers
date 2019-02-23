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
   * @param color The color of the player
   */
  public Player(String name, PieceColor color) {
    this.name = name;
    this.color = color;
  }

  /**
   * Initialize a player with a name and no color. Used before a player is
   * put into a game
   * @param name The name of the player
   */
  public Player(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public PieceColor getColor() {
    return color;
  }

  public void setColor(PieceColor color) {
    this.color = color;
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

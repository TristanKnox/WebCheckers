package com.webcheckers.model.checkers;

/**
 * Represents a piece on the board as well as the type of piece that it is
 *
 * @author Collin Bolles
 */
public class Piece {

  /**
   * Represents the types of pieces, either single or king
   */
  public enum PieceType {
    SINGLE,
    KING
  }

  /**
   * Represents the color of the piece, either red or white
   */
  public enum PieceColor {
    RED,
    WHITE
  }

  /** The type of the piece **/
  private PieceType type;
  /** The color of the piece **/
  private PieceColor color;

  /**
   * Initializes the piece with a type and color
   * @param type The type of the piece
   * @param color The color of the piece
   */
  public Piece(PieceType type, PieceColor color) {
    this.type = type;
    this.color = color;
  }

  /**
   * Get the type of the piece either king or single
   * @return The type of the piece
   */
  public PieceType getType() {
    return type;
  }

  /**
   * Get the color of the piece either red or white
   * @return The color of the piece
   */
  public PieceColor getColor() {
    return color;
  }
}
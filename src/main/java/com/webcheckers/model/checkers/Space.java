package com.webcheckers.model.checkers;

import com.webcheckers.model.checkers.Piece;

import java.io.Serializable;

/**
 * Each space represents a single location on the board. It also keeps track of the piece currently
 * on it as well as if the space is a valid location to move to.
 *
 * @author Collin Bolles
 */
public class Space implements Serializable {

  /**
   * Represents the two types of spaces, white or black
   */
  public enum SpaceType {
    WHITE,
    BLACK
  }

  /** The column number of the space **/
  private int cellIdx;
  /** The piece on this space, null if no piece on this space **/
  private Piece piece;
  /** The type of space either white or black **/
  private SpaceType type;

  /**
   * Creates a Space object with a given column id, piece (null if none) and move validity
   * @param cellIdx The column number of the space
   * @param piece The piece on this space (null if none)
   * @param type The type of space either white or black
   */
  public Space(int cellIdx, Piece piece, SpaceType type) {
    this.cellIdx = cellIdx;
    this.piece = piece;
    this.type = type;
  }

  /**
   * Copy constructor
   * @param original - the space to copy
   */
  public Space(Space original){
    this.cellIdx = original.getCellIdx();
    if(original.getPiece() != null)
      this.piece = new Piece(original.getPiece());
    this.type = original.getType();
  }

  /**
   * Get the current column number of this space
   * @return the current column number
   */
  public int getCellIdx() {
    return cellIdx;
  }

  /**
   * Get the current piece on this space (null if none)
   * @return the piece on this space
   */
  public Piece getPiece() {
    return piece;
  }

  /**
   * Used to set the piece of this space.
   * @param piece The new piece for this space, null if no piece in this space
   */
  public void setPiece(Piece piece) {
    this.piece = piece;
  }

  /**
   * Return the validity of the space
   * Needs to be a black space and not already have a piece on it
   * @return if this move is valid
   */
  public boolean isValid() {
    return type == SpaceType.BLACK && piece == null;
  }

  /**
   * A getter method for the space type
   * @return - type of this space
   */
  public SpaceType getType(){ return this.type; }
}

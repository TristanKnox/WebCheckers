package com.webcheckers.model.checkers;

import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Piece.PieceType;
import com.webcheckers.model.checkers.Space.SpaceType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing the row which contains 8 spaces. The row is used to
 * maintain the state of 8 spaces
 *
 * @author Collin Bolles
 */
public class Row implements Iterable<Space> {
  /** Each row has 8 spaces **/
  private static final int MAX_SIZE = 8;

  /** Spaces on this row **/
  private List<Space> spaces;
  /** The id of this row starting from 0 **/
  private int index;

  /**
   * Creates a row object and initializes itself via the initializeSpaces method
   * @param index The id of the row
   */
  public Row(int index) {
    this.index = index;
    spaces = new ArrayList<>();
    initializeSpaces();
  }

  /**
   * Creates a row object with predefined order of spaces
   * @param spaces list of spaces in the desired order
   * @param rowIndex - index of row
   */
  public Row(List<Space> spaces, int rowIndex){
    this.spaces = spaces;
    this.index = rowIndex;
  }

  /**
   * Used to check if a given space should be initialized with a piece.
   * @param index The row number of the space to check
   * @param col The column number of the space to check
   * @return True if the space should be initialized with a piece false if not
   */
  private boolean initSpaceWithPiece(int index, int col) {
    // Middle rows are not initialized with piece
    if(index == 3 || index == 4)
      return false;
    // Non middle even rows have every odd column initialized with a piece
    else if(index % 2 == 0 && col % 2 != 0)
      return true;
    // Non middle odd rows have every even column initialized with a piece
    return index % 2 != 0 && col % 2 == 0;
  }

  /**
   * Checks if a given space is a black space
   * @param index The number of the row to check
   * @param col The column number to check
   * @return True if the space is black, false if it is not
   */
  private boolean isSpaceBlack(int index, int col) {
    return index - col % 2 == 0;
  }

  /**
   * Initialize the spaces with the appropriate spaces initialized with spaces
   */
  private void initializeSpaces() {
    PieceColor color = index < 3 ? PieceColor.RED : PieceColor.WHITE;

    for(int col = 0; col < MAX_SIZE; col++) {
      Space space = null;
      if(initSpaceWithPiece(index, col))
        space = new Space(col, new Piece(PieceType.SINGLE, color), SpaceType.BLACK);
      else
        space = new Space(col, null,
            isSpaceBlack(index, col) ? SpaceType.BLACK : SpaceType.WHITE);
      spaces.add(space);
    }
  }

  /**
   * The iterator needed for the client UI, the iterator returns the iterator of spaces
   * @return The iterator from the spaces list
   */
  @Override
  public Iterator<Space> iterator() {
    return spaces.iterator();
  }

  /**
   * Get the list of spaces
   * @return The list of spaces
   */
  public List<Space> getSpaces() {
    return spaces;
  }

  /**
   * Get the index of the current row
   * @return The index of the row
   */
  public int getIndex() {
    return index;
  }
}

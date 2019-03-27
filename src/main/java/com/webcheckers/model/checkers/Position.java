package com.webcheckers.model.checkers;

/**
 * This class represents a position on the board
 */
public class Position {

  private int row;
  private int cell;

  /**
   * Constructor - creates a new instance of Position
   * @param row - the row of the position
   * @param cell - the cell of the position
   */
  public Position(int row, int cell){
    this.row = row;
    this.cell = cell;
  }

  /**
   * Checks to see if this instance of Position is equivalent ot the given object
   * @param obj - the object to compare with this
   * @return true if equal false if not equal
   */
  public boolean equals(Object obj) {
    if(this == obj)
      return true;
    if(obj instanceof Position) {
      Position other = (Position) obj;
      return this.row == other.row && this.cell == other.cell;
    }
    return false;
  }

  /**
   * Getter method for row
   * @return - row
   */
  public int getRow(){ return row; }

  /**
   * Getter method for cell
   * @return - cell
   */
  public int getCell(){ return cell; }

  /**
   * Overridden toString method
   * @return position string
   */
  public String toString(){
    return "row: " + row + ", cell: " + cell;
  }
}

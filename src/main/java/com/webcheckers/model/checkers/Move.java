package com.webcheckers.model.checkers;

/**
 * This class repersents a move from one position to another
 */
public class Move {

  private Position start;
  private Position end;

  /**
   * Constructor - creates a new Move object
   * @param start - the position the piece is moving from
   * @param end - the position the piece is moving to
   */
  public Move(Position start, Position end){
    this.start = start;
    this.end = end;
  }

  /**
   * Getter method for the start position
   * @return - start position
   */
  public Position getStart(){ return start; }

  /**
   * Getter method for end position
   * @return - end position
   */
  public Position getEnd(){ return end; }

  /**
   * Checks to see if this instance of Move is equivilant ot the given object
   * @param obj - the object to compare with this
   * @return true if equal false if not equal
   */
  public boolean equals(Object obj) {
    if(this == obj)
      return true;
    if(obj instanceof Position) {
      Move other = (Move) obj;
      return this.start == other.start && this.end == other.end;
    }
    return false;
  }

  /**
   * overriden toString
   * @return - the string that represents this object
   */
  public String toString(){
    return "Start: " + start.toString() + "\nEnd: " + end.toString();
  }
}

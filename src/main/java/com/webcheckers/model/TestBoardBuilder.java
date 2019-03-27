package com.webcheckers.model;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece;
import com.webcheckers.model.checkers.Row;
import com.webcheckers.model.checkers.Space;

import java.util.List;

/**
 * This class is for creating customized boards for the purpose of setting specific testing scenarios
 */

public class TestBoardBuilder {

  public enum BoardType{
    KING_PIECE,
    MULTY_JUMP,
    KING_MULTY_JUMP
  }


  /**
   * This static method is used to setup the desired board state. I requires a list of rows with spaces to be created
   * @param rows - the list of rows that represents a normal game
   * @param type - the type of game synario to be set up
   * @return - an alterd list of rows and spaces representing the desired setup
   */
  public static List<Row> getTestBoard(List<Row> rows, BoardType type){
    switch(type) {
      case KING_PIECE:
        rows = kingSetUp(rows);
        break;
      case MULTY_JUMP:
        rows = multyJumpSetUp(rows);
        break;
      case KING_MULTY_JUMP:
        rows = kingJumpSetUp(rows);
        break;
    }
    return rows;
  }

  /**
   * Sets up the board so that one of each color is one space away from beeing kinged
   * @parm rows - the rows to be alterd
   * @return - a list of rows that represents the new board setup
   */
  private static List<Row> kingSetUp(List<Row> rows){
    rows = removeAllPieces(rows);
    rows.get(6).getSpace(3).setPiece(new Piece(Piece.PieceType.SINGLE, Piece.PieceColor.RED));
    rows.get(1).getSpace(4).setPiece(new Piece(Piece.PieceType.SINGLE, Piece.PieceColor.WHITE));
    return rows;
  }

  /**
   * Sets up a board with a multy jump available in one direction
   * @param rows - a standard board set up
   * @return - the new board set up
   */
  private static List<Row> multyJumpSetUp(List<Row> rows){
    rows = removeAllPieces(rows);
    rows.get(1).getSpace(2).setPiece(new Piece(Piece.PieceType.SINGLE, Piece.PieceColor.RED));
    int col = 3;
    for(int row = 2; row < 5; row +=2){
        rows.get(row).getSpace(col).setPiece(new Piece(Piece.PieceType.SINGLE, Piece.PieceColor.WHITE));
        col +=2;
    }
    return rows;
  }

  /**
   * Sets up the board with a red king and some white pies to jump in both directions
   * @param rows - the rows that should be updated to represent the new board state
   * @return - the updated rows
   */
  private static List<Row> kingJumpSetUp(List<Row> rows){
    rows = multyJumpSetUp(rows);
    rows.get(1).getSpace(2).setPiece(new Piece(Piece.PieceType.KING, Piece.PieceColor.RED));
    rows.get(2).getSpace(5).setPiece(new Piece(Piece.PieceType.SINGLE, Piece.PieceColor.WHITE));
    return rows;
  }

  /**
   * Remove all pieces from the board
   * @param rows - the board with the pieces on it
   * @return - the new board
   */
  private static List<Row> removeAllPieces(List<Row> rows){
    for(Row row: rows)
      for(Space space: row)
        space.setPiece(null);
    return rows;
  }
}

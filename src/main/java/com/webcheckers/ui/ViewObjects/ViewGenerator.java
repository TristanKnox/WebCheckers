package com.webcheckers.ui.ViewObjects;

import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece;
import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Row;
import com.webcheckers.model.checkers.Space;

import java.util.*;

public class ViewGenerator {
  /**
  * Orients the view of the board for each player
  * @param game -  the game being played
  * @return an list of rows in the order to be renderd made up of spaces in the order they should be renderd in
  */
  public static List<Row> getView(Game game, PieceColor color){
    List<Row> board = game.getCopyRows();
    // If player one return the board as is
    if(color == Piece.PieceColor.WHITE){
      return board;
    }
    // Other wise revers rows and spaces
    return reverseRows(game);
  }

  /**
  * Reverses the rows of the board
  * @param board - the game board that is to be reversed
  * @return a List of reversed rows
  */
  private static List<Row> reverseRows(Game board){
    List<Row> reverse = board.getCopyRows();
    List<Row> finalBoard = new ArrayList<Row>();
    Collections.reverse(reverse);
    for(int i = 0; i < reverse.size(); i++) {
      Row row = reverse.get(i);
      finalBoard.add(i, reverseSpaces(row));
    }
    return finalBoard;
  }


    /**
     * Reverses the order of spaces in a row
     * @param row - the row of spaces to be reversed
     * @return -  a new row representing the reversed spaces
     */
    private static Row reverseSpaces(Row row){
        List<Space> spaces = new ArrayList<Space>(row.getSpaces());
        Collections.reverse(spaces);
        Row reversed = new Row(spaces, row.getIndex());
        return reversed;
    }
}

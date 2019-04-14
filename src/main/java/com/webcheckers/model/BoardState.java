package com.webcheckers.model;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece;
import com.webcheckers.model.checkers.Row;

import java.io.Serializable;
import java.util.List;

public class BoardState implements Serializable {

  private List<Row> rows;
  private Piece.PieceColor activePlayer;
  private boolean gameOver;
  private Game.EndGameCondition endGameCondition;

  /**
  * Constructor
  * @param game - the game with the state to store
  */
  public BoardState(Game game){
    this.rows = game.getCopyRows();
    this.activePlayer = game.getActiveColor();
    this.gameOver = game.isGameOver();
    this.endGameCondition = game.getEndGameCondition();
  }

  /**
   * Gets the rows
   * @return - rows
   */
  public List<Row> getRows(){ return rows; }

  /**
   * Gets the activePlayer
   * @return - activePlayer
   */
  public Piece.PieceColor getActivePlayer(){ return activePlayer; }

  public boolean isGameOver(){ return gameOver; }
  public Game.EndGameCondition getEndGameCondition(){ return endGameCondition; }

  public void setEndGameCondition(Game.EndGameCondition condition){
    this.endGameCondition = condition;
    this.gameOver = true;
  }
}

package com.webcheckers.model;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Turn;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *A class which is a less cumbersome version of a game,
 * used to render replays of games which have already been played.
 *
 * @author Andrew Bado, Tristan Knox, Evan Nolan
 */
public class Replay implements Serializable {

  // attributes
  private int id;
  private Player player1;
  private Player player2;
  private List<BoardState> boardStateList;
  private int currentTurnIndex;

  /**
   * constructor 1
   * runs through a replayed game's turns to get the board state to make a replay
   * @param game the game to be turned into a replay.
   */
  public Replay(Game game){
    player1 = game.getRedPlayer();
    player2 = game.getWhitePlayer();
    boardStateList = new ArrayList<>();
    List<Turn> turnList = game.getTurnList();
    id = game.getID();
    convertTurnsToBoardStates(turnList);
    BoardState lastState = boardStateList.get(boardStateList.size()-1);
    lastState.setEndGameCondition(game.getEndGameCondition());
    currentTurnIndex = 0;
  }

  /**
   * Copy Constructor
   * @param replay -  the replay to copy
   */
  public Replay(Replay replay){
    this.boardStateList = replay.getBoardStateList();
    this.currentTurnIndex = replay.getCurrentTurnIndex();
    this.id = replay.getId();
    this.player1 = replay.getPlayer1();
    this.player2 = replay.getPlayer2();
  }

  /**
   * runs through a given turn list and stores the game
   * @param turnList the turn list
   */
  private void convertTurnsToBoardStates(List<Turn> turnList){
    Game newGame = new Game(player1,player2, BoardBuilder.BoardType.STANDARD);
    storeBoardState(newGame);
    for (int index = 0; index < turnList.size(); index ++) {
      Turn turn = turnList.get(index);
      turn.execute(newGame);
      newGame.toggleActiveColor();
      newGame.checkEndGame();
      storeBoardState(newGame);
    }
  }

  /**
<<<<<<< HEAD
   * Stores a boardState of the current state of the given game
   * @param game - the game to store the state of
=======
   * takes a game and stores its board as a boardstate
   * @param game
>>>>>>> ffc8863adafbf5e280217d7aea018ccde08dbbee
   */
  private void storeBoardState(Game game){
    BoardState boardState = new BoardState(game);
    boardStateList.add(boardState);
  }

  /**
<<<<<<< HEAD
   * Getter for playerOne
   * @return - playerOne
=======
   * returns player 1
   * @return player1
>>>>>>> ffc8863adafbf5e280217d7aea018ccde08dbbee
   */
  public Player getPlayer1(){return player1;}

  /**
<<<<<<< HEAD
   * Getter for playerTwo
   * @return - playerTwo
=======
   * returns player 2
   * @return player2
>>>>>>> ffc8863adafbf5e280217d7aea018ccde08dbbee
   */
  public Player getPlayer2(){return player2;}

  /**
<<<<<<< HEAD
   * Getter for Id
   * @return - id
=======
   * returns the unique id for this replay
   * @return id
>>>>>>> ffc8863adafbf5e280217d7aea018ccde08dbbee
   */
  public int getId(){return id;}

  /**
   * returns the turn number which this replay is currently on
   * @return currentTurnIndex
   */
  public int getCurrentTurnIndex(){ return currentTurnIndex; }

  public void resetReplay(){
    this.currentTurnIndex = 0;
  }

  public List<BoardState> getBoardStateList(){ return boardStateList; }

  /**
   * Gets the total number of turns in a replay
   * @return the total number of boardstates
   */
  public int getTotalTurns(){
    return boardStateList.size();
  }

  public boolean isEndOfGame(){
    return currentTurnIndex == getTotalTurns()-1;
  }

  public boolean isBeginingOfGame(){
    return currentTurnIndex == 0;
  }

  /**
   *the hash code function
   * @return the hash
   */
  @Override
  public int hashCode(){
    return id;
  }

  /**
   * gets the next board state
   * @return the next board state
   */
  public BoardState getNextBoardState(){
    currentTurnIndex++;
    return getBoardState(currentTurnIndex);
  }

  /**
   * gets the previous board state
   * @return the previous board state
   */
  public BoardState getPreviousBoardState(){
    currentTurnIndex--;
    return getBoardState(currentTurnIndex);
  }


  public BoardState getCurrentBoardState(){ return getBoardState(currentTurnIndex); }


  /**
   * gets the board state at a given turn
   * @param turn the TURRRN
   * @return the board state
   */
  private BoardState getBoardState(int turn) {
    return boardStateList.get(turn);
  }

  /**
   * does the game have a next board state?
   * @return
   */
  public boolean hasNext(){
    return !isEndOfGame();
  }

  /**
   * does the game have a previous?
   * @return
   */
  public boolean hasPrev(){
    return !isBeginingOfGame();
  }

  /**
   *
   */
  @Override
  public String toString(){
    return getPlayer1().getName() + " vs. " + getPlayer2().getName() + ": " + (boardStateList.size()-1) + " Turns";
  }
}

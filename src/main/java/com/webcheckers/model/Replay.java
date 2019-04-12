package com.webcheckers.model;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Turn;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Replay {
  private static int replayCount = 0;
  private int id;
  private Player player1;
  private Player player2;
  private List<BoardState> boardStateList;
  private int currentTurnIndex;

  /**
   * runs through a replayed games turns to get the board state to making a replay
   * @param game the game to be turned into a replay.
   */
  public Replay(Game game){
    player1 = game.getRedPlayer();
    player2 = game.getWhitePlayer();
    boardStateList = new ArrayList<>();
    List<Turn> turnList = game.getTurnList();
    synchronized (Replay.class) {

      replayCount++;
    }
    convertTurnsToBoardStates(turnList);
    currentTurnIndex =0;
    boardStateList.get(currentTurnIndex);
  }

  /**
   * runs through a given turn list and stores the game
   * @param turnList the turn list
   */
  private void convertTurnsToBoardStates(List<Turn> turnList){
    Game newGame = new Game(player1,player2, BoardBuilder.BoardType.STANDARD);
    storeBoardState(newGame);
    for (Turn turn : turnList) {
        turn.execute(newGame);
        storeBoardState(newGame);
    }
  }
  private void storeBoardState(Game game){
    BoardState boardState = new BoardState(game);
    boardStateList.add(boardState);
  }

  public Player getPlayer1(){return player1;}

  public Player getPlayer2(){return player2;}

  public int getId(){return id;}

//  public List<Turn> getTurnList(){return turnStates;}

  /**
   * Gets the total number of turns in a replay
   * @return the total number of boardstates
   */
  public int getTotalTurns(){
    return boardStateList.size();
  }

  public boolean isEndOfGame(){
    return currentTurnIndex == getTotalTurns();
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
    return getPlayer1().getName() + " vs. " + getPlayer2().getName() + ": " + boardStateList.size() + " Turns";
  }
}

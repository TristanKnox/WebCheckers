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
  private List<BoardState> boardStates;
  private int currentTurnIndex;

  /**
   * runs through a replayed games turns to get the board state to making a replay
   * @param game the game to be turned into a replay.
   */
  public Replay(Game game){
    player1 = game.getRedPlayer();
    player2 = game.getWhitePlayer();
    boardStates = new ArrayList<>();
    List<Turn> turnList = game.getTurnList();
    synchronized (Replay.class) {

      replayCount++;
    }
    convertTurnsToBoardStates(turnList);
    currentTurnIndex =0;
    boardStates.get(currentTurnIndex);
  }

  /**
   * runs through a given turn list and stores the game
   * @param turnList
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
    boardStates.add(boardState);
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
    return boardStates.size();
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

  public BoardState getNextBoardState(){
    currentTurnIndex++;
    return getBoardState(currentTurnIndex);
  }
  public BoardState getPreviousBoardState(){
    currentTurnIndex--;
    return getBoardState(currentTurnIndex);
  }
  private BoardState getBoardState(int turn) {
    return boardStates.get(turn);
  }

  /**
   *
   */
  @Override
  public String toString(){
    return getPlayer1().getName() + " vs. " + getPlayer2().getName() + ": " + boardStates.size() + " Turns";
  }
}

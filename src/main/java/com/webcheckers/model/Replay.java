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
      id = replayCount;
      replayCount++;
    }
    convertTurnsToBoardStates(turnList);
    currentTurnIndex = 0;
    boardStateList.get(currentTurnIndex);
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
    boardStateList.add(boardState);
  }

  public Player getPlayer1(){return player1;}

  public Player getPlayer2(){return player2;}

  public int getId(){return id;}

  public List<BoardState> getTurnList(){return boardStateList;}

  public BoardState getNextBoardState(){
    currentTurnIndex++;
    return boardStateList.get(currentTurnIndex);
  }

  /**
   *
   * @return
   */
  @Override
  public int hashCode(){
    return id;
  }

  /**
   *
   */
  @Override
  public String toString(){
    return getPlayer1().getName() + " vs. " + getPlayer2().getName();
  }
}

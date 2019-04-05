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
  private List<BoardState> turnStates;
  private int currentTurnIndex;

  /**
   * runs through a replayed games turns to get the board state to making a replay
   * @param game the game to be turned into a replay.
   */
  public Replay(Game game){
    player1 = game.getRedPlayer();
    player2 = game.getWhitePlayer();
    List<Turn> turnList = game.getTurnList();
    synchronized (Replay.class) {
      id = replayCount;
      replayCount++;
    }
    runThroughTurns(turnList);
    currentTurnIndex =0;
    turnStates.get(currentTurnIndex);
  }

  /**
   * runs through a given turn list and stores the game
   * @param turnList
   */
  private void runThroughTurns(List<Turn> turnList){
    Game game = new Game(player1,player2);
    storeBoardState(game);
    for (Turn turn: turnList) {
      turn.execute(game);
      storeBoardState(game);
    }
  }
  private void storeBoardState(Game game){
    BoardState boardState = new BoardState(game);
    turnStates.add(boardState);
  }

  public Player getPlayer1(){return player1;}

  public Player getPlayer2(){return player2;}

  public int getId(){return id;}

  public List<BoardState> getTurnList(){return turnStates;}

  /**
   *
   * @return
   */
  @Override
  public int hashCode(){
    return id;
  }
  public BoardState getNextBoardState(){

    currentTurnIndex++;
    return turnStates.get(currentTurnIndex);
  }
}

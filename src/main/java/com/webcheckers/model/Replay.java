package com.webcheckers.model;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Turn;
import java.util.List;

public class Replay {
  static int replayCount = 0;
  int id;
  private Player player1;
  private Player player2;
  private List<Turn> turnList;

  public Replay(Game game){
    player1 = game.getRedPlayer();
    player2 = game.getWhitePlayer();
    turnList = game.getTurnList();
    synchronized (Replay.class) {
      id = replayCount;
      replayCount++;
    }
  }

  @Override
  public int hashCode(){
    return id;
  }
}

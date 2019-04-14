package com.webcheckers.model;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Model Tier")
public class ReplayTest {

  private Player player1;
  private Player player2;

  private Game game;
  private List<Turn> turnList;
  private Replay replay;
  @BeforeEach
  public void setup(){
    player1 = mock(Player.class);
    player2 = mock(Player.class);
    game = mock(Game.class);
    turnList = new ArrayList<>();
    for(int i = 0; i < 4; i++){
      turnList.add(mock(Turn.class));
    }
    when(game.getRedPlayer()).thenReturn(player1);
    when(game.getWhitePlayer()).thenReturn(player2);


  }

  @Test
  public void testConstructorOne(){
    when(game.getTurnList()).thenReturn(turnList);
    replay = new Replay(game);
    assert(replay != null);
    assert (replay.getPlayer1().equals(player1));
    assert(replay.getPlayer2().equals(player2));
    assert(replay.getCurrentBoardState() instanceof BoardState);

  }

  @Test
  public void TestConstuctorTwo(){
    when(game.getTurnList()).thenReturn(turnList);
    Replay replayOther = new Replay(game);
    replay = new Replay(replayOther);
    assert(replay != null);
    assert (replay.getPlayer1().equals(player1));
    assert(replay.getPlayer2().equals(player2));
    assert(replay.getCurrentBoardState() instanceof BoardState);
    assert(replayOther.getId() == replay.getId());
    assertTrue(replayOther.hasNext());
    assertFalse(replay.hasPrev());
  }

  @Test
  public void testNextPrevious(){
    when(game.getTurnList()).thenReturn(turnList);
    replay = new Replay(game);
    assertTrue(replay.hasNext());
    assertFalse(replay.hasPrev());
    BoardState stateOne = replay.getCurrentBoardState();
    replay.getNextBoardState();
    BoardState stateTwo = replay.getCurrentBoardState();
    assertTrue(replay.hasPrev());
    assert(stateOne != stateTwo);
    replay.getPreviousBoardState();
    assertFalse(replay.hasPrev());
  }


}

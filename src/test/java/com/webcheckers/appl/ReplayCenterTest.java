package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.model.checkers.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReplayCenterTest {
  private ReplayCenter CuT;
  private Game game;
  private Player watcher;
  private String watcherName = "Tim";
  private Replay replay;

  @BeforeEach
  public void setUp(){
    CuT = new ReplayCenter();
    game = mock(Game.class);
    watcher = mock(Player.class);
    replay = mock(Replay.class);
    when(watcher.getName()).thenReturn(watcherName);
  }
  @Test
  public void testCtor(){
    assertNotNull(new ReplayCenter());
  }
  public void testStoreReplay(){
    CuT.storeReplay(game);
    assertTrue(CuT.hasReplays());
  }

  @Test
  public void testStartReplay(){
    when(game.getID()).thenReturn(5);
    CuT.storeReplay(game);
    CuT.startReplay(watcher,5);
    assertNotNull(CuT.getReplay(watcher));
    assert(!CuT.getReplay(watcher).equals(CuT.getReplay(game.getID())));

  }

  @Test
  public  void endReplay(){
    when(game.getID()).thenReturn(5);
    CuT.storeReplay(game);
    CuT.startReplay(watcher,5);
    assertNotNull(CuT.getReplay(watcher));
    CuT.endReplay(watcher);
    assert(CuT.getReplay(watcher) == null);
  }

}

package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.checkers.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReplayCenterTest {
  private ReplayCenter CuT;
  private Game game;

  @BeforeEach
  public void setUp(){
    CuT = new ReplayCenter();
    game = mock(Game.class);
  }

  /**
   * tests construction
   */
  @Test
  public void testCtor(){
    assertNotNull(new ReplayCenter());
  }

  /**
   * test the store replay function
   */
  @Test
  public void testStoreReplay(){
    CuT.storeReplay(game);
    assertTrue(CuT.hasReplays());
  }

}

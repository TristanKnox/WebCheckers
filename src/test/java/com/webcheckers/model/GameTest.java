package com.webcheckers.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.checkers.Game;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * handles the Unit testing of the game
 * @author Evan Nolan
 */
@Tag("Model Tier")
public class GameTest {

  //Constructor Test
  @Test
  public void testCtor(){
    Player p1 = mock(Player.class);
    Player p2 = mock(Player.class);
    Game game = new Game(p1,p2);
    assertNotNull(game);
  }
  @Test
  public void testPlayer(){

  }




}

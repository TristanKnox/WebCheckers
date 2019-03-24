package com.webcheckers.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * handles the Unit testing of the game
 * @author Evan Nolan
 */
@Tag("Model Tier")
public class GameTest {
  private Game game;
  private Player p1;
  private Player p2;
  @BeforeEach
  public void setup(){
    p1 = mock(Player.class);
    p2 = mock(Player.class);
    game = new Game(p1,p2);
  }
  //Constructor Test
  @Test
  public void testCtor(){
    Player p1 = mock(Player.class);
    Player p2 = mock(Player.class);
    Game game = new Game(p1,p2);
    assertNotNull(game);
  }

  @Test
  public void testStartingTurn(){
    assertEquals(game.getActivateColor(), PieceColor.RED);
    assertEquals(game.getWhitePlayer(), p2);
    assertFalse(game.isGameOver());
  }
  @Test
  public void testTurnSwitch(){


  }
  @Test
  public void testResign(){

  }
  @Test
  public void testValidMove(){

  }
  @Test
  public void testBackUpMove(){

  }
  @Test
  public void testResignationEnabler(){

  }
  @Test
  public void testEndGame(){

  }



}

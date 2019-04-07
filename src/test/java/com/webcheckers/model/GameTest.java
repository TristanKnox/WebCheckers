package com.webcheckers.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Move;
import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Turn;
import java.util.List;
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
  private Turn turn;
  private List<Turn> turns;

  /**
   * sets up the things to test for the game class.
   */
  @BeforeEach
  public void setup(){
    p1 = mock(Player.class);
    p2 = mock(Player.class);
    turn = mock(Turn.class);
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

  /**
   * Tests the starting game conditons
   *
   */
  @Test
  public void testStartingTurn(){
    assertEquals(game.getActiveColor(), PieceColor.RED);
    assertEquals(game.getRedPlayer(), p1);
    assertEquals(game.getWhitePlayer(), p2);
    assertFalse(game.isGameOver());
  }
  @Test
  /**
   * Tests the switching of turns.
   */
  public void testTurnSwitch(){
    testStartingTurn();
    turn.addMove(game, mock(Move.class));
    game.flipActiveColor();
    assertEquals(game.getActiveColor(), PieceColor.WHITE);
    assertNotEquals(game.getActiveColor(), (PieceColor.RED));
  }

  /**
   * test of getters
   */
  @Test
  public void testGetRows(){
    assertNotNull(game.getCopyRows());
  }

  /**
   * tests player Color getter.
   */
  @Test
  public void testGetPlayerColor(){
    PieceColor playerColor = game.getPlayerColor(p1);
    assertEquals(PieceColor.RED, playerColor );
  }

}

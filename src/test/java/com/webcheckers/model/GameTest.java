package com.webcheckers.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Move;

import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Position;
import com.webcheckers.model.checkers.Turn;
import java.util.List;
import javafx.geometry.Pos;
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
  private Move move;
  public Position start = new Position(0,2);
  public Position end = new Position(1,3);

  /**
   * sets up the things to test for the game class.
   */
  @BeforeEach
  public void setup(){
    p1 = mock(Player.class);
    p2 = mock(Player.class);
    turn = mock(Turn.class);
    game = new Game(p1,p2, BoardBuilder.BoardType.STANDARD);
  }
  //Constructor Test
  @Test
  public void testCtor(){
    Player p1 = mock(Player.class);
    Player p2 = mock(Player.class);
    Game game = new Game(p1,p2, BoardBuilder.BoardType.STANDARD);
    assertNotNull(game);
  }

  /**
   * Tests the starting game conditions
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


//  @Test
//  public void testTurnsAdded(){
//
//
//  }
//  @Test
//  public void testValidMove(){
//
//  }
//  @Test
//  public void testBackUpMove(){
//
//  }
//  @Test
//  public void testResignationEnabler(){
//
//  }


  /**
   * test of getters
   */
  @Test
  public void testGetRows(){
    assertNotNull(game.getCopyRows());
  }


  @Test
  public void testEndGame(){

    //Out of Pices
    game = new Game(p1,p2, BoardBuilder.BoardType.OUT_OF_MOVES);
    assertNull(game.getOutOfPieces());
    game = new Game(p1,p2,BoardBuilder.BoardType.OUT_OF_PIECES);
    Move move = new Move(new Position(3,2), new Position(5,4));
    game.addMove(move);
    game.executeTurn();
    game.checkEndGame();
    assertTrue(game.isGameOver());
    assertEquals(game.getEndGameCondition(), Game.EndGameCondition.WHITE_OUT_OF_PIECES);

    //Out of  Moves
    game = new Game(p1,p2, BoardBuilder.BoardType.OUT_OF_MOVES);
    game.checkEndGame();
    assertNull(game.getEndGameCondition());
    assertFalse(game.isGameOver());
    game.addMove(new Move(new Position(0,1), new Position(1,2)));
    game.executeTurn();
    game.checkEndGame();
    assertTrue(game.isGameOver());
    assertEquals(game.getEndGameCondition(), Game.EndGameCondition.WHITE_OUT_OF_MOVES, "Expected White to be out of Pieces but got " + game.getEndGameCondition());

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

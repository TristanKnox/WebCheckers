package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Handles testing the submission of the turn execution. Handles verification
 * that moves are available to execute and that the correct player is requesting the
 * execution
 *
 * @author Collin Bolles
 */
@Tag("UI-tier")
public class PostSubmitTurnRouteTest {

  private Request request;
  private Session session;
  private GameCenter gameCenter;
  private Game game;
  private Gson gson;
  private PostSubmitTurnRoute CuT;

  /**
   * Automatically setups the tests with mocks for required objects.
   */
  @BeforeEach
  public void setup() {
    request = mock(Request.class);
    session = mock(Session.class);
    gameCenter = mock(GameCenter.class);
    game = mock(Game.class);
    when(request.session()).thenReturn(session);
    when(session.attribute(any())).thenReturn(null);
    when(gameCenter.getGame(any())).thenReturn(game);
    gson = new Gson();
    CuT = new PostSubmitTurnRoute(gson, gameCenter);
  }

  /**
   * Make sure that an error message is returned when the incorrect player attempts
   * to execute a turn
   */
  @Test
  public void notPlayersTurn() {
    when(game.getActivateColor()).thenReturn(PieceColor.RED);
    when(game.getPlayerColor(any())).thenReturn(PieceColor.WHITE);
    String expectedValue = gson.toJson(PostSubmitTurnRoute.NOT_PLAYERS_TURN);
    String actualValue = (String) CuT.handle(request, null);
    assertEquals(expectedValue, actualValue);
  }

  /**
   * Make sure that a correct player maki
   */
  @Test
  public void correctPlayer() {
    when(game.getActivateColor()).thenReturn(PieceColor.RED);
    when(game.getPlayerColor(any())).thenReturn(PieceColor.RED);
    String expectedValue = gson.toJson(PostSubmitTurnRoute.NO_MOVES_TO_EXECUTE);
    String actualValue = (String) CuT.handle(request, null);
    assertEquals(expectedValue, actualValue);
  }

  @Test
  public void noMoveToExecute() {
    when(game.getActivateColor()).thenReturn(PieceColor.RED);
    when(game.getPlayerColor(any())).thenReturn(PieceColor.RED);
    when(game.currentTurnHasMove()).thenReturn(false);
    String expectedValue = gson.toJson(PostSubmitTurnRoute.NO_MOVES_TO_EXECUTE);
    String actualValue = (String) CuT.handle(request, null);
    assertEquals(expectedValue, actualValue);
  }

  @Test
  public void successfulMoveExecution() {
    when(game.getActivateColor()).thenReturn(PieceColor.RED);
    when(game.getPlayerColor(any())).thenReturn(PieceColor.RED);
    when(game.currentTurnHasMove()).thenReturn(true);
    String expectedValue = gson.toJson(PostSubmitTurnRoute.TURN_EXECUTED);
    String actualValue = (String) CuT.handle(request, null);
    assertEquals(expectedValue, actualValue);
  }
}

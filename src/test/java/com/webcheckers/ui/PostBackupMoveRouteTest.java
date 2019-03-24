package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece.PieceColor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import spark.Request;
import spark.Session;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Handles testing for the backup move. Ensures that errors are sent if the incorrect player
 * requests to backup a move or if there is not move to backup
 *
 * @author Collin Bolles
 */
public class PostBackupMoveRouteTest {

  private Request request;
  private Session session;
  private GameCenter gameCenter;
  private Game game;

  Gson gson;
  PostBackupMoveRoute CuT;

  /**
   * Handles the logic for setting up the mocked objects.
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
    when(game.getActivateColor()).thenReturn(PieceColor.RED);

    gson = new Gson();
    CuT = new PostBackupMoveRoute(gson, gameCenter);
  }

  /**
   * Test when there is no move to be undone, an error message should be returned
   */
  @Test
  public void testNoMove() {
    when(game.currentTurnHasMove()).thenReturn(false);
    String expectedValue = gson.toJson(PostBackupMoveRoute.NO_MOVE_TO_BACKUP);
    String actualValue = (String) CuT.handle(request, null);
    assertEquals(expectedValue, actualValue);
  }

  /**
   * Test when the wrong player attempts to undo a move
   */
  @Test
  public void testWrongPlayer() {
    when(game.currentTurnHasMove()).thenReturn(true);
    when(game.getPlayerColor(any())).thenReturn(PieceColor.WHITE);
    String expectedValue = gson.toJson(PostBackupMoveRoute.WRONG_PLAYER);
    String actualValue = (String) CuT.handle(request, null);
    assertEquals(expectedValue, actualValue);
  }

  /**
   * Test when a valid backup request is made. An info message should be returned
   */
  @Test
  public void testValidBackup() {
    when(game.currentTurnHasMove()).thenReturn(true);
    when(game.getPlayerColor(any())).thenReturn(PieceColor.RED);
    String expectedValue = gson.toJson(PostBackupMoveRoute.BACKUP_SUCCESSFUL);
    String actualValue = (String) CuT.handle(request, null);
    assertEquals(expectedValue, actualValue);
  }
}

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
 * Handles testing for the PostCheckTurnRoute for when it is a player's turn and when it is not
 * a player's turn
 */
public class PostCheckTurnRouteTest {
  Request request;
  Session session;
  GameCenter gameCenter;
  Game game;
  Gson gson;
  PostCheckTurnRoute CuT;

  /**
   * Handle initial setup which mocks each object with the needed methods
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
    CuT = new PostCheckTurnRoute(gson, gameCenter);
  }

  /**
   * Checks to make sure that a message with true is returned with the request player is the
   * same color as the active player
   */
  @Test
  public void testIsPlayersTurn() {
    when(game.getPlayerColor(any())).thenReturn(PieceColor.RED);
    when(game.getActiveColor()).thenReturn(PieceColor.RED);
    String expectedValue = gson.toJson(PostCheckTurnRoute.IS_PLAYER_TURN);
    String actualValue = (String) CuT.handle(request, null);
    assertEquals(expectedValue, actualValue);
  }

  /**
   * Checks to make sure the a message with false is returned with the request player is a
   * different color as the active player
   */
  @Test
  public void testIsOpponentTurn() {
    when(game.getPlayerColor(any())).thenReturn(PieceColor.RED);
    when(game.getActiveColor()).thenReturn(PieceColor.WHITE);
    String expectedValue = gson.toJson(PostCheckTurnRoute.IS_OPPONENT_TURN);
    String actualValue = (String) CuT.handle(request, null);
    assertEquals(expectedValue, actualValue);
  }
}

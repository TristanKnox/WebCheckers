package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.ui.GetGameRoute;

import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Handles testing for getting a game from the GetGameRoute. Mocks returning a game view by
 * checking that the correct players are returned in the model
 *
 * @author Collin Bolles
 */
@Tag("UI-tier")
public class GetGameRouteTest {
  private GetGameRoute CuT;

  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;
  private GameCenter gameCenter;
  private Game game;
  private Player playerOne;
  private Player playerTwo;

  @BeforeEach
  public void setup() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);
    engine = mock(TemplateEngine.class);
    gameCenter = mock(GameCenter.class);
    game = mock(Game.class);
    playerOne = new Player("sam");
    playerTwo = new Player("bob");

    // create a unique CuT for each test
    CuT = new GetGameRoute(engine, gameCenter);
  }

  /**
   * Testing getting a game from the GetGameRoute. Mocks GameCenter returning a game with players
   * to test for in the GetGameRoute return model.
   */
  @Test
  public void getGame() {
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    // Return player one when session attribute is requested
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(playerOne);
    // Return player one as the red player
    when(game.getRedPlayer()).thenReturn(playerOne);
    // Return player two as the white player
    when(game.getWhitePlayer()).thenReturn(playerTwo);
    // Return red as the active color
    when(game.getActivateColor()).thenReturn(PieceColor.RED);
    // Any game request will be the mocked game
    when(gameCenter.getGame(any(Player.class))).thenReturn(game);

    CuT.handle(request, response);
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    testHelper.assertViewModelAttribute(
        GetGameRoute.GAME_TITLE_ATTR, GetGameRoute.GAME_TITLE);
    testHelper.assertViewModelAttribute(
        "currentUser", playerOne);
    testHelper.assertViewModelAttribute(
        "redPlayer", playerOne);
    testHelper.assertViewModelAttribute(
        "whitePlayer", playerTwo);
    testHelper.assertViewModelAttribute(
        "activeColor", PieceColor.RED);
  }

}

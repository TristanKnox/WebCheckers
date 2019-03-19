package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * The suite of tests for the webcheckers GetHomeRoute class
 */

@Tag("UI-Tier")
public class GetHomeRouteTest {
  // the component being tested
  private GetHomeRoute CuT;

  // friendly classes
  private PlayerLobby playerLobby;

  // mock classes
  private Request request;
  private Response response;
  private Session session;
  private TemplateEngine engine;
  private Player player;

  /**
   * standard setup to be performed before each test.
   * Sets up the CuT, mock classes, and other necessary classes.
   */
  @BeforeEach
  public void setup() {
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);
    engine = mock(TemplateEngine.class);
    playerLobby = mock(PlayerLobby.class);
    player = mock(Player.class);

    // create a unique CuT for each test
    CuT = new GetHomeRoute(engine, playerLobby);
  }

  /**
   * test a new session, before a player has been logged in.
   */
  @Test
  public void new_session() {
    // enables us to check for things in the vm bucket
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    // Arrange the test Scenario: No players are logged in.
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    when(playerLobby.getNumberOfUsers()).thenReturn(0);

    // Invoke the test
    CuT.handle(request, response);

    // Analyze the results:
    //   * model is a non-null Map
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    //   * model contains all necessary View-Model data
    testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, "Welcome!");
    testHelper.assertViewModelAttribute("message",  Message.info("Welcome to the world of online Checkers.\n" +
            "0 players are currently logged in."));
    //   * test view name
    testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
  }

  @Test
  public void player_in_game(){
    // Arrange the test scenario: There is an existing session, and the player is in a game
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);
    when(playerLobby.isInGame(session.attribute(GetHomeRoute.PLAYER_KEY))).thenReturn(true);

    // Invoke the test
    try {
      CuT.handle(request, response);
      fail("Redirects invoke halt exceptions.");
    } catch (HaltException e) {
      // expected
    }

    // Analyze the results:
    //   * redirect to the Game view
    verify(response).redirect(WebServer.GAME_URL);
  }

  @Test
  public void selected_player_unavailable(){
    // enables us to check for things in the vm bucket
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    // Arrange the test Scenario: There is a player in the session, and they have selected an
    // unavailable opponent.
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);
    when(playerLobby.isInGame(session.attribute(GetHomeRoute.PLAYER_KEY))).thenReturn(false);
    when(session.attribute(GetHomeRoute.CURRENT_USER_ATTR)).thenReturn(player);
    when(session.attribute(GetHomeRoute.IN_GAME_ERROR_FLAG)).thenReturn(true);

    List<Player> pList= new ArrayList<Player>();
    pList.add(player);

    when(playerLobby.getAllAvalPlayers()).thenReturn(pList);

    // Invoke the test
    CuT.handle(request, response);

    // Analyze the results:
    //   * model is a non-null Map
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    //   * model contains all necessary View-Model data
    testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, "Homepage");
    testHelper.assertViewModelAttribute(GetHomeRoute.CURRENT_USER_ATTR, player);
    testHelper.assertViewModelAttribute(GetHomeRoute.PLAYERS_LIST_ATTR, pList);
    testHelper.assertViewModelAttribute("message",
            Message.error("User has joined another game. Pick another user."));
    //   * test view name
    testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    //   * verify require session variables are correct
    verify(session).attribute(eq(GetHomeRoute.IN_GAME_ERROR_FLAG), eq(false));
  }

  @Test
  public void default_home_state(){
      // enables us to check for things in the vm bucket
      final TemplateEngineTester testHelper = new TemplateEngineTester();
      when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

      // Arrange the test Scenario: There is a player in the session, and they have selected an
      // unavailable opponent.
      when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);
      when(playerLobby.isInGame(session.attribute(GetHomeRoute.PLAYER_KEY))).thenReturn(false);
      when(session.attribute(GetHomeRoute.IN_GAME_ERROR_FLAG)).thenReturn(false);
      when(player.getName()).thenReturn("playername");

      List<Player> pList= new ArrayList<Player>();
      pList.add(player);

      when(playerLobby.getAllAvalPlayers()).thenReturn(pList);

      // Invoke the test
      CuT.handle(request, response);

      // Analyze the results:
      //   * model is a non-null Map
      testHelper.assertViewModelExists();
      testHelper.assertViewModelIsaMap();
      //   * model contains all necessary View-Model data
      testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, "Homepage");
      testHelper.assertViewModelAttribute(GetHomeRoute.CURRENT_USER_ATTR, player);
      testHelper.assertViewModelAttribute(GetHomeRoute.PLAYERS_LIST_ATTR, pList);
      testHelper.assertViewModelAttribute("message",
              Message.info(String.format(GetHomeRoute.PERSONAL_WELCOME, player.getName())));
      //   * test view name
      testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }
}

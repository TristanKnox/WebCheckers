package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Turn;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostMoveRequestRouteTest {

  private final String moveJson = "actionData=%7B%22start%22%3A%7B%22row%22%3A2%2C%22cell%22%3A5%7D%2C%22end%22%3A%7B%22row%22%3A3%2C%22cell%22%3A4%7D%7D";

  private Session session;
  private Request request;
  private Response response;
  private Player player;
  private Game game;
  private GameCenter gameCenter;

  private PostMoveRequestRoute CuT;
  private Gson gson;

  /**
   * Sets up the mock objects needed for testing
   */
  @BeforeEach
  public void setup(){
    response = mock(Response.class);
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    player = mock(Player.class);
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);
    gameCenter = mock(GameCenter.class);
    game = mock(Game.class);
    when(gameCenter.getGame(player)).thenReturn(game);
    when(request.body()).thenReturn(this.moveJson);

    gson = new Gson();
    CuT = new PostMoveRequestRoute(gameCenter);
  }

  /**
   * Tests a valid move
   */
  @Test
  public void testValidMove(){
    when(game.addMove(any())).thenReturn(Turn.TurnResponse.VALID_TURN);
    String expected = gson.toJson(Message.info(PostMoveRequestRoute.VALID_MOVE));
    String actual = null;
    try {
      actual = (String)CuT.handle(request,response);
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertEquals(expected,actual);

  }

  /**
   * Tests an invalid move
   */
  @Test
  public void testInvalidMove(){
    when(game.addMove(any())).thenReturn(Turn.TurnResponse.INVALID_DIRECTION);
    String expected = gson.toJson(Message.error(PostMoveRequestRoute.INVALID_MOVE + Turn.TurnResponse.INVALID_DIRECTION));
    String actual = null;
    try {
      actual = (String)CuT.handle(request,response);
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertEquals(expected,actual);
  }
}

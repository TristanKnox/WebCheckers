package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Session;
import spark.TemplateEngine;

public class PostSignOutRouteTest {

  private Request request;
  private Session session;
  private GameCenter gameCenter;
  private PlayerLobby playerLobby;
  private Gson gson;
  private Player player1;
  private Player player2;
  private PostSubmitTurnRoute CuT;
  private TemplateEngine templateEngine;


  /**
   * Automatically setups the tests with mocks for required objects.
   */
  @BeforeEach
  public void setup() {
    request = mock(Request.class);
    session = mock(Session.class);
    gameCenter = mock(GameCenter.class);
    playerLobby = mock(PlayerLobby.class);
    templateEngine =mock(TemplateEngine.class);
    player1 = new Player("KAKAKAAK");
    when(playerLobby.isInGame(player1)).thenReturn(Boolean.TRUE);
    when(playerLobby.isInGame(player2)).thenReturn(Boolean.FALSE);
    when(request.session()).thenReturn(session);
    when(session.attribute(any())).thenReturn(null);
    gson = new Gson();
    CuT = new PostSubmitTurnRoute(gson, gameCenter);
  }

  /**
   * tests the constructor
   */
  @Test
  public void testConstructor(){
    PostSignOutRoute routey = new PostSignOutRoute(playerLobby,gameCenter,templateEngine);
    assertNotNull(routey);
  }

}
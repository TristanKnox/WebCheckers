package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.model.checkers.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

public class PostSignOutRouteTest {

  private Response responce;
  private Request request;
  private Session session;
  private GameCenter gameCenter;
  private PlayerLobby playerLobby;
  private ReplayCenter replayCenter;
  private Gson gson;
  private Player player1;
  private Player player2;
  private PostSignOutRoute CuT;
  private TemplateEngine templateEngine;


  /**
   * Automatically setups the tests with mocks for required objects.
   */
  @BeforeEach
  public void setup() {
    responce = mock(Response.class);
    request = mock(Request.class);
    session = mock(Session.class);
    gameCenter = mock(GameCenter.class);
    playerLobby = mock(PlayerLobby.class);
    replayCenter = mock(ReplayCenter.class);
    templateEngine =mock(TemplateEngine.class);
    player1 = new Player("KAKAKAAK");
    when(playerLobby.isInGame(player1)).thenReturn(Boolean.TRUE);
    when(playerLobby.isInGame(player2)).thenReturn(Boolean.FALSE);
    when(request.session()).thenReturn(session);
    when(session.attribute(any())).thenReturn(null);
    gson = new Gson();
    CuT = new PostSignOutRoute(playerLobby,gameCenter,replayCenter,templateEngine);
  }

  /**
   * tests the constructor
   */
  @Test
  public void testConstructor(){
    PostSignOutRoute route = new PostSignOutRoute(playerLobby,gameCenter,replayCenter,templateEngine);
    assertNotNull(route);
  }

  @Test
  public void testHandle(){
    CuT = new PostSignOutRoute(playerLobby,gameCenter,replayCenter,templateEngine);
    try{
      CuT.handle(request,responce);
    }catch(Exception e){}
    verify(responce).redirect(WebServer.HOME_URL);
  }

}

package com.webcheckers.ui;

import static com.webcheckers.ui.PostSignInAttemptRoute.INVALID_USERNAME;
import static com.webcheckers.ui.PostSignInAttemptRoute.MESSAGE_ATTR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerLobby.Outcome;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;
import spark.Response;
import spark.Request;
import spark.Session;
import spark.TemplateEngine;
import org.junit.jupiter.api.Tag;

@Tag("UI-tier")
public class PostSignInAttemptTest {

  private static final String USERNAME = "Username";
  private static final String TEST_NAME_VALID = "Timmy";
  private static final String TEST_NAME_VALID2 = "Mumps";
  private static final String TEST_NAME_VALID3 = "Shingles";
  private static final String TEST_NAME_VALID4 = "Sh1nGL3Z";
  private static final String TEST_NAME_INVALID = "1gangnamstyletogoplease";
  private static final int EMPTY = 0;
  private static PlayerLobby.Outcome outCome;

  private PostSignInAttemptRoute CuT;

  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine templateEngine;
  private PlayerLobby playerLobby;

  @BeforeEach
  public void setup(){
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);

    response = mock(Response.class);
    templateEngine = mock(TemplateEngine.class);

    playerLobby = mock(PlayerLobby.class);
    CuT = new PostSignInAttemptRoute(playerLobby,templateEngine);
  }

  /**
   * test for the constructor of PostSignInAttempt route
   */
  @Test
  public void testCtor(){
    PostSignInAttemptRoute route = new PostSignInAttemptRoute(playerLobby,templateEngine);
    assertNotNull(route);
  }


  @Test
  public void test_Valid_Login_Query(){
    outCome = Outcome.SUCCESS;
    when(playerLobby.addPlayer(TEST_NAME_VALID)).thenReturn(outCome);
    when(request.queryParams(any(String.class))).thenReturn(TEST_NAME_VALID);

    try {
      CuT.handle(request, response);
    }catch (Exception e){}

    verify(response).redirect(WebServer.HOME_URL);

  }
  @Test
  public void test_Invalid_Login_Query(){
    outCome = Outcome.INVALID;
    when(playerLobby.addPlayer(TEST_NAME_INVALID)).thenReturn(outCome);
    try {
      CuT.handle(request, response);
    } catch (Exception e){ }
    assertNotNull(session.attributes());
    assertEquals(outCome, playerLobby.addPlayer(TEST_NAME_INVALID));

  }

}
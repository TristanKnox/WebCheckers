package com.webcheckers.ui;

import static com.webcheckers.ui.PostSignInAttemptRoute.INVALID_USERNAME;
import static com.webcheckers.ui.PostSignInAttemptRoute.MESSAGE_ATTR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.PlayerLobby;
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
  }

  @Test
  public void test_Valid_Login_Query(){
    when(request.queryParams(USERNAME)).thenReturn(TEST_NAME_VALID);
    when(playerLobby.getPlayer(TEST_NAME_VALID)).thenReturn(new Player(TEST_NAME_VALID));

    try {
      CuT.handle(request, response);
    }catch (Exception e){}
    assertNotNull(session.attributes());

    verify(session).attribute(eq(GetHomeRoute.PLAYER_KEY),any(Player.class));
    verify(session).attribute(eq(GetHomeRoute.IN_GAME_ERROR_FLAG),eq(Boolean.FALSE));
    verify(response).redirect(WebServer.HOME_URL);

  }
  @Test
  public void test_Invalid_Login_Query(){
    when(request.queryParams(USERNAME)).thenReturn(TEST_NAME_INVALID);
    TemplateEngineTester engineTester = new TemplateEngineTester();
    try {
      CuT.handle(request, response);
    } catch (Exception e){ }
    assertNotNull(session.attributes());
    verify()



  }

}

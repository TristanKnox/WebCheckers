package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.PlayerLobby;
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
  public void test_Login_Query(){
    when(request.queryParams(USERNAME)).thenReturn(TEST_NAME_VALID);
    //thanks tristan!
    final TemplateEngineTester testHelper = new TemplateEngineTester();

    when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request,response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();

  }

}

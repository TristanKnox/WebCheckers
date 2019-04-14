package com.webcheckers.ui.ReplayRoutes;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.ReplayCenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

public class GetReplayRouteTest {
  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine templateEngine;
  private ReplayCenter replayCenter;
  private GetReplayRoute CuT;
  @BeforeEach
  public void setUp(){
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    replayCenter = mock(ReplayCenter.class);
    templateEngine = mock(TemplateEngine.class);
  }
  @Test
  public void testCtor(){
    GetReplayRoute cut = new GetReplayRoute(replayCenter,templateEngine);
    assertNotNull(cut);
  }
  //todo
  @Test
  public void testHandle(){

  }
}

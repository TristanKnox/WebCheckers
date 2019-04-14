package com.webcheckers.ui.ReplayRoutes;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.Replay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

public class PostPreviousTurnRouteTest {


  private Request request;
  private Session session;
  private Response response;
  private ReplayCenter replayCenter;
  private Replay replay;
  private PostPreviousTurnRoute CuT;

  @BeforeEach
  public void setUp(){
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    replayCenter = mock(ReplayCenter.class);
    replay = mock(Replay.class);
    CuT = new PostPreviousTurnRoute(replayCenter);
  }
  @Test
  public void testCtor(){
    assertNotNull(CuT);
  }
  @Test
  public void testHandle(){
    assertNotNull(CuT);
    try {
      assertNotNull(CuT.handle(request, response));
    } catch (Exception e) { }
  }
}


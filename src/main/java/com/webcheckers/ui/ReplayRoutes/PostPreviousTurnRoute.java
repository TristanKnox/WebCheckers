package com.webcheckers.ui.ReplayRoutes;

import com.webcheckers.appl.ReplayCenter;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostPreviousTurnRoute implements Route {

  private ReplayCenter replayCenter;

  public PostPreviousTurnRoute(ReplayCenter rCenter){
    replayCenter = rCenter;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    return null;
  }
}

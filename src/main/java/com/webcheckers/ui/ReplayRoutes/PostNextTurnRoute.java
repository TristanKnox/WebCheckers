package com.webcheckers.ui.ReplayRoutes;


import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.Replay;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostNextTurnRoute implements Route {
  private ReplayCenter replayCenter;


  /**
   * the constructor for PostNextRoute for replay mode
   */
  public PostNextTurnRoute(ReplayCenter replayas){
    replayCenter = replayas;

  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    return null;
  }
}

package com.webcheckers.ui.ReplayRoutes;


import static spark.Spark.halt;

import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.WebServer;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

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
    Session session = request.session();
    Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
    Replay replay = replayCenter.getReplay(player);
    replay.getNextBoardState();
    response.redirect(WebServer.REQUEST_REPLAY_URL);
    halt();
    return null;
  }
}

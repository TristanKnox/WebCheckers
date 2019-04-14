package com.webcheckers.ui.ReplayRoutes;


import static spark.Spark.halt;

import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import spark.*;

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

    System.out.println(request.queryParams("gameID"));

    System.out.println("Before Redirect");//TODO remove
    response.redirect(WebServer.GET_REPLAY_URL);
    System.out.println("After Redirect");//TODO remove
   // halt();
    System.out.println("After halt");
    return Message.info("This is a test");
  }
}

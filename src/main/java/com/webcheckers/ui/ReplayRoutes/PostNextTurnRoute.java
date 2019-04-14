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
  private TemplateEngine templateEngine;


  /**
   * the constructor for PostNextRoute for replay mode
   */
  public PostNextTurnRoute(ReplayCenter replayCenter, TemplateEngine templateEngine){
    this.replayCenter = replayCenter;
    this.templateEngine = templateEngine;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    Session session = request.session();
    Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
    Replay replay = replayCenter.getReplay(player);
    replay.getNextBoardState();

    System.out.println(request.queryString());
    
    GetReplayRoute route = new GetReplayRoute(replayCenter, templateEngine);
    route.handle(request, response);
    return Message.info("true");
  }
}

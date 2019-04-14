package com.webcheckers.ui.ReplayRoutes;

import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.Player;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.WebServer;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

public class PostRequestReplayRoute implements Route {

  private ReplayCenter replayCenter;


  public PostRequestReplayRoute(ReplayCenter replayCenter){
    this.replayCenter = replayCenter;
  }
  @Override
  public Object handle(Request request, Response response) throws Exception {
    Session session = request.session();

    Player player = session.attribute(GetHomeRoute.PLAYER_KEY);

    int replayId = Integer.parseInt(request.queryParams("replayID"));

    replayCenter.startReplay(player, replayId);
    response.redirect(WebServer.GET_REPLAY_URL);
    halt();

    return null;
  }
}

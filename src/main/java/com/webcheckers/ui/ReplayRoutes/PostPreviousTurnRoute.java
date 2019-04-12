package com.webcheckers.ui.ReplayRoutes;

import static spark.Spark.halt;

import com.google.gson.Gson;
import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.BoardState;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.ui.WebServer;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostPreviousTurnRoute implements Route {

  private ReplayCenter replayCenter;

  public PostPreviousTurnRoute(ReplayCenter rCenter){
    replayCenter = rCenter;


  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    Session session = request.session();
    Player player = session.attribute(GetReplayHomeRoute.CURRENT_USER_ATTR);
    Replay replay = replayCenter.getReplay(player);
    replay.getPreviousBoardState();
    response.redirect(WebServer.REQUEST_REPLAY_URL);
    halt();
    return null;


  }
}

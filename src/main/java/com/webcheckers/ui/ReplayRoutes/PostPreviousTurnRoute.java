package com.webcheckers.ui.ReplayRoutes;

import static spark.Spark.halt;

import com.google.gson.Gson;
import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.BoardState;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import spark.*;

public class PostPreviousTurnRoute implements Route {

  private ReplayCenter replayCenter;
  private TemplateEngine templateEngine;

  public PostPreviousTurnRoute(ReplayCenter replayCenter, TemplateEngine templateEngine){
    this.replayCenter = replayCenter;
    this.templateEngine = templateEngine;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    Session session = request.session();
    Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
    Replay replay = replayCenter.getReplay(player);
    replay.getPreviousBoardState();

    response.redirect(WebServer.GET_REPLAY_URL);
    halt();
    return null;
  }
}

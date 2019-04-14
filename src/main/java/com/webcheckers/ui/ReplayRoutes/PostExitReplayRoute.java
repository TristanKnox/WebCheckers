package com.webcheckers.ui.ReplayRoutes;

import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.WebServer;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

import static spark.Spark.halt;

public class PostExitReplayRoute implements Route {
  // attributes
  private ReplayCenter replayCenter;

  public PostExitReplayRoute(ReplayCenter replayCenter){
    // check the params are not null
    Objects.requireNonNull(replayCenter, "ReplayCenter must not be null");
    this.replayCenter = replayCenter;
  }

  @Override
  public String handle(Request request, Response response){
    // remove the player and replay from the active replays map
    replayCenter.endReplay(request.session().attribute(GetHomeRoute.PLAYER_KEY));

    response.redirect(WebServer.REPLAY_URL);
    halt();
    return null;
  }
}

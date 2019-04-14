package com.webcheckers.ui.ReplayRoutes;

import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.WebServer;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.Objects;
import static spark.Spark.halt;

/**
 * A class which is in charge of sesamlessly exiting a player from a replay screen,
 * and having them returned to the replay home
 *
 * @author Andrew Bado
 */
public class PostExitReplayRoute implements Route {
  // attributes
  private ReplayCenter replayCenter;

  /**
   * constructs a new PostExitReplayRoute
   * @param replayCenter the server's replayCenter
   */
  public PostExitReplayRoute(ReplayCenter replayCenter){
    // check the params are not null
    Objects.requireNonNull(replayCenter, "ReplayCenter must not be null");
    this.replayCenter = replayCenter;
  }

  /**
   * handles removing the player form the list of active replays
   *
   * @param request the http request
   * @param response the http response
   * @return a string
   */
  @Override
  public String handle(Request request, Response response){
    // remove the player and replay from the active replays map
    replayCenter.endReplay(request.session().attribute(GetHomeRoute.PLAYER_KEY));

    // redirect to to replay home page
    response.redirect(WebServer.REPLAY_URL);
    halt();
    return null;
  }
}

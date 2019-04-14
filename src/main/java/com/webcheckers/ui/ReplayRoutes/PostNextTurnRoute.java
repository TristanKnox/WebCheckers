package com.webcheckers.ui.ReplayRoutes;

import static spark.Spark.halt;

import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import spark.*;

/**
 * This class is in charge of moving a replay to its next turn, and then having the view re-rendered
 */
public class PostNextTurnRoute implements Route {
  // attributes
  private ReplayCenter replayCenter;
  private TemplateEngine templateEngine;


  /**
   * the constructor for PostNextRoute
   */
  public PostNextTurnRoute(ReplayCenter replayCenter, TemplateEngine templateEngine){
    this.replayCenter = replayCenter;
    this.templateEngine = templateEngine;
  }

  /**
   * Tells the replay to be incremented, and then has the page re-rendered
   * @param request the http request
   * @param response the http response
   * @return a message to notify the JS that this action was a success
   * @throws Exception
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    // get the session
    Session session = request.session();

    // get the player
    Player player = session.attribute(GetHomeRoute.PLAYER_KEY);

    // get and increment the replay
    Replay replay = replayCenter.getReplay(player);
    replay.getNextBoardState();

    response.redirect(WebServer.GET_REPLAY_URL);
    halt();
    return null;

  }
}

package com.webcheckers.ui.ReplayRoutes;

import com.google.gson.Gson;
import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.BoardState;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.model.checkers.Game;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostPreviousTurnRoute implements Route {

  private ReplayCenter replayCenter;
  private Gson gson;

  public PostPreviousTurnRoute(Gson gson,ReplayCenter rCenter){
    replayCenter = rCenter;
    this.gson = gson;

  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    Session session = request.session();
    Player player = session.attribute(GetReplayHomeRoute.CURRENT_USER_ATTR);
    Replay replay = replayCenter.getReplay(player);
//    if (replay.isBeginningOfGame()){
//
//    }
    BoardState bs = replay.getPreviousBoardState();
    return bs;




    return null;
  }
}

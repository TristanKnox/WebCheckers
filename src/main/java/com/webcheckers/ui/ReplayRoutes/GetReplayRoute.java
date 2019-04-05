package com.webcheckers.ui.ReplayRoutes;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetReplayRoute implements Route {

  private PlayerLobby playerLobby;
  private ReplayCenter replayCenter;

  public GetReplayRoute(ReplayCenter replayCenter, PlayerLobby playerLobby){
    this.replayCenter = replayCenter;
    this.playerLobby = playerLobby;
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    return null;
  }
}

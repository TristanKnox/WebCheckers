package com.webcheckers.ui.ReplayRoutes;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.Player;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.WebServer;
import com.webcheckers.util.Message;
import spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import static spark.Spark.halt;

public class GetReplayHomeRoute implements Route {

  // keeps track of all players eho are logged in
  private PlayerLobby playerLobby;
  // keeps track of all replays which have been saved
  private ReplayCenter replayCenter;
  // helps to render html pages from ftl and a vm bucket
  private TemplateEngine templateEngine;

  //required fields in the vm
  static final String TITLE_ATTR = "title";
  static final String MESSAGE_ATTR = "message";
  static final String CURRENT_USER_ATTR = "currentUser";
  static final String REPLAY_LIST_ATTR = "replays";

  public GetReplayHomeRoute(ReplayCenter replayCenter, PlayerLobby playerLobby, TemplateEngine templateEngine) {
    this.replayCenter = Objects.requireNonNull(replayCenter, "The replay center must not be null");
    this.playerLobby = Objects.requireNonNull(playerLobby, "PlayerLobby must not be null");
    this.templateEngine = Objects.requireNonNull(templateEngine, "TemplateEngine must not be null");
  }

  /**
   * render's the replaycenter display page when prompted.
   *
   * @param request the session request
   * @param response the session response
   * @return rendered html page from the templateEngine and vm
   * @throws Exception
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    // retrieve the session
    Session httpSession = request.session();

    // retrieve the player
    Player currentPlayer = httpSession.attribute(GetHomeRoute.PLAYER_KEY);

    // create the view-model bucket
    Map<String, Object> vm = new HashMap<>();
    vm.put(TITLE_ATTR, "Replay Center");
    vm.put(MESSAGE_ATTR, Message.info("Welcome to the Replay Center! Select a replay to watch."));
    vm.put(CURRENT_USER_ATTR, currentPlayer);
    vm.put(REPLAY_LIST_ATTR, replayCenter.getReplayList());

    httpSession.attribute(GetHomeRoute.VIEW_MODE_ATTR, "REPLAY");

    // remove the user from available players
    playerLobby.removePlayer(currentPlayer.getName());

    return templateEngine.render(new ModelAndView(vm, "replay.ftl"));
  }
}


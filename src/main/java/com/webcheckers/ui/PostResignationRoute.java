package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;
import java.util.Objects;

/**
 * This Route is responsible for resigning a player from a game.
 * It will have them removed from the game, and returned to the
 * home screen when they press the resign button from the game view.
 *
 * @author Andrew Bado
 */

public class PostResignationRoute implements Route {
  // Values used in the view-model map for rendering the game view after a sign in attempt

  // Attributes
  private final PlayerLobby playerLobby;
  private final GameCenter gameCenter;
  private final ReplayCenter replayCenter;


  /**
   * The constructor for the POST /resignGame route handler.
   *
   * @param playerLobby playerLobby, which keeps track of all current players
   * @param gameCenter gameCenter keeps track of all games going on
   */
  PostResignationRoute(PlayerLobby playerLobby, GameCenter gameCenter, ReplayCenter replayCenter) {
    // neither parameter may be null
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    Objects.requireNonNull(replayCenter, "replayCenter must not be null");

    this.playerLobby = playerLobby;
    this.gameCenter = gameCenter;
    this.replayCenter = replayCenter;

  }

  /**
   * Obtains the username, asks playerLobby if it is valid. Then re-renders
   * the sign in page, or re-directs to home accordingly.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   *
   * @return the rendered HTML for either the sign in page, or a redirect.
   */
  @Override
  public String handle(Request request, Response response) {
    // get the session
    Session httpSession = request.session();


    //retrieve the current player object
    Player player = httpSession.attribute(GetHomeRoute.PLAYER_KEY);

    // store the replay of the game
    replayCenter.storeReplay(gameCenter.getGame(player));

    // list the player as available, and end their game.
    // then switch whose turn it is if need be
    playerLobby.makeAvailable(player);

    // resign them from the game center.
    gameCenter.resignation(player);

    Gson gson = new Gson();
    return gson.toJson(Message.info("someone resigned"));
  }
}

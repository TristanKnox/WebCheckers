package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static spark.Spark.halt;

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
  // private final TemplateEngine templateEngine;

  /**
   * The constructor for the POST /signinattempt route handler.
   *
   * @param playerLobby playerLobby, which keeps track of all current players
   * // @param templateEngine template engine to use for rendering HTML page
   */
  PostResignationRoute(PlayerLobby playerLobby, GameCenter gameCenter /*, TemplateEngine templateEngine*/) {
    // neither parameter may be null
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    //Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    this.playerLobby = playerLobby;
    this.gameCenter = gameCenter;
    // this.templateEngine = templateEngine;
  }

  /**
   * Gets the username of the resigning player, ends their game,
   * and returns them to the lobby and homepage
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

    Player player = httpSession.attribute(GetHomeRoute.PLAYER_KEY);

    gameCenter.removePlayerFromGame(player);
    // TODO need a method that does this
    //playerLobby.makeAvailable(player);

    // TODO Need to make the message here, and do Gson/Json stuffs

    // start the View-Model
    // final Map<String, Object> vm = new HashMap<>();

    response.redirect(WebServer.HOME_URL);
    halt();
    return null;
  }
}

package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.ui.ViewObjects.ViewGenerator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * The GetGameRoute is called when a game is required. This route expects that the game already
 * exists in the game center.
 *
 * @author Collin Bolles
 */
public class GetGameRoute implements Route {

  /** Used to log messages to standard out **/
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  /** Used to render user pages **/
  private final TemplateEngine templateEngine;
  /** Keeps track of the current games and the players in them **/
  private GameCenter gameCenter;
  /** The title of the game screen on the UI **/
  public static final String GAME_TITLE = "Checkers";

  /**
   * Construct the game route with a template engine and game center
   * @param templateEngine The template engine to render the client UI
   * @param gameCenter The object that keeps track of all games and the users in the game
   */
  public GetGameRoute(final TemplateEngine templateEngine, GameCenter gameCenter) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gameCenter = gameCenter;
    LOG.config("GetGameRoute is initialized.");
  }

  /**
   * Handle request for the game. Returns the rendered game for the given user passed in from
   * the session.
   * @param request The request for the game
   * @param response The response of the game view
   * @precondition The player has a game in game center already
   * @precondition The player is saved in the request session
   * @return Rendered game view for the given player
   */
  public Object handle(Request request, Response response) {
    LOG.finer("GetGameRoute is invoked.");

    final Session session = request.session();
    /*
     * TODO
     * Create permanent variable for player attribute key
     */
    final Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
    Game game = gameCenter.getGame(player);

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", GAME_TITLE);

    vm.put("currentUser", player);
    vm.put("redPlayer", game.getRedPlayer());
    vm.put("whitePlayer", game.getWhitePlayer());
    vm.put("activeColor", game.getActivateColor());
    /*
     * TODO
     * Add ability to select game view (will be an enhancement down the road)
     */
    vm.put("viewMode", "PLAY");
    vm.put("board", ViewGenerator.getView(game, game.getPlayerColor(player)));
    
    // render the View
    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
  }
}

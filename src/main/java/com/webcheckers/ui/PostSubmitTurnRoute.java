package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * Handles logic for submitting the current turn that being added to. Calling
 * of this route represents the end of the players turn and the move being executed.
 * Move addition/validation is not handled here
 *
 * @author Collin Bolles
 */
public class PostSubmitTurnRoute implements Route {

  /** Handles logic for rendering response to player */
  private TemplateEngine templateEngine;
  /** Keeps track of the active games. Used for getting game to execute move on **/
  private GameCenter gameCenter;

  /**
   * Construct a route with a given template engine and game center
   * @param templateEngine The template engine to use to render player views
   * @param gameCenter The game center that keeps track of the active games
   */
  public PostSubmitTurnRoute(TemplateEngine templateEngine, GameCenter gameCenter) {
    this.templateEngine = templateEngine;
    this.gameCenter = gameCenter;
  }

  /**
   * Handle the logic of executing a given move. Before the move is executed, the route
   * checks to make sure that there is a move to execute and the correct player is calling
   * the execution then returning any error messages to the user
   * @param request The request from the player including player information
   * @param response The response to return to the user
   * @return Any errors that may have occurred
   */
  @Override
  public Object handle(Request request, Response response) {
    Session session = request.session();
    Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
    Game game = gameCenter.getGame(player);
    if(game.getActivateColor() != game.getPlayerColor(player)) {
      // TODO: Handle sending an error, wrong player executing turn
    }
    if(!game.currentTurnHasMove()) {
      // TODO: Handle sending an error, no turns to execute
    }
    game.executeTurn();
  }
}

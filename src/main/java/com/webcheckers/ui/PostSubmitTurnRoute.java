package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.util.Message;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * Handles logic for submitting the current turn that being added to. Calling
 * of this route represents the end of the players turn and the move being executed.
 * Move addition/validation is not handled here
 *
 * @author Collin Bolles
 */
public class PostSubmitTurnRoute implements Route {

  public static final Message NO_MOVES_TO_EXECUTE = Message.error("No move to execute");
  public static final Message NOT_PLAYERS_TURN = Message.error("It is not your turn");
  public static final Message MULTI_MOVE_INCOMPLETE = Message.error("You need to finish your multi jump");
  public static final Message TURN_EXECUTED = Message.info("Turn Executed");

  /** Handles logic for rendering response to player */
  private Gson gson;
  /** Keeps track of the active games. Used for getting game to execute move on **/
  private GameCenter gameCenter;


  /**
   * Construct a route with a given template engine and game center
   * @param gson The gson object which handles converting messages to json
   * @param gameCenter The game center that keeps track of the active games
   */
  public PostSubmitTurnRoute(Gson gson, GameCenter gameCenter) {
    this.gson = gson;
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
    if(game.getActiveColor() != game.getPlayerColor(player))
      return gson.toJson(NOT_PLAYERS_TURN);
    if(!game.currentTurnHasMove())
      return gson.toJson(NO_MOVES_TO_EXECUTE);
    if(!game.turnIsComplete())
      return gson.toJson(MULTI_MOVE_INCOMPLETE);
    game.executeTurn();
    return gson.toJson(TURN_EXECUTED);
  }
}

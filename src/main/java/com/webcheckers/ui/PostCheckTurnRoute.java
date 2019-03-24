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

public class PostCheckTurnRoute implements Route {

  /** Message for when it is the player's turn */
  private static final Message IS_PLAYER_TURN = Message.info("true");
  /** Message for when is it the opponents turn */
  private static final Message IS_OPPONENT_TURN = Message.info("false");

  /** Handles converting messages to json */
  private Gson gson;
  /** Used to get the player's game */
  private GameCenter gameCenter;

  /**
   * Handles constructing a check turn route with a given gson parser and game center
   * @param gson The gson object for translating message to json
   * @param gameCenter Game center with active game included
   */
  public PostCheckTurnRoute(Gson gson, GameCenter gameCenter) {
    this.gson = gson;
    this.gameCenter = gameCenter;
  }

  /**
   * Handles returning which players turn it is. Returns a message with true if it is the
   * requesting players turn. False if the current turn belongs to the opponent
   * @param request Request from the user
   * @param response Response to the user
   * @return True message if player's turn false if not
   */
  @Override
  public Object handle(Request request, Response response) {
    Session session = request.session();
    Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
    Game game = gameCenter.getGame(player);
    return game.getPlayerColor(player) == game.getActivateColor() ? gson.toJson(IS_PLAYER_TURN)
        : gson.toJson(IS_OPPONENT_TURN);
  }
}

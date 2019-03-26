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

public class PostBackupMoveRoute implements Route {

  /** Message returned when there is no move to backup */
  public static final Message NO_MOVE_TO_BACKUP = Message.error("No move to backup!");
  /** Message returned when the wrong player attempts to backup a move */
  public static final Message WRONG_PLAYER = Message.error("It is not your turn!");
  /** Message returned when the backup is successful */
  public static final Message BACKUP_SUCCESSFUL = Message.info("Backup move");

  /** Handles the logic for converting messages to json */
  private Gson gson;
  /** Handles getting the active game */
  private GameCenter gameCenter;

  /**
   * Construct a route with gson and game center to return messages
   * @param gson Gson object for converting messages to json
   * @param gameCenter Handles getting the active game
   */
  public PostBackupMoveRoute(Gson gson, GameCenter gameCenter) {
    this.gson = gson;
    this.gameCenter = gameCenter;
  }

  /**
   * Handles the logic of backing up a single move.
   * @param request The user request
   * @param response The response for the user
   * @return Any error messages associated with a backup
   */
  @Override
  public Object handle(Request request, Response response) {
    Session session = request.session();
    Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
    Game game = gameCenter.getGame(player);
    if(!game.currentTurnHasMove())
      return gson.toJson(NO_MOVE_TO_BACKUP);
    if(game.getActiveColor() != game.getPlayerColor(player))
      return gson.toJson(WRONG_PLAYER);
    game.backupTurn();
    return gson.toJson(BACKUP_SUCCESSFUL);
  }
}

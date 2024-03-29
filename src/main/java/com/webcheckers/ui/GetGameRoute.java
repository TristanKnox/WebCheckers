package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.Player.Badge;
import com.webcheckers.model.ai.AIPlayer;
import com.webcheckers.model.ai.AIPlayer.Difficulty;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game.EndGameCondition;
import com.webcheckers.model.checkers.Piece;
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
  /**Allows for the storage of played gamse**/
  private ReplayCenter replayCenter;
  /** keeps track of which players are available to play games **/
  private final PlayerLobby playerLobby;
  /** The title of the game screen on the UI **/
  public static final String GAME_TITLE = "Checkers";

  public static final String GAME_TITLE_ATTR = "title";

  /**
   * Construct the game route with a template engine and game center
   * @param templateEngine The template engine to render the client UI
   * @param gameCenter The object that keeps track of all games and the users in the game
   */
  public GetGameRoute(final TemplateEngine templateEngine, GameCenter gameCenter, PlayerLobby playerLobby, ReplayCenter replayCenter) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gameCenter = gameCenter;
    this.playerLobby = playerLobby;
    this.replayCenter = replayCenter;
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
    vm.put(GAME_TITLE_ATTR, GAME_TITLE);

    if(game.isGameOver()){
      gameCenter.exitGame(player);
      playerLobby.makeAvailable(player);
      replayCenter.storeReplay(game);

      Gson gson = new Gson();
      Map<String, Object> modeOptions = new HashMap<String, Object>();
      modeOptions.put("isGameOver", true);
      modeOptions.put("gameOverMessage", getEndGameMessage(game, player));
      vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));

      // Add badges as needed
      Player opponent = game.getOpponent(player);
      EndGameCondition end = game.getEndGameCondition();
      boolean playingAgainstAI = opponent instanceof AIPlayer;
      boolean playerWon = end == EndGameCondition.WHITE_OUT_OF_PIECES || end == EndGameCondition.WHITE_OUT_OF_MOVES;

      if(playingAgainstAI && playerWon) {
        Badge badge = ((AIPlayer)opponent).getDifficulty() == Difficulty.HARD ?
            Badge.HARD_AI_DEFEATED : Badge.EASY_AI_DEFEATED;
        player.addBadge(badge);
      }
    }

    vm.put("currentUser", player);
    vm.put("redPlayer", game.getRedPlayer());
    vm.put("whitePlayer", game.getWhitePlayer());
    vm.put("activeColor", game.getActiveColor());
    /*
     * TODO
     * Add ability to select game view (will be an enhancement down the road)
     */
    vm.put("viewMode", "PLAY");
    vm.put("board", ViewGenerator.getView(game, game.getPlayerColor(player)));

    // render the View
    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
  }

  /**
   * Hleper method to build the end game message
   * @param game - the game the message is for
   * @param player - the player the message is for
   * @return - the message fro the player
   */
  public String getEndGameMessage(Game game, Player player){
    String msg = "Game Over: ";
    String oponentsName = game.getOpponent(player).getName();
    Piece.PieceColor playerColor = game.getPlayerColor(player);
    switch (game.getEndGameCondition()){
      case WHITE_RESIGNED:
      case RED_RESIGNED:
        msg += oponentsName + " has resigned";
        break;
      case RED_OUT_OF_MOVES:
        if(playerColor == Piece.PieceColor.RED)
          msg += "You are out of moves. YOU LOSE";
        else
          msg += oponentsName + " is out of moves. YOU WIN";
        break;
      case RED_OUT_OF_PIECES:
        if(playerColor == Piece.PieceColor.RED)
          msg += "You are out of pieces. YOU LOSE";
        else
          msg += oponentsName + " is out of pieces. YOU WIN";
        break;
      case WHITE_OUT_OF_MOVES:
        if(playerColor == Piece.PieceColor.WHITE)
          msg += "You are out of moves. YOU LOSE";
        else
          msg += oponentsName + " is out of moves. YOU WIN";
        break;
      case WHITE_OUT_OF_PIECES:
        if(playerColor == Piece.PieceColor.WHITE)
          msg += "You are out of pieces. YOU LOSE";
        else
          msg += oponentsName + " is out of pieces. YOU WIN";
        break;
    }
    return msg;

  }
}

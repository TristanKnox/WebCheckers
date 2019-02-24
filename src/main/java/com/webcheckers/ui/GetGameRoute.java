package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.ui.ViewObjects.ViewGenorator;
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

public class GetGameRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  private final TemplateEngine templateEngine;
  private GameCenter gameCenter;
  public static final String GAME_TITLE = "Checkers";

  public GetGameRoute(final TemplateEngine templateEngine, GameCenter gameCenter) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gameCenter = gameCenter;
    LOG.config("GetGameRoute is initialized.");
  }

  public Object handle(Request request, Response response) {
    LOG.finer("GetGameRoute is invoked.");

    final Session session = request.session();
    /*
     * TODO
     * Create permanent variable for player attribute key
     */
    final Player player = session.attribute("player");
    Game game = gameCenter.getGame(player);

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", GAME_TITLE);

    vm.put("currentUser", player);
    vm.put("redPlayer", game.getPlayerOne());
    vm.put("whitePlayer", game.getPlayerTwo());
    /*
     * TODO
     * Add ability to keep track of active color
     */
    vm.put("activeColor", "red");
    /*
     * TODO
     * Add ability to select game view (will be an enhancement down the road)
     */
    vm.put("viewMode", "PLAY");
    vm.put("board", ViewGenorator.getView(game, player));
    
    // render the View
    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
  }
}

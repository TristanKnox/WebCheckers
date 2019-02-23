package com.webcheckers.ui;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Piece.PieceColor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class GetGameRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  private final TemplateEngine templateEngine;
  private static final String GAME_TITLE = "Checkers";

  public GetGameRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetGameRoute is initialized.");
  }

  public Object handle(Request request, Response response) {
    LOG.finer("GetGameRoute is invoked.");
    /*
     * TODO:
     * Add logic to insert data into the game ftl
     */
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", GAME_TITLE);
    // Fake user data
    Player playerOne = new Player("Sammily", PieceColor.RED);
    Player playerTwo = new Player("Bobbathy", PieceColor.WHITE);
    vm.put("currentUser", playerOne);
    vm.put("whitePlayer", playerOne);
    vm.put("redPlayer", playerTwo);
    vm.put("activeColor", "red");
    // Fake view mode
    vm.put("viewMode", "PLAY");
    // Fake board
    vm.put("board", new Game(playerOne, playerTwo));


    // render the View
    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
  }
}

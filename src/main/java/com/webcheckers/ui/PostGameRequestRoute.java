package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.ViewObjects.ViewGenerator;
import com.webcheckers.ui.WebServer;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 *  The UI controller to Post requests for starting a new game
 */
public class PostGameRequestRoute implements Route {
  GameCenter gameCenter;
  PlayerLobby playerLobby;
  TemplateEngine templateEngine;

  /**
   * Constructor - creates and instance of this class
   */
  public PostGameRequestRoute(TemplateEngine templateEngine, PlayerLobby playerLobby, GameCenter gameCenter){
    this.gameCenter = gameCenter;
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }


  @Override
  public Object handle(Request request, Response response){
    //Create the View Model to store need data
    Map<String, Object> vm  = new HashMap<>();

    //Get players names
    final Session session = request.session();
    //Get player from session
    Player playerOne = session.attribute(GetHomeRoute.PLAYER_KEY);
     //Get playerTwo username from posted data
    String playerTwoName = request.queryParams("otherUser");

    if(playerLobby.isInGame(playerLobby.getPlayer(playerTwoName)) || playerLobby.isInGame(playerOne)){
      session.attribute(GetHomeRoute.IN_GAME_ERROR_FLAG, true);
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }

    //Remove players from playerLobby
    playerOne = playerLobby.getPlayerForGame(playerOne.getName());
    Player playerTwo = playerLobby.getPlayerForGame(playerTwoName);

    //Inject players into game center to retrieve game
    Game game = gameCenter.spawnGame(playerOne,playerTwo);

    //Adding data to vm
    vm.put("title", GetGameRoute.GAME_TITLE);

    //TODO refactor
    vm.put("currentUser", playerOne);
    vm.put("redPlayer", game.getRedPlayer());
    vm.put("whitePlayer", game.getWhitePlayer());
    vm.put("activeColor", game.getActiveColor());
    vm.put("viewMode", "PLAY");
    vm.put("board", ViewGenerator.getView(game, game.getPlayerColor(playerOne)));


    // render the View
    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
  }
}

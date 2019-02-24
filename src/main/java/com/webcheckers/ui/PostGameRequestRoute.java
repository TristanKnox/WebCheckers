package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.ui.ViewObjects.ViewGenerator;
import spark.*;

import java.util.HashMap;
import java.util.Map;


/**
 *  The UI controller to Post requests for starting a new game
 */
public class PostGameRequestRoute implements Route {

  GameCenter gameCenter;
  PlayerLobby playerLobby;
  TemplateEngine templateEngine;


  public PostGameRequestRoute(TemplateEngine templateEngine, PlayerLobby playerLobby, GameCenter gameCenter){
    this.gameCenter = gameCenter;
    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }


  @Override
  public Object handle(Request request, Response response) throws Exception {
    //Create the View Model to store need data
    Map<String, Object> vm  = new HashMap<>();

    //Get players names
    final Session session = request.session();
    //TODO make sure that session was started in getHomeRoute and get access to PlayerServisKey
     Player playerOne = session.attribute(GetHomeRoute.PLAYER_KEY);
    //TODO make sure that the opponet name was stored on post request and get key
    String playerTwoName = request.queryParams("otherUser");


    //Remove players from playerLobby
    //TODO Make sure that the PlayerLobby way to remove a player using its username from the avialable player list and return that play
      playerOne = playerLobby.getPlayerForGame(playerOne.getName());
      Player playerTwo = playerLobby.getPlayerForGame(playerTwoName);

    //Inject players into game center to retrev game
    //TODO insure that GameCenter exissts and has a startGame method that takes 2 players and returns a game
    Game game = gameCenter.getGame(playerOne,playerTwo);


    Map<String, Object> vm = new HashMap<>();
    vm.put("title", GetGameRoute.GAME_TITLE);
    // Fake user data

    vm.put("currentUser", playerOne);
    vm.put("whitePlayer", playerOne);
    vm.put("redPlayer", playerTwo);
    vm.put("activeColor", "red");
    // Fake view mode
    vm.put("viewMode", "PLAY");
    // Fake board
    vm.put("board", ViewGenorator.getView(game, playerOne));


    // render the View
    return templateEngine.render(new ModelAndView(vm , "game.ftl"));

  }
}
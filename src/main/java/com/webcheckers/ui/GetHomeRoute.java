package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

import static spark.Spark.halt;

/**
* The Route which is responsible for rendering the home page.
* Capable of determining whether the player is signed in,
* and rendering the home page accordingly.
*
* @author Andrew Bado
*/
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  // values for use in the view-model map
  static final String WELCOME_MSG = "Welcome to the world of online Checkers.\n" +
    "%d players are currently logged in.";
  static final String TITLE_ATTR = "title";
  static final String VIEW_NAME = "home.ftl";
  static final String PERSONAL_WELCOME = "Welcome to Webcheckers, %s.";
  static final String CURRENT_USER_ATTR = "currentUser";
  static final String PLAYERS_LIST_ATTR = "players";

  // values for use in the session attribute map
  public static final String PLAYER_KEY = "player";
  static final String IN_GAME_ERROR_FLAG = "inGameError";

  // Attributes
  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  /**
  * Create the Spark Route (UI controller) to handle all GET / HTTP requests.
  *
  * @param templateEngine
  *   the HTML template rendering engine
  * @param playerLobby
  *   responsible of keeping track of all active players
  */
  public GetHomeRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby) {
    // neither parameter may be null
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
    LOG.config("GetHomeRoute is initialized.");
  }

    /**
    * Render the WebCheckers Home page.
    *
    * @param request the HTTP request
    * @param response he HTTP response
    *
    * @return the rendered HTML for the Home page
    */
    @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");

    // retrieve the session
    Session httpSession = request.session();

    // create the view-model bucket
    Map<String, Object> vm = new HashMap<>();

    // check if there is a player in the session map
    if(httpSession.attribute(PLAYER_KEY) == null){

      // begin filling the view bucket case: player not yet signed in
      vm.put(TITLE_ATTR, "Welcome!");
      vm.put("message", Message.info(String.format(WELCOME_MSG, playerLobby.getNumberOfUsers())));
    }
    // check that you have not been put in a game
    else if(playerLobby.isInGame(httpSession.attribute(PLAYER_KEY))){
      // if the player is in a game, redirect them to that game page
      response.redirect(WebServer.GAME_URL);
      halt();
      return null;
    }
    // check that you have not selected a busy player
    else if(httpSession.attribute(IN_GAME_ERROR_FLAG) == (Boolean)true){
      // build the vm case: there is a player, and they have just tried
      // to start a game with a player who is already in a game
      vmBuilderHelper(vm, httpSession);

      // give the home page an error message
      vm.put("message", Message.error("User has joined another game. Pick another user."));
      httpSession.attribute(IN_GAME_ERROR_FLAG, false);
    }
    // default home view
    else{
      // build the vm case: there is a player, and they are 'idling' on the home page
      vmBuilderHelper(vm, httpSession);

      // give the home page a personalized welcome message
      Player p = httpSession.attribute(PLAYER_KEY);
      vm.put("message", Message.info(String.format(PERSONAL_WELCOME, p.getName())));
    }

    // render the view
    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }

    /**
    * helper method to avoid duplicating code. Sets up the vm for the last two cases
    * in the large if statement in 'handle'.
    *
    * @param vm the bucket to put things in
    * @param httpSession the session
    */
  private void vmBuilderHelper(Map<String, Object> vm, Session httpSession){
    //begin filling the view bucket case: the player is signed in
    vm.put(TITLE_ATTR, "Homepage");

    // retrieve the player from the session
    Player p = httpSession.attribute(PLAYER_KEY);
    vm.put(CURRENT_USER_ATTR, p);
    vm.put(PLAYERS_LIST_ATTR, playerLobby.getAllAvalPlayers());
  }
}
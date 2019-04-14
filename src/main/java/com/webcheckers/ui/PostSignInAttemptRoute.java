package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static spark.Spark.halt;

/**
* Route which is responsible for reading in username entries.
* Takes in a username when the button on the sign in page is clicked,
* and determines whether to re-render the sign in page, or to re-direct to home.
*
* @author Andrew Bado
*/
public class PostSignInAttemptRoute implements Route {
  // Values used in the view-model map for rendering the game view after a sign in attempt
  static final String MESSAGE_ATTR = "message";
  static final String USERNAME_PARAM = "myUserName";
  static final Message TAKEN_USERNAME = Message.error("Username taken. Enter another to login.");
  static final Message INVALID_USERNAME = Message.error("Invalid username. " +
    "Username must start with a letter, and use only alphanumeric characters");
  static final String VIEW_NAME = "signin.ftl";

  // Attributes
  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;

  /**
  * The constructor for the POST /signinattempt route handler.
  *
  * @param playerLobby playerLobby, which keeps track of all current players
  * @param templateEngine template engine to use for rendering HTML page
  */
  PostSignInAttemptRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
    // neither parameter may be null
    Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    this.playerLobby = playerLobby;
    this.templateEngine = templateEngine;
  }

  /**
  * Obtains the username, asks playerLobby if it is valid. Then re-renders
  * the sign in page, or re-directs to home accordingly.
  *
  * @param request the HTTP request
  * @param response the HTTP response
  *
  * @return the rendered HTML for either the sign in page, or a redirect.
  */
  @Override
  public String handle(Request request, Response response) {
    // get the session
    Session httpSession = request.session();

    // if the player is already signed in, redirect them to the home page
    if(httpSession.attribute(GetHomeRoute.PLAYER_KEY) != null){
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }

    // start the View-Model
    final Map<String, Object> vm = new HashMap<>();

    // retrieve username parameter
    final String username = request.queryParams(USERNAME_PARAM);
    // store the result of adding the player to the playerLobby
    PlayerLobby.Outcome outcome = playerLobby.addPlayer(username);

    if(outcome == PlayerLobby.Outcome.SUCCESS) {
      // if the user was successfully logged in, store them in the session map,
      // initialize IN_GAME_ERROR_FLAG in the session map
      // and re-direct to home
      httpSession.attribute(GetHomeRoute.PLAYER_KEY, playerLobby.getPlayer(username));
      httpSession.attribute(GetHomeRoute.IN_GAME_ERROR_FLAG, false);
      httpSession.attribute(GetHomeRoute.NO_REPLAYS_ERROR_FLAG, false);
      System.out.println(username);
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;
    }
    else if(outcome == PlayerLobby.Outcome.INVALID){
      // if the username was invalid, render sign in with an error message
      vm.put(MESSAGE_ATTR, INVALID_USERNAME);
      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
    else{
      // the username was taken, so render sign in page with an error message
      vm.put(MESSAGE_ATTR, TAKEN_USERNAME);
      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
  }
}

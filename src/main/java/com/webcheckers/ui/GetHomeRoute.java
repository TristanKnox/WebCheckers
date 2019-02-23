package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  
  // values for use in the view-model map
  static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  static final String PLAYER_LOBBY_ATTR = "playerLobby";
  static final String TITLE_ATTR = "title";
  static final String VIEW_NAME = "home.ftl";
  static final String PERSONAL_WELCOME = "Welcome to webcheckers, %s.";
  static final String CURRENT_USER_ATTR = "currentUser";
  static final String PLAYERS_LIST_ATTR = "players";

  // values for use in the session attribute map
  static final String PLAYER_KEY = "player";

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
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
      vm.put(TITLE_ATTR, "Welcome!");
      // give the home page a welcome message
      vm.put("message", WELCOME_MSG);
    }
    else{
      vm.put(TITLE_ATTR, "Homepage");

      // retrieve the player from the session
      Player p = httpSession.attribute(PLAYER_KEY);
      vm.put(CURRENT_USER_ATTR, p);
      vm.put(PLAYERS_LIST_ATTR, playerLobby.getAllUserNames());

      // give the home page a personalized welcome message
      vm.put("message", Message.info(String.format(PERSONAL_WELCOME, p.getName())));
    }

    // render the view
    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }
}

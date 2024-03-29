package com.webcheckers.ui;

import com.webcheckers.model.BoardBuilder;
import com.webcheckers.ui.ReplayRoutes.*;
import static spark.Spark.*;
import com.webcheckers.appl.GameCenter;
import java.util.Objects;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayCenter;
import spark.TemplateEngine;



/**
 * The server that initializes the set of HTTP request handlers.
 * This defines the <em>web application interface</em> for this
 * WebCheckers application.
 *
 * <p>
 * There are multiple ways in which you can have the client issue a
 * request and the application generate responses to requests. If your team is
 * not careful when designing your approach, you can quickly create a mess
 * where no one can remember how a particular request is issued or the response
 * gets generated. Aim for consistency in your approach for similar
 * activities or requests.
 * </p>
 *
 * <p>Design choices for how the client makes a request include:
 * <ul>
 *     <li>Request URL</li>
 *     <li>HTTP verb for request (GET, POST, PUT, DELETE and so on)</li>
 *     <li><em>Optional:</em> Inclusion of request parameters</li>
 * </ul>
 * </p>
 *
 * <p>Design choices for generating a response to a request include:
 * <ul>
 *     <li>View templates with conditional elements</li>
 *     <li>Use different view templates based on results of executing the client request</li>
 *     <li>Redirecting to a different application URL</li>
 * </ul>
 * </p>
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class WebServer {
  private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

  //
  // Constants
  //

  /**
   * The URL pattern to request the Home page.
   */
  public static final String HOME_URL = "/";

  public static final String GAME_URL = "/game";

  public static final String SIGN_IN_URL = "/signin";

  public static final String REQUEST_GAME_URL = "/requestgame";

  public static final String TRY_USERNAME_URL = "/signinattempt";
  
  public static final String RESIGNATION_URL = "/resignGame";

  public static final String REQUEST_MOVE_URL = "/validateMove";

  public static final String REQUEST_TURN_SUBMISSION = "/submitTurn";

  public static final String CHECK_TURN_URL = "/checkTurn";

  public static final String BACKUP_URL = "/backupMove";

  public static final String SIGN_OUT_URL = "/signout";

  public static final String REPLAY_URL = "/replay";

  public static final String GET_REPLAY_URL = "/replay/game";

  public static final String POST_REPLAY_REQUEST_URL = "/replay/request";

  public static final String REPLAY_NEXT_TURN_URL = "/replay/nextTurn";

  public static final String REPLAY_PREVIOUS_TURN_URL = "/replay/previousTurn";

  public static final String EXIT_REPLAY_URL = "/replay/stopWatching";



  //
  // Attributes
  //

  private final TemplateEngine templateEngine;
  private final Gson gson;
  private final PlayerLobby playerLobby;
  private final GameCenter gameCenter;
  private final ReplayCenter replayCenter;

  //
  // Constructor
  //

  /**
   * The constructor for the Web Server.
   *
   * @param templateEngine
   *    The default {@link TemplateEngine} to render page-level HTML views.
   * @param gson
   *    The Google JSON parser object used to render Ajax responses.
   *
   * @throws NullPointerException
   *    If any of the parameters are {@code null}.
   */
  public WebServer(final TemplateEngine templateEngine, final Gson gson) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    Objects.requireNonNull(gson, "gson must not be null");
    //
    this.templateEngine = templateEngine;
    this.gson = gson;
    this.playerLobby = new PlayerLobby();
    this.gameCenter = new GameCenter();
    this.replayCenter = new ReplayCenter();
  }

  public void setBoardType(BoardBuilder.BoardType boardType){
    gameCenter.setBoardType(boardType);

  }

  //
  // Public methods
  //

  /**
   * Initialize all of the HTTP routes that make up this web application.
   *
   * <p>
   * Initialization of the web server includes defining the location for static
   * files, and defining all routes for processing client requests. The method
   * returns after the web server finishes its initialization.
   * </p>
   */
  public void initialize() {

    // Configuration to serve static files
    staticFileLocation("/public");

    //// Setting any route (or filter) in Spark triggers initialization of the
    //// embedded Jetty web server.

    //// A route is set for a request verb by specifying the path for the
    //// request, and the function callback (request, response) -> {} to
    //// process the request. The order that the routes are defined is
    //// important. The first route (request-path combination) that matches
    //// is the one which is invoked. Additional documentation is at
    //// http://sparkjava.com/documentation.html and in Spark tutorials.

    //// Each route (processing function) will check if the request is valid
    //// from the client that made the request. If it is valid, the route
    //// will extract the relevant data from the request and pass it to the
    //// application object delegated with executing the request. When the
    //// delegate completes execution of the request, the route will create
    //// the parameter map that the response template needs. The data will
    //// either be in the value the delegate returns to the route after
    //// executing the request, or the route will query other application
    //// objects for the data needed.

    //// FreeMarker defines the HTML response using templates. Additional
    //// documentation is at
    //// http://freemarker.org/docs/dgui_quickstart_template.html.
    //// The Spark FreeMarkerEngine lets you pass variable values to the
    //// template via a map. Additional information is in online
    //// tutorials such as
    //// http://benjamindparrish.azurewebsites.net/adding-freemarker-to-java-spark/.

    //// These route definitions are examples. You will define the routes
    //// that are appropriate for the HTTP client interface that you define.
    //// Create separate Route classes to handle each route; this keeps your
    //// code clean; using small classes.

    // Shows the Checkers game Home page.

    get(HOME_URL, new GetHomeRoute(templateEngine, playerLobby, gameCenter));

    get(SIGN_IN_URL, new GetSigninRoute(templateEngine));

    post(REQUEST_GAME_URL, new PostGameRequestRoute(templateEngine,playerLobby,gameCenter));

    post(TRY_USERNAME_URL, new PostSignInAttemptRoute(playerLobby,templateEngine));

    get(GAME_URL, new GetGameRoute(templateEngine, gameCenter, playerLobby,replayCenter));

    post(RESIGNATION_URL, new PostResignationRoute(playerLobby, gameCenter, replayCenter));

    post(REQUEST_MOVE_URL, new PostMoveRequestRoute(gameCenter));

    post(REQUEST_TURN_SUBMISSION, new PostSubmitTurnRoute(gson, gameCenter));

    post(CHECK_TURN_URL, new PostCheckTurnRoute(gson, gameCenter));

    post(BACKUP_URL, new PostBackupMoveRoute(gson, gameCenter));

    post(SIGN_OUT_URL, new PostSignOutRoute(playerLobby, gameCenter, replayCenter, templateEngine));

    //Replay Routes
    get(REPLAY_URL, new GetReplayHomeRoute(replayCenter, playerLobby, templateEngine));

    get(GET_REPLAY_URL, new GetReplayRoute(replayCenter,templateEngine));

    post(REPLAY_NEXT_TURN_URL, new PostNextTurnRoute(replayCenter));

    post(POST_REPLAY_REQUEST_URL,new PostRequestReplayRoute(replayCenter));

    post(REPLAY_PREVIOUS_TURN_URL, new PostPreviousTurnRoute(replayCenter, templateEngine));

    get(EXIT_REPLAY_URL, new PostExitReplayRoute(replayCenter));

    LOG.config("WebServer is initialized.");
  }

}
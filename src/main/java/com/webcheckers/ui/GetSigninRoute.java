package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

/**
* The route which is responsible for rendering the sign in page
* when the link from the home page is clicked.
*
* @author Andrew Bado
*/
public class GetSigninRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

  // Values used to build the view-model
  static final Message SIGN_IN = Message.info("Please enter a username to sign in.");

  // Attributes
  private final TemplateEngine templateEngine;

  /**
  * Create the Spark Route (UI controller) to handle all @code GET /signin HTTP requests.
  *
  * @param templateEngine  HTML template rendering engine
  */
  public GetSigninRoute(final TemplateEngine templateEngine) {
    // the template engine may not be null
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");

    LOG.config("GetSigninRoute is initialized.");
  }

  /**
  * Render the WebCheckers Signin page.
  *
  * @param request the HTTP request
  * @param response the HTTP response
  *
  * @return the rendered HTML for the Signin page
  */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetSigninRoute is invoked.");
    // start the view model bucket
    Map<String, Object> vm = new HashMap<>();

    // display a user message in the Home page
    vm.put("message", SIGN_IN);

    // render the View
    return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
  }
}

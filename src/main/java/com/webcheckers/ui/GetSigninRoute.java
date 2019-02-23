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
 * The UI Controller to GET the Signin page.
 *
 * @author Andrew Bado
 */
public class GetSigninRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

    private static final Message SIGN_IN = Message.info("Please enter a username to sign in.");
    private static final Message SIGN_IN_ERROR = Message.info("Username taken. Enter another to sign in.");

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSigninRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetSigninRoute is initialized.");
    }

    /**
     * Render the WebCheckers Signin page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Signin page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSigninRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();

        // display a user message in the Home page
        vm.put("message", SIGN_IN);

        // render the View
        return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
    }
}

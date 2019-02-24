package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import static spark.Spark.halt;

public class PostSignInAttemptRoute implements Route {

    // Values used in the view-model map for rendering the game view after a guess.
    static final String MESSAGE_ATTR = "message";
    static final String USERNAME_PARAM = "myUserName";
    static final Message TAKEN_USERNAME = Message.error("Username taken. Enter another to login.");
    static final Message INVALID_USERNAME = Message.error("Invalid username. " +
            "Username must start with a letter, and use only alphanumeric charachters");
    static final String VIEW_NAME = "signin.ftl";

    //
    // Attributes
    //

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    //
    // Constructor
    //

    /**
     * The constructor for the {@code POST /guess} route handler.
     *
     * @param playerLobby
     *    {@Link PlayerLobby} that holds over statistics
     *    {@Link GameCenter} that holds over statistics
     * @param templateEngine
     *    template engine to use for rendering HTML page
     *
     * @throws NullPointerException
     *    when the {@code gameCenter} or {@code templateEngine} parameter is null
     */
    PostSignInAttemptRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    //
    // TemplateViewRoute method
    //

    /**
     * {@inheritDoc}
     *
     * @throws NoSuchElementException
     *    when an invalid result is returned after making a guess
     */
    @Override
    public String handle(Request request, Response response) {
        // get the session
        Session httpSession = request.session();

        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        // retrieve request parameter
        final String username = request.queryParams(USERNAME_PARAM);

        PlayerLobby.Outcome outcome = playerLobby.addPlayer(username);
        if(outcome == PlayerLobby.Outcome.SUCCESS){
            httpSession.attribute(GetHomeRoute.PLAYER_KEY, playerLobby.getPlayer(username));
            httpSession.attribute(GetHomeRoute.IN_GAME_ERROR_FLAG, false);
            System.out.println(username);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }
        else if(outcome == PlayerLobby.Outcome.INVALID){
            vm.put(MESSAGE_ATTR, INVALID_USERNAME);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
        else{
            vm.put(MESSAGE_ATTR, TAKEN_USERNAME);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
    }
}

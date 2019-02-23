package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
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
    static final String MESSAGE_TYPE_ATTR = "message.type";
    static final String ERROR_TYPE = "ERROR";
    static final String USERNAME_PARAM = "myUserName";
    static final Message INVALID_USERNAME = Message.error("Username taken. Enter another to login.");
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
        // start building the View-Model
        final Map<String, Object> vm = new HashMap<>();

        // retrieve request parameter
        final String username = request.queryParams(USERNAME_PARAM);

        if(playerLobby.addPlayer(username)){
            vm.put("title", "Homepage");
            vm.put(GetHomeRoute.PLAYER_LOBBY_ATTR, playerLobby);
            System.out.println(username);
            return templateEngine.render(new ModelAndView(vm, "home.ftl"));
        }
        else{
            vm.put("message", INVALID_USERNAME);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }
    }
}

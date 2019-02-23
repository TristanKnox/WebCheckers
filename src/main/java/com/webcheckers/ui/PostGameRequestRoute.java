package com.webcheckers.ui;

import com.webcheckers.appl.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;


/**
 *  The UI controller to Post requests for starting a new game
 */
public class PostGameRequestRoute implements Route {


    public PostStartGameRoute(TemplateEngine templateEngine,)


    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, Object> vm  = new HashMap<>();

        final Session session = request.session();
        //TODO make sure that session was started in getHomeRoute and get access to PlayerServisKey
        Player player1 = session.attribute("PlayerServecisKey");
        //TODO make sure that the opponet name was stored on post request and get key
        String player2 = request.queryParams("oponentName");



        return null;
    }
}

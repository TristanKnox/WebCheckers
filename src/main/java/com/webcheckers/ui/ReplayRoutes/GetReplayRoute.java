package com.webcheckers.ui.ReplayRoutes;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.BoardBuilder;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.ViewObjects.ViewGenerator;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetReplayRoute implements Route {
    /**
     * Used to log messages to standard out
     **/
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
    /**
     * Used to render user pages
     **/
    private final TemplateEngine templateEngine;
    /**
     * Keeps track of the replays saved by the server
     **/
    private ReplayCenter replayCenter;

    public GetReplayRoute(ReplayCenter replayCenter, TemplateEngine templateEngine){
        this.replayCenter = replayCenter;
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        Session session = request.session();
        Player player = session.attribute(GetHomeRoute.PLAYER_KEY);

        String replayId = request.queryParams("replayID");

       // replayCenter.startReplay();
        return null;
    }
}
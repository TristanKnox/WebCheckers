package com.webcheckers.ui.ReplayRoutes;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.BoardBuilder;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece;
import com.webcheckers.ui.GetGameRoute;
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
        System.out.println("GetGameRoute");
        Session session = request.session();
        Player player = session.attribute(GetHomeRoute.PLAYER_KEY);

        System.out.println("GetReplayRoute: "+ replayCenter.getReplay(player));

        Replay replay = replayCenter.getReplay(player);

        Map<String, Object> vm  = new HashMap<>();
        vm.put(GetGameRoute.GAME_TITLE_ATTR,GetGameRoute.GAME_TITLE);
        vm.put("currentUser",player);
        vm.put("viewMode", "REPLAY");
        Map<String,Object> modeOptions = new HashMap<>();
        modeOptions.put("hasNext",replay.hasNext());
        modeOptions.put("hasPrevious",replay.hasPrev());
        Gson gson = new Gson();
        vm.put("modeOptionsAsJSON",gson.toJson(modeOptions));
        vm.put("redPlayer", replay.getPlayer1());
        vm.put("whitePlayer", replay.getPlayer2());
        Game game = new Game(replay.getPlayer1(),replay.getPlayer2(), replay.getCurrentBoardState());
        vm.put("activeColor", game.getActiveColor());
        vm.put("board",ViewGenerator.getView(game, Piece.PieceColor.RED));
        System.out.println("GetReplay: Render now");
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
}
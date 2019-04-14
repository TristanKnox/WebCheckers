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
        if(game.isGameOver()){
            Map<String, Object> modeOptions2 = new HashMap<String, Object>();
            modeOptions2.put("isGameOver", true);
            modeOptions2.put("gameOverMessage", getEndGameMessage(game, player));
            System.out.println("Game Over: " + game.getEndGameCondition());
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions2));
        }
        System.out.println("Render replay");
        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
    /**
     * Hleper method to build the end game message
     * @param game - the game the message is for
     * @param player - the player the message is for
     * @return - the message fro the player
     */
    public String getEndGameMessage(Game game, Player player){
        String msg = "Game Over: ";
        String oponentsName = game.getOpponent(player).getName();
        Piece.PieceColor playerColor = game.getPlayerColor(player);
        switch (game.getEndGameCondition()){
            case RED_RESIGNED:
                msg += game.getRedPlayer().getName() + " has resigned";
                break;

            case WHITE_RESIGNED:
                msg += game.getWhitePlayer().getName() + " has resigned";
                break;

            case RED_OUT_OF_MOVES:
                    msg += game.getRedPlayer().getName()+ " is out of moves. " + game.getWhitePlayer().getName()+" WINS";
                    break;

            case RED_OUT_OF_PIECES:
                    msg += game.getRedPlayer().getName()+ " is out of pieces. " + game.getWhitePlayer().getName()+" WINS";

                break;
            case WHITE_OUT_OF_MOVES:
                msg += game.getWhitePlayer().getName()+ " is out of moves. " + game.getRedPlayer().getName()+" WINS";
                break;
            case WHITE_OUT_OF_PIECES:
                msg += game.getWhitePlayer().getName()+ " is out of pieces. " + game.getRedPlayer().getName()+" WINS";
                break;
        }
        return msg;

    }
}
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
     * Keeps track of the current games and the players in them
     **/
    private GameCenter gameCenter;
    /**
     * Keeps track of the players logged into webcheckers
     **/
    private PlayerLobby playerLobby;
    /**
     * Keeps track of the replays saved by the server
     **/
    private ReplayCenter replayCenter;
    /**
     * The title of the game screen on the UI
     **/
    public static final String GAME_TITLE = "Checkers";

    public static final String GAME_TITLE_ATTR = "title";

    /**
     * Construct the game route with a template engine and game center
     *
     * @param templateEngine The template engine to render the client UI
     * @param gameCenter     The object that keeps track of all games and the users in the game
     */
    public GetReplayRoute(final TemplateEngine templateEngine, GameCenter gameCenter, PlayerLobby playerLobby, ReplayCenter replayCenter) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gameCenter = gameCenter;
        this.playerLobby = playerLobby;
        this.replayCenter = replayCenter;
        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * Handle request for the game. Returns the rendered game for the given user passed in from
     * the session.
     *
     * @param request  The request for the game
     * @param response The response of the game view
     * @return Rendered game view for the given player
     * @precondition The player has a game in game center already
     * @precondition The player is saved in the request session
     */
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");

        final Session session = request.session();

        Map<String, Object> vm = new HashMap<>();
        /*
         * TODO
         * Create permanent variable for player attribute key
         */
        final Player player = session.attribute(GetHomeRoute.PLAYER_KEY);

        Game game = null;

        if (session.attribute(GetHomeRoute.VIEW_MODE_ATTR) != null &&
                session.attribute(GetHomeRoute.VIEW_MODE_ATTR).equals("REPLAY")) {
            vm.put(GetHomeRoute.VIEW_MODE_ATTR, "REPLAY");
            vm.put(GAME_TITLE_ATTR, "Replay");
            Integer replayID = Integer.parseInt(request.queryParams("replayID"));
            game = new Game(replayCenter.getReplay(replayID).getPlayer1(), replayCenter.getReplay(replayID).getPlayer2(), BoardBuilder.BoardType.STANDARD);
        } else {
            vm.put(GetHomeRoute.VIEW_MODE_ATTR, "PLAY");
            vm.put(GAME_TITLE_ATTR, GAME_TITLE);
            game = gameCenter.getGame(player);
        }

        vm.put("board", ViewGenerator.getView(game, game.getPlayerColor(player)));

        if (game.isGameOver() && !session.attribute(GetHomeRoute.VIEW_MODE_ATTR).equals("REPLAY")) {
            gameCenter.exitGame(player);
            playerLobby.makeAvailable(player);

            replayCenter.storeReplay(game);

            Gson gson = new Gson();
            Map<String, Object> modeOptions = new HashMap<String, Object>();
            modeOptions.put("isGameOver", true);
            modeOptions.put("gameOverMessage", getEndGameMessage(game, player));
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
        }

        vm.put("currentUser", player);
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("activeColor", game.getActiveColor());

        // render the View
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
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
            case OPPONENT_RESIGNED:
                msg += oponentsName + " has resigned";
                break;
            case RED_OUT_OF_MOVES:
                if(playerColor == Piece.PieceColor.RED)
                    msg += "You are out of moves. YOU LOSE";
                else
                    msg += oponentsName + " is out of moves. YOU WIN";
                break;
            case RED_OUT_OF_PIECES:
                if(playerColor == Piece.PieceColor.RED)
                    msg += "You are out of pieces. YOU LOSE";
                else
                    msg += oponentsName + " is out of pieces. YOU WIN";
                break;
            case WHITE_OUT_OF_MOVES:
                if(playerColor == Piece.PieceColor.WHITE)
                    msg += "You are out of moves. YOU LOSE";
                else
                    msg += oponentsName + " is out of moves. YOU WIN";
                break;
            case WHITE_OUT_OF_PIECES:
                if(playerColor == Piece.PieceColor.WHITE)
                    msg += "You are out of pieces. YOU LOSE";
                else
                    msg += oponentsName + " is out of pieces. YOU WIN";
                break;
        }
        return msg;

    }
}

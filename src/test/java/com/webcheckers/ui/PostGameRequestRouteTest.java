package com.webcheckers.ui;


import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece;
import com.webcheckers.ui.ViewObjects.ViewGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostGameRequestRouteTest {

    private static final String POSTED_USER_NAME = "Tom";
    private static final String PLAYER1_USER_NAME = "Bob";
    private static final String POSTED_USER =   "otherUser";

    private PostGameRequestRoute CuT;


    // attributes holding mock objects
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    //player1 represents the user generating this responce
    private Player player1;
    //player2 represents the user that player1 is requesting a game with
    private Player player2;

    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private Game game;

    /**
     * Before each test set up mock elements
     */
    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        game = mock(Game.class);
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);
        player1 = mock(Player.class);
        player2 = mock(Player.class);

        when(player2.getName()).thenReturn(POSTED_USER_NAME);
        when(player1.getName()).thenReturn(PLAYER1_USER_NAME);

        when(request.queryParams(POSTED_USER)).thenReturn(POSTED_USER_NAME);
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player1);

        CuT = new PostGameRequestRoute(engine,playerLobby,gameCenter);
    }

    /**
     * Tests PostRequestGameRout when the selected player is not available for a game
     */
    //@Test
    public void player2_not_available(){
        when(playerLobby.getPlayer(POSTED_USER_NAME)).thenReturn(player2);
        when(playerLobby.isInGame(player2)).thenReturn(true);

        try {
            CuT.handle(request, response);
        }catch(Exception e){}
        verify(response).redirect(WebServer.HOME_URL);
        verify(session).attribute(eq(GetHomeRoute.IN_GAME_ERROR_FLAG),eq(true));
    }

    /**
     * Tests PostRequestGameRout when current user is not available for a game
     */
    //@Test
    public void player1_not_available(){
        when(playerLobby.isInGame(player1)).thenReturn(true);

        try {
            CuT.handle(request, response);
        }catch(Exception e){
            e.printStackTrace();
        }
        verify(response).redirect(WebServer.HOME_URL);
        verify(session).attribute(eq(GetHomeRoute.IN_GAME_ERROR_FLAG),eq(true));
    }

    /**
     * Tests the PostRequestGameRout when both players are available for a game
     */
    //@Test
    public void game_started(){
        //The posted name should be found in the player loby and that player should be avialable to play
        when(playerLobby.getPlayer(POSTED_USER_NAME)).thenReturn(player2);
        when(playerLobby.isInGame(player2)).thenReturn(false);
        //Remove both players from the lobby for the purpose of putting them into the game
        when(playerLobby.getPlayerForGame(player1.getName())).thenReturn(player1);
        when(playerLobby.getPlayerForGame(POSTED_USER_NAME)).thenReturn(player2);

        //setting up mock game data
        when(game.getRedPlayer()).thenReturn(player1);
        when(game.getWhitePlayer()).thenReturn(player2);
        when(game.getActivateColor()).thenReturn(Piece.PieceColor.RED);

        when(gameCenter.spawnGame(player1,player2)).thenReturn(game);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request,response);
        //Ensure ViewModel Exists and is a Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //Check all need attributes are present and acurate
        testHelper.assertViewModelAttribute("title", GetGameRoute.GAME_TITLE);
        testHelper.assertViewModelAttribute("currentUser", player1);
        testHelper.assertViewModelAttribute("redPlayer", player1);
        testHelper.assertViewModelAttribute("whitePlayer", player2);
        testHelper.assertViewModelAttribute("activeColor", Piece.PieceColor.RED);
        testHelper.assertViewModelAttribute("viewMode", "PLAY");
        testHelper.assertViewModelAttribute("board", ViewGenerator.getView(game,game.getPlayerColor(player1)));
    }


}



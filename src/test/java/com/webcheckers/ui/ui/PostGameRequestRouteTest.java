package com.webcheckers.ui.ui;


import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.PostGameRequestRoute;
import com.webcheckers.ui.TemplateEngineTester;
import com.webcheckers.ui.ViewObjects.ViewGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostGameRequestRouteTest {

    private static final String POSTED_USER_NAME = "Tom";
    private static final String PLAYER1_USER_NAME = "Bob";
    private static final String POSTED_USER =   "otherUser";

    private PostGameRequestRoute CuT;

    // friendly objects


    // attributes holding mock objects
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    //player1 reperesents the user generating this responce
    private Player player1;
    //player2 represents the user that player1 is requesting a game with
    private Player player2;

    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private Game game;


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


    @Test
    public void player2_not_available(){
        when(playerLobby.getPlayer(POSTED_USER_NAME)).thenReturn(player2);
        when(playerLobby.isInGame(player2)).thenReturn(true);

      //  final TemplateEngineTester testHelper = new TemplateEngineTester();
       // when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        assertThrows(HaltException.class, () -> CuT.handle(request,response));

       // CuT.handle(request,response);
       // assertTrue(session.attribute(GetHomeRoute.IN_GAME_ERROR_FLAG));
       // testHelper.assertViewModelExists();
       // testHelper.assertViewModelIsaMap();
        //testHelper.assertViewModelAttribute(GetHomeRoute.IN_GAME_ERROR_FLAG,true);//TODO figure out how to check for this after exception is thrown
    }

    @Test
    public void game_started(){
        when(playerLobby.getPlayer(POSTED_USER_NAME)).thenReturn(player2);
        when(playerLobby.isInGame(player2)).thenReturn(false);

        when(playerLobby.getPlayerForGame(player1.getName())).thenReturn(player1);
        when(playerLobby.getPlayerForGame(POSTED_USER_NAME)).thenReturn(player2);
        when(game.getRedPlayer()).thenReturn(player1);
        when(game.getWhitePlayer()).thenReturn(player2);
        when(game.getActivateColor()).thenReturn(Piece.PieceColor.RED);

        when(gameCenter.spawnGame(player1,player2)).thenReturn(game);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request,response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", GetGameRoute.GAME_TITLE);
        testHelper.assertViewModelAttribute("currentUser", player1);
        testHelper.assertViewModelAttribute("redPlayer", player1);
        testHelper.assertViewModelAttribute("whitePlayer", player2);
        testHelper.assertViewModelAttribute("activeColor", Piece.PieceColor.RED);
        testHelper.assertViewModelAttribute("viewMode", "PLAY");
        testHelper.assertViewModelAttribute("board", ViewGenerator.getView(game,game.getPlayerColor(player1)));
    }


}



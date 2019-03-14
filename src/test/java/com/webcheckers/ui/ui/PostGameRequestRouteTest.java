package com.webcheckers.ui.ui;


import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.PostGameRequestRoute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostGameRequestRouteTest {

    private static final String POSTED_USER_NAME = "Tom";
    private static final String POSTED_USER =   "otherUser";

    private PostGameRequestRoute CuT;

    // friendly objects
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;

    // attributes holding mock objects
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    //player1 reperesents the user generating this responce
    private Player player1;
    //player2 represents the user that player1 is requesting a game with
    private Player player2;


    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        player1 = mock(Player.class);
        player2 = mock(Player.class);
        when(player2.getName()).thenReturn(POSTED_USER_NAME);
    }


    @Test
    public void player2_not_available(){
        when(request.queryParams(POSTED_USER)).thenReturn(POSTED_USER_NAME);
        playerLobby = mock(PlayerLobby.class);
        when(playerLobby.getPlayer(POSTED_USER_NAME)).thenReturn(player2);
        when(playerLobby.isInGame(player2)).thenReturn(true);



    }


}



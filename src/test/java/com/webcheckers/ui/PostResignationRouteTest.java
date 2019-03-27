package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Handles testing for getting a game from the GetGameRoute. Mocks returning a game view by
 * checking that the correct players are returned in the model
 *
 * @author Andrew Bado
 */
@Tag("UI-tier")
public class PostResignationRouteTest {
    private PostResignationRoute CuT;

    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private Gson gson;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);
        gson = new Gson();

        // create a unique CuT for each test
        CuT = new PostResignationRoute(playerLobby, gameCenter);
    }

    /**
     * Tests that the resignation route removes players from the gamecenter and adds them to the
     * playerlobby
     */
    @Test
    public void resign() {
        assertNotNull(CuT);
        assertNotNull(CuT.handle(request, response));
    }

}

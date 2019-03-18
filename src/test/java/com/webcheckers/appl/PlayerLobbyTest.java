package com.webcheckers.appl;

import com.webcheckers.Application;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @Author Jacob Jirinec
 * Unit testing for the Application tier class PlayerLobby.
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

  private PlayerLobby CuT;
  private static final String INVALID_NAME1 = "1looo";
  private static final String INVALID_NAME2 = "";
  private static final String INVALID_NAME3 = "Jacob!";
  private static final String INVALID_NAME4 = "4523451234";
  private static final String VALID_NAME1 = "Johnson";
  private static final String VALID_NAME2 = "Jacob999";
  private static final String VALID_NAME3 = "Jacob Jirinec";

  @BeforeAll
  public void testNames(){
    CuT = new PlayerLobby();
    assertSame(Outcome.SUCCESS,Cut.addPlayer(VALID_NAME1))
    assertSame(Outcome.SUCCESS,Cut.addPlayer(VALID_NAME2))
    assertSame(Outcome.SUCCESS,Cut.addPlayer(VALID_NAME3))
    assertSame(Outcome.INVALID,Cut.addPlayer(INVALID_NAME1))
    assertSame(Outcome.INVALID,Cut.addPlayer(INVALID_NAME2))
    assertSame(Outcome.INVALID,Cut.addPlayer(INVALID_NAME3))
    assertSame(Outcome.INVALID,Cut.addPlayer(INVALID_NAME4))
  }
  //constructors
  @Test
  public void construct_test(){
    PlayerLobby playLobby = new PlayerLobby();
    assertNotNull(gameCenter);
    assertTrue(playLobby instanceof PlayerLobby);
  }
  @Test
  public void addPlayer_test(){
    PlayerLobby playLobby = new PlayerLobby();
    playLobby.addPlayer(VALID_NAME1)
    assertNotNull(playLobby.getPlayer(VALID_NAME1)
    playLobby.addPlayer(INVALID_NAME1)
    assertTrue(playLobby.getPlayer(VALID_NAME1) instanceof Player)
}
@Test
  public void logout_test(){
    PlayerLobby playLobby = new PlayerLobby();
    playLobby.addPlayer(VALID_NAME1)
    assertNotNull(playLobby.getPlayer(VALID_NAME1)
    playLobby.logout(VALID_NAME1)
    assertNull(playLobby.getPlayer(VALID_NAME1)
}
@Test
  public void leaveForGame_test(){
    PlayerLobby playLobby = new PlayerLobby();
    playLobby.addPlayer(VALID_NAME1)
    assertNotNull(playLobby.getPlayer(VALID_NAME1)
    playLobby.remove(VALID_NAME1)
    assertSame(playerLobby.avalPlayers.containsKey(VALID_NAME1), False)
}
}

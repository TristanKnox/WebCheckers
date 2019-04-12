package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.model.ai.AIPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.eclipse.jetty.webapp.MetaDataComplete.False;
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

  @BeforeEach
  public void testNames(){
    CuT = new PlayerLobby();
    assertSame(PlayerLobby.Outcome.SUCCESS,CuT.addPlayer(VALID_NAME1));
    CuT.addPlayer(VALID_NAME1);
    assertSame(PlayerLobby.Outcome.SUCCESS,CuT.addPlayer(VALID_NAME2));
    CuT.addPlayer(VALID_NAME2);
    assertSame(PlayerLobby.Outcome.SUCCESS,CuT.addPlayer(VALID_NAME3));
    CuT.addPlayer(VALID_NAME3);
    assertSame(PlayerLobby.Outcome.INVALID,CuT.addPlayer(INVALID_NAME1));
    assertSame(PlayerLobby.Outcome.INVALID,CuT.addPlayer(INVALID_NAME2));
    assertSame(PlayerLobby.Outcome.INVALID,CuT.addPlayer(INVALID_NAME3));
    assertSame(PlayerLobby.Outcome.INVALID,CuT.addPlayer(INVALID_NAME4));
  }
  //constructors
  @Test
  public void construct_test(){
    PlayerLobby playLobby = new PlayerLobby();
    assertNotNull(playLobby);
    assertTrue(playLobby instanceof PlayerLobby);
  }
  @Test
  public void addPlayer_test(){
    PlayerLobby playLobby = new PlayerLobby();
    playLobby.addPlayer(VALID_NAME1);
    assertNotNull(playLobby.getPlayer(VALID_NAME1));
    playLobby.addPlayer(INVALID_NAME1);
    assertTrue(playLobby.getPlayer(VALID_NAME1) instanceof Player);
  }

  @Test
  public void logout_test(){
    PlayerLobby playLobby = new PlayerLobby();
    playLobby.addPlayer(VALID_NAME1);
    assertNotNull(playLobby.getPlayer(VALID_NAME1));
    playLobby.logout(VALID_NAME1);
    assertNull(playLobby.getPlayer(VALID_NAME1));
  }

    @Test
    public void leaveForGame_test(){
    PlayerLobby playLobby = new PlayerLobby();
    playLobby.addPlayer(VALID_NAME1);
    assertNotNull(playLobby.getPlayer(VALID_NAME1));
    playLobby.removePlayer(VALID_NAME1);
    assertSame(playLobby.getAllAvalPlayers().contains(playLobby.getPlayer(VALID_NAME1)),false);
  }

  /**
   * Basic tests for the AI player in the player lobby. AI players are available players
   * by default and cannot be added to / removed from the player lobby after the lobby
   * is created.
   */
  @Test
  public void addRemoveAI() {
    PlayerLobby CuT = new PlayerLobby();
    AIPlayer easyAI = PlayerLobby.easyAI;
    AIPlayer hardAI = PlayerLobby.hardAI;

    // Both AI players should be in the lobby by default
    assertEquals(CuT.getPlayer(easyAI.getName()), easyAI);
    assertEquals(CuT.getPlayer(hardAI.getName()), hardAI);

    // Cannot remove AIs from list of available users
    CuT.getPlayerForGame(easyAI.getName());
    CuT.getPlayerForGame(hardAI.getName());
    assertFalse(CuT.isInGame(easyAI));
    assertFalse(CuT.isInGame(hardAI));
  }
}

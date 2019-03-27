package com.webcheckers.appl;

import com.webcheckers.Application;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @Author Evan Nolan
 * Unit testing for the Application tier class GameCenter.
 */
@Tag("Application-tier")
public class GameCenterTest {

  //Friendlies! Woo i wanna go
  private String playerName1;
  private String playerName2;
  private Player nullPlayer;
  private Player player1;
  private Player player2;
  private Player player3;

  private GameCenter CuT;
  private static final String MY_FAVORITE_LOGIN = "KAKAKAKAKAKA";
  private static final String VALID_NAME = "Kristen";
  private static final String YONDER = "Yonder999";

  @BeforeEach
  public void testSetups(){
    CuT = new GameCenter();
    playerName1 =  MY_FAVORITE_LOGIN;
    playerName2 = VALID_NAME;
    player1 = new Player(playerName1);
    player2 = new Player(playerName2);
    nullPlayer = null;
    player3 = new Player(YONDER);

  }
  //constructors
  @Test
  public  void construct_test(){
    GameCenter gameCenter = new GameCenter();
    assertNotNull(gameCenter);
    assertTrue(gameCenter instanceof GameCenter);
  }
  //Gonna get down with the BUSINESS logic testing
  @Test
  public void test_SpawnGame(){
    CuT.spawnGame(player1,player2);
    assertEquals(CuT.getActiveGames().get(player1),(CuT.getActiveGames().get(player2)));
  }
  @Test
  public void test_inGame(){
    CuT.spawnGame(player1,player2);
    assertEquals(CuT.getOtherPlayer(player1), player2);
    assertEquals(CuT.getOtherPlayer(player2), player1);
    assertNull(CuT.getOtherPlayer(player3));
  }
  @Test
  public void test_removedFromMatch(){
    CuT.spawnGame(player1,player2);
    CuT.removePlayersFromMatch(player1,player2);
    assertNull(CuT.getOtherPlayer(player1));
    assertNull(CuT.getOtherPlayer(player2));
  }
  @Test
  public void test_removeFromGame(){
    CuT.spawnGame(player1,player2);
    CuT.removePlayerFromGame(player1);
    assertNull(CuT.getGame(player1));
    assertNotNull(CuT.getGame(player2));
    CuT.removePlayerFromGame(player2);
    assertNull(CuT.getGame(player2));
  }
  @Test
  public void test_resign(){
    CuT.removePlayerFromGame(player2);
    CuT.removePlayerFromGame(player1);
    assertNull(CuT.getOtherPlayer(player1));
    assertNull(CuT.getOtherPlayer(player2));
  }

}
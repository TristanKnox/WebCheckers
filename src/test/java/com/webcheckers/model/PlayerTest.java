package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test cases for the Player Class. Handles checking basic functionality such as
 * getting the name of the player. Also checks the hash function to make sure
 * that an expected hash is created. Also checks several cases with the equal method
 *
 * @author Collin Bolles
 */
@Tag("Model-tier")
public class PlayerTest {

  /**
   * Handles testing to make sure the player name can be retrieved from the player
   * object successfully.
   */
  @Test
  public void testPlayerName() {
    String expectedName = "Jimmothy";
    Player testPlayer = new Player(expectedName);
    assertEquals(expectedName, testPlayer.getName());
  }

  /**
   * Tests the hashCode of the player object. Should be the hash of the name so
   * the hashCode of the player should be the same as the hashCode of the name
   * passed in
   */
  @Test
  public void testHashCode() {
    // Test with name Bobby
    String nameOne = "Bobby";
    int expectedHashOne = nameOne.hashCode();
    Player testPlayerOne = new Player(nameOne);
    assertEquals(expectedHashOne, testPlayerOne.hashCode());

    // Test with name Sam
    String nameTwo = "Sam";
    int expectedHashTwo = nameTwo.hashCode();
    Player testPlayerTwo = new Player(nameTwo);
    assertEquals(expectedHashTwo, testPlayerTwo.hashCode());
  }

  /**
   * Test the equals method in several different cases. Checks with different
   * classes ex) calling equals with a string passed in instead of a player.
   * Also tests with two identical objects (same memory). Also tests two players
   * that have different names and two players with the same name.
   */
  @Test
  public void testEquals() {
    // Test with different classes
    String nameOne = "Sammy";

    Player testPlayerOne = new Player(nameOne);
    assertFalse(testPlayerOne.equals(nameOne));

    // Test with same objects
    Player testPlayerTwo = testPlayerOne;
    assertEquals(testPlayerOne, testPlayerTwo);

    // Test with different player objects, not equal
    Player testPlayerThree = new Player("Sam");
    assertNotEquals(testPlayerOne, testPlayerThree);

    // Test with different player objects, equal
    Player testPlayerFour = new Player(nameOne);
    assertEquals(testPlayerOne, testPlayerFour);
  }
}

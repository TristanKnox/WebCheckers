package com.webcheckers.model.ai;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.webcheckers.model.ai.AIPlayer.Difficulty;
import com.webcheckers.model.checkers.Turn;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Will house the logic for testing the AIPlayer, currently, since there is no
 * functionality there are no tests written for the AIPlayer
 *
 * @author Collin Bolles
 */
@Tag("Model-tier")
public class AIPlayerTest {

  @Test
  public void testCreationOfAI() {
    assertNotNull(new AIPlayer("Bobby", Difficulty.HARD));
  }

  @Test
  public void testGetRandomTurn() {
    AIPlayer CuT = new AIPlayer("Bob", Difficulty.HARD);
    assertThrows(IllegalArgumentException.class, () -> {
      CuT.getRandomTurn(new ArrayList<>());
    });

    Turn testTurn = new Turn(null);
    List<Turn> turns = new ArrayList<>();
    turns.add(testTurn);
    assertEquals(testTurn, CuT.getRandomTurn(turns));
  }


}

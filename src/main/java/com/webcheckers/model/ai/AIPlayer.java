package com.webcheckers.model.ai;

import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Move;
import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Position;
import com.webcheckers.model.checkers.Turn;
import java.util.List;
import sun.security.krb5.internal.EncASRepPart;

/**
 * The AIPlayer represents the AI that humans can play against. The AIPlayer
 * can check for when it is its turn then make a move depending on its difficulty
 * level.
 *
 * @author Collin Bolles
 */
public class AIPlayer extends Player {

  /**
   * Currently supports two different difficulties, Easy and Medium
   */
  public enum Difficulty {
    EASY,
    HARD
  }

  /** Since the AI never starts a game, it is always white */
  private static PieceColor color = PieceColor.WHITE;
  /** Represents the difficulty of this AI */
  private Difficulty difficulty;

  /**
   * Create an AI player with a given name and difficulty level
   * @param name The name of the AI player
   * @param difficulty The difficulty level of the AI
   */
  public AIPlayer(String name, Difficulty difficulty) {
    super(name);
    this.difficulty = difficulty;
  }

  private void makeEasyMove(Game game) {
    AITurnUtil util = new AITurnUtil();
    Move move = util.getSimpleMove(game, color);
    if(move != null) {
      game.addMove(move);
      game.executeTurn();
    }
  }

  private void makeHardMove(Game game) {

  }

  /**
   * The call to make a move against the passed in turn. When this method is
   * called, a move is decided on and executed on the game
   * @param game The game to make the move on
   */
  public synchronized void makeMove(Game game) {
    switch (difficulty) {
      case EASY:
        makeEasyMove(game);
        break;
      case HARD:
        makeHardMove(game);
        break;
      default:
        //Should not get here
    }
  }
}

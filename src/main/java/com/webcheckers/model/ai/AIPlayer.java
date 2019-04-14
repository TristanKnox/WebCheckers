package com.webcheckers.model.ai;

import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Move;
import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Turn;
import java.util.List;
import java.util.Random;

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

  /**
   * Utility method for getting a random move from a list of possible moves.
   * @return A random turn from the list of turns
   */
  private Turn getRandomTurn(List<Turn> turns) {
    Random r = new Random();
    return turns.get(r.nextInt(turns.size()));
  }

  /**
   * Easy moves are defined as always making a single jump when possible and only making
   * multi-jumps if it legally has to
   * @param game The game to make the move on
   */
  private void makeEasyMove(Game game) {
    AITurnUtil utilTurn = new AITurnUtil(game, color);
    Turn turn;
    if(utilTurn.getPossibleSingleJumps().size() > 0) {
      turn = getRandomTurn(utilTurn.getPossibleSingleJumps());
    }
    else if(utilTurn.getPossibleMultiJumps().size() > 0) {
      turn = getRandomTurn(utilTurn.getPossibleMultiJumps());
    }
    else {
      turn = getRandomTurn(utilTurn.getPossibleSimpleTurn());
    }

    for(Move move: turn.getMoves())
      game.addMove(move);
    game.executeTurn();
  }

  /**
   * Hard moves are defined as making multi-jumps whenever possible and only single jumping
   * when it has to
   * @param game The game to make the move on
   */
  private void makeHardMove(Game game) {
    AITurnUtil utilTurn = new AITurnUtil(game, color);
    Turn turn;
    if(utilTurn.getPossibleMultiJumps().size() > 0) {
      turn = getRandomTurn(utilTurn.getPossibleMultiJumps());
    }
    else if(utilTurn.getPossibleSingleJumps().size() > 0) {
      turn = getRandomTurn(utilTurn.getPossibleSingleJumps());
    }
    else {
      turn = getRandomTurn(utilTurn.getPossibleSimpleTurn());
    }

    for(Move move: turn.getMoves())
      game.addMove(move);
    game.executeTurn();
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

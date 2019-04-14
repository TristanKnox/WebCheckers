package com.webcheckers.model.ai;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Move;
import com.webcheckers.model.checkers.Piece;
import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Position;
import com.webcheckers.model.checkers.Turn;
import com.webcheckers.model.checkers.Turn.TurnResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * The AIUtil contains helper methods for making various turns as an AI player. It has the ability
 * to get lists of different move types that can be used as seen fit by the AI/
 *
 * @author Collin Bolles
 */
public class AITurnUtil {

  /** The game that the move will be made on */
  private Game game;
  /** The color of the player making the move */
  private PieceColor color;

  /** A list of legal turns that only make simple turns */
  private List<Turn> possibleSimpleTurns;
  /** A list of legal single jump moves */
  private List<Turn> possibleSingleJumps;
  /** A list of legal multi-jump moves */
  private List<Turn> possibleMultiJumps;

  /**
   * Create a util with the given game and color making the move. Populates the lists with the
   * different moves that are possible
   * @param game The game the move will be made on
   * @param color The color of the player making the turn
   */
  public AITurnUtil(Game game, PieceColor color) {
    this.game = game;
    this.color = color;

    this.possibleSimpleTurns = new LinkedList<>();
    this.possibleSingleJumps = new LinkedList<>();
    this.possibleMultiJumps = new LinkedList<>();

    setSimpleMoves();
    setJumpMoves();
  }

  /**
   * Set the list of possible simple moves
   */
  private void setSimpleMoves() {
    List<Position> piecePositions = game.getPiecePositions(color);

    Turn utilTurn = new Turn(color);

    // Go through all of the possible starting positions
    for(Position startPosition: piecePositions) {
      List<Position> endPositions = utilTurn.getPossibleSimpleMoves(startPosition);
      // Go through all of the end positions
      for(Position endPosition: endPositions) {
        Move testMove = new Move(startPosition, endPosition);
        Turn testTurn = new Turn(color);
        // If a valid simple move is found, then return it
        if(testTurn.addMove(game, testMove) == TurnResponse.VALID_TURN) {
          possibleSimpleTurns.add(testTurn);
        }
      }
    }
  }

  /**
   * Get all of the jumps that are possible with a given game configuration. Note that the returned
   * moves are only the start of jumps ie) If the jump is actually a multi-jump, it will not
   * have it completed.
   * @return A list of moves representing the start of all possible jumps
   */
  private List<Turn> getAllJumps() {
    List<Position> piecePositions = game.getPiecePositions(color);

    List<Turn> possibleTurns = new LinkedList<>();
    Turn utilTurn = new Turn(color);

    // Go through all of the starting positions
    for(Position startPosition: piecePositions) {
      Piece piece = utilTurn.getPiece(game, startPosition);
      List<Position> endPositions = utilTurn.getPossibleJumpPositions(startPosition, piece);
      for(Position endPosition: endPositions) {
        Move testMove = new Move(startPosition, endPosition);
        Turn testTurn = new Turn(color);
        if(testTurn.addMove(game, testMove) == TurnResponse.VALID_TURN)
          possibleTurns.add(testTurn);
      }
    }

    return possibleTurns;
  }

  /**
   * Utility method for getting the next legal jump move for the given turn. If there is another
   * jump to be made, the first one is returned. If no new move is possible, then null is returned
   * @param turn The turn to add the next jump onto
   * @return A move representing the next jump
   */
  private Move getNextJump(Turn turn) {
    Move firstMove = turn.getMoves().get(0);
    Move recentMove = turn.getMoves().get(turn.getMoves().size() - 1);
    Piece movedPiece = turn.getPiece(game, firstMove.getStart());

    List<Position> possibleJumpPositions = turn.getPossibleJumpPositions(recentMove.getEnd(), movedPiece);
    for(Position endPosition: possibleJumpPositions) {
      Move newMove = new Move(recentMove.getEnd(), endPosition);
      if(turn.pieceCanJumpToPos(newMove, game))
        return newMove;
    }
    return null;
  }

  /**
   * Handles populating both the list of possible single jumps and multi-jumps
   */
  private void setJumpMoves() {
    List<Turn> allJumps = getAllJumps();
    for(Turn turn: allJumps) {
      if(turn.isComplete(game))
        possibleSingleJumps.add(turn);
      else {
        while(!turn.isComplete(game)) {
          turn.addMove(game, getNextJump(turn));
        }
        possibleMultiJumps.add(turn);
      }
    }
  }

  /**
   * Get the list of possible simple turns
   * @return The list of possible simple turns
   */
  public List<Turn> getPossibleSimpleTurn() {
    return possibleSimpleTurns;
  }

  /**
   * Get the list of possible single jumps
   * @return The list of possible single jumps
   */
  public List<Turn> getPossibleSingleJumps() {
    return possibleSingleJumps;
  }

  /**
   * Get the list of possible multi-jumps
   * @return The list of possible multi-jumps
   */
  public List<Turn> getPossibleMultiJumps() {
    return possibleMultiJumps;
  }
}

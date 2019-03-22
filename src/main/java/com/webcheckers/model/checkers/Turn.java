package com.webcheckers.model.checkers;

import com.webcheckers.model.checkers.Piece.PieceColor;
import java.util.List;

/**
 * The Turn class keeps track of a series of moves made during a single turn of the user. The turn
 * is associated with a given color based on the player who is making the turn. This class also
 * includes the logic for validating itself against a given game. The result of the validation is
 * any potential rule that may have been broken
 *
 * @author Collin Bolles
 */
public class Turn {

  /**
   * Represents different rules that could potentially be broken during a turn. Also contains
   * the `Valid_TURN` option if no rules have been broken
   */
  public enum TurnResponse {
    SPACE_TAKEN,
    MUST_JUMP,
    VALID_TURN
  }

  /** The color of that is currently making the turn */
  private PieceColor turnColor;
  /** All moves made during theis specific turn */
  private List<Move> moves;

  /**
   * Handles adding the move to the list of moves if the move is valid. Validation occurs in here
   * and the result of the validation is returned. If the addition of the move would make the turn
   * to be invalid, the move will not be added and the broken rule will be returned. If the addition
   * of the move is valid, then a valid response will be returned and the move will be added to
   * the list of moves
   * @param game The game to validate the move/turn against
   * @param move The move to add to the list of moves for this turn
   * @return The broken rule if the move is invalid, valid turn response if the move is valid
   */
  public TurnResponse addMove(Game game, Move move) {
    return null;
  }

  /**
   * Get the color that is making this turn
   * @return The color of this turn
   */
  public PieceColor getTurnColor() {
    return turnColor;
  }

  /**
   * Get all of the valid moves made during this turn
   * @return All moves during this turn
   */
  public List<Move> getMoves() {
    return moves;
  }
}

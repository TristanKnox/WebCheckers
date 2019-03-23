package com.webcheckers.model.checkers;

import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Piece.PieceType;
import com.webcheckers.model.checkers.Space.SpaceType;
import java.util.ArrayList;
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
    INCORRECT_PIECE_USED,
    MOVE_TO_WHITE_SPACE,
    INVALID_MULTI_MOVE,
    INVALID_DIRECTION,
    MUST_JUMP,
    VALID_TURN
  }

  /** The color of that is currently making the turn */
  private PieceColor turnColor;
  /** All moves made during this specific turn */
  private List<Move> moves;

  /**
   * Create a new turn with a given color. Initialize moves to be an empty list
   * @param turnColor The color of the active player
   */
  public Turn(PieceColor turnColor) {
    this.turnColor = turnColor;
    this.moves = new ArrayList<>();
  }

  private Piece getPiece(Game game, Position pos) {
    return game.getSpace(pos).getPiece();
  }

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
    Piece piece = moves.size() == 0 ? getPiece(game, move.getStart())
        : getPiece(game, moves.get(0).getStart());
    // Check that the player is moving the correct color piece
    if(piece.getColor() != turnColor)
        return TurnResponse.INCORRECT_PIECE_USED;
    // Check that the move is to a black tile
    if(game.getSpace(move.getEnd()).getType() != SpaceType.BLACK)
        return TurnResponse.MOVE_TO_WHITE_SPACE;
    // Check that the position from the last move is possible
    if(moves.size() > 0) {
      Move previousMove = moves.get(moves.size() - 1);
      if(!previousMove.getEnd().equals(move.getStart()))
        return TurnResponse.INVALID_MULTI_MOVE;
    }
    // Check direction of the move
    if(piece.getType() == PieceType.KING) {
      boolean xOffSetValid = Math.abs(move.getStart().getCell() - move.getEnd().getCell()) == 1;
      boolean yOffSetValid = Math.abs(move.getStart().getRow() - move.getEnd().getRow()) == 1;
      if(!xOffSetValid || !yOffSetValid)
        return TurnResponse.INVALID_DIRECTION;
    }
    else {
      boolean xOffSetValid = Math.abs(move.getStart().getCell() - move.getEnd().getCell()) == 1;
      int validDirection = turnColor == PieceColor.RED ? 1 : -1;
      boolean yOffSetValid = move.getStart().getRow() - move.getEnd().getRow() == validDirection;
      if(!xOffSetValid || !yOffSetValid)
        return TurnResponse.INVALID_DIRECTION;
    }
    // Check that a jump is not possible and the user is making a simple move
    // Check that the current space is not occupied
    if(game.getSpace(move.getEnd()).getPiece() != null)
      return TurnResponse.SPACE_TAKEN;
    return TurnResponse.VALID_TURN;
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

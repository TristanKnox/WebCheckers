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

  public Piece getPiece(Game game, Position pos) {
    return game.getSpace(pos).getPiece();
  }

  /**
   * Makes sure that the color used during a move and the color of the turn match
   * @param usedColor The color of the piece used during the move
   * @return True if the used and correct colors match
   */
  public boolean correctPieceUsed(PieceColor usedColor) {
    return usedColor == turnColor;
  }

  /**
   * Checks to make sure that the move is to a black tile
   * @param game The game state
   * @param move The move attempted to be made
   * @return True if the end position is a black tile
   */
  public boolean moveToBlack(Game game, Move move) {
    SpaceType targetSpaceType = game.getSpace(move.getEnd()).getType();
    return targetSpaceType == SpaceType.BLACK;
  }

  /**
   * Checks to make sure that the move path is possible. If multiple moves are made in a single turn
   * the the end of the first move should be the start of the second move.
   * @param move The move to check for the start position of
   * @return True if this is the first move of the turn or if the end of the previous turn is
   * the start of this new move
   */
  public boolean movePathPossible(Move move) {
    if(moves.size() > 0) {
      Move previousMove = moves.get(moves.size() - 1);
      return previousMove.getEnd().equals(move.getStart());
    }
    return true;
  }

  /**
   * Handles checking if a piece is moving in a valid direction based on its type and color.
   * Kings are moving in the correct direction if they are moving diagonal in any direction. Red
   * pieces are moving in the correct direction if they are moving diagonal in the positive
   * direction. White pieces are moving in the correct direction if they are moving diagonal in
   * the negative direction.
   * TODO: ADD LOGIC FOR HANDLING WHEN A MOVE IS A JUMP/MULTI-JUMP
   * @param piece The piece being moved
   * @param move The move being attempted
   * @return True if the piece is moving in a valid direction as specified above
   */
  public boolean moveDirectionValid(Piece piece, Move move) {
    boolean validColumnMove = Math.abs(move.getStart().getCell() - move.getEnd().getCell()) == 1;
    if(!validColumnMove)
      return false;
    boolean validRowMove = false;
    if(piece.getType() == PieceType.KING)
      validRowMove = Math.abs(move.getStart().getRow() - move.getEnd().getRow()) == 1;
    else {
      int validDirection = piece.getColor() == PieceColor.RED ? -1 : 1;
      validRowMove = move.getStart().getRow() - move.getEnd().getRow() == validDirection;
    }
    return validRowMove;
  }

  /**
   * Checks to make sure the end position of the move is empty ie piece is null in a space
   * @param game The game state
   * @param move The attempted move
   * @return True if the space at the move's end is null
   */
  public boolean spaceIsEmpty(Game game, Move move) {
    return game.getSpace(move.getEnd()).getPiece() == null;
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
    if(!correctPieceUsed(piece.getColor()))
      return TurnResponse.INCORRECT_PIECE_USED;
    // Check that the move is to a black tile
    if(!moveToBlack(game, move))
      return TurnResponse.MOVE_TO_WHITE_SPACE;
    // Check that the position from the last move is possible
    if(!movePathPossible(move))
      return TurnResponse.INVALID_MULTI_MOVE;
    // Check direction of the move
    if(!moveDirectionValid(piece, move))
      return TurnResponse.INVALID_DIRECTION;
    // Check that a jump is not possible and the user is making a simple move
    // Check that the current space is not occupied
    if(!spaceIsEmpty(game, move))
      return TurnResponse.SPACE_TAKEN;
    moves.add(move);
    return TurnResponse.VALID_TURN;
  }

  /**
   * Handles the logic of backing up a single move. The backup removes
   * the last move added to the list of moves
   * @precondition Moves has at least a single move
   */
  public void backupMove() {
    moves.remove(moves.size() - 1);
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

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

public class AITurnUtil {

  /**
   * Get the first simple move that a given player can make
   * @param game The game to the get the move from
   * @param color The color of the player making the turn
   * @return A list of possible simple moves that a player can make
   */
  public List<Move> getSimpleMoves(Game game, PieceColor color) {
    List<Position> piecePositions = game.getPiecePositions(color);

    List<Move> possibleMoves = new LinkedList<>();
    Turn utilTurn = new Turn(color);

    // Go through all of the possible starting positions
    for(Position startPosition: piecePositions) {
      List<Position> endPositions = utilTurn.getPossibleSimpleMoves(startPosition);
      // Go through all of the end positions
      for(Position endPosition: endPositions) {
        Move testMove = new Move(startPosition, endPosition);
        Turn testTurn = new Turn(color);
        // If a valid simple move is found, then return it
        if(testTurn.addMove(game, testMove) == TurnResponse.VALID_TURN)
          possibleMoves.add(testMove);
      }
    }

    return possibleMoves;
  }

  /**
   * Get the first single jump that a given player can make
   * @param game The game to get the move from
   * @param color The color of the player making the turn
   * @return A list of possible jumps the player can make
   */
  public List<Move> getSingleJump(Game game, PieceColor color) {
    List<Position> piecePositions = game.getPiecePositions(color);

    List<Move> possibleMoves = new LinkedList<>();
    Turn utilTurn = new Turn(color);

    // Go through all of the starting positions
    for(Position startPosition: piecePositions) {
      Piece piece = utilTurn.getPiece(game, startPosition);
      List<Position> endPositions = utilTurn.getPossibleJumpPositions(startPosition, piece);
      for(Position endPosition: endPositions) {
        Move testMove = new Move(startPosition, endPosition);
        Turn testTurn = new Turn(color);
        if(testTurn.addMove(game, testMove) == TurnResponse.VALID_TURN)
          possibleMoves.add(testMove);
      }
    }

    return possibleMoves;
  }

  /**
   * Get the first multi-jump that a given player can make
   * @param game The game to get the move from
   * @param color The color of the player making the turn
   * @return A move that is a multi-jump, null if there are no multi-jumps
   */
  public Move getMultiJump(Game game, PieceColor color) {
    return null;
  }
}

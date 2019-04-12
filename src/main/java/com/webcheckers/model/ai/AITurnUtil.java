package com.webcheckers.model.ai;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Move;
import com.webcheckers.model.checkers.Piece;
import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Position;
import com.webcheckers.model.checkers.Turn;
import com.webcheckers.model.checkers.Turn.TurnResponse;
import java.util.List;

public class AITurnUtil {

  /**
   * Get the first simple move that a given player can make
   * @param game The game to the get the move from
   * @param color The color of the player making the turn
   * @return A move that is the simple move, null if there are no simple moves
   */
  public Move getSimpleMove(Game game, PieceColor color) {
    Turn testTurn = new Turn(color);
    List<Position> piecePositions = game.getPiecePositions(color);

    // Go through all of the possible starting positions
    for(Position startPosition: piecePositions) {
      Piece currentPiece = testTurn.getPiece(game, startPosition);
      List<Position> endPositions = testTurn.getPossibleSimpleMoves(startPosition);
      // Go through all of the end positions
      for(Position endPosition: endPositions) {
        Move testMove = new Move(startPosition, endPosition);
        // If a valid simple move is found, then return it
        if(testTurn.addMove(game, testMove) == TurnResponse.VALID_TURN)
          return testMove;
      }
    }

    return null;
  }

  /**
   * Get the first single jump that a given player can make
   * @param game The game to get the move from
   * @param color The color of the player making the turn
   * @return A move that is a single jump, null if there are no single jumps
   */
  public Move getSingleJump(Game game, PieceColor color) {
    return null;
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

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

  private Game game;
  private PieceColor color;

  private List<Turn> possibleSimpleTurns;
  private List<Turn> possibleSingleJumps;
  private List<Turn> possibleMultiJumps;

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

  public List<Turn> getPossibleSimpleTurn() {
    return possibleSimpleTurns;
  }

  public List<Turn> getPossibleSingleJumps() {
    return possibleSingleJumps;
  }

  public List<Turn> getPossibleMultiJumps() {
    return possibleMultiJumps;
  }
}

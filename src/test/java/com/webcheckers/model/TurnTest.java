package com.webcheckers.model;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Move;
import com.webcheckers.model.checkers.Piece;
import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Piece.PieceType;
import com.webcheckers.model.checkers.Position;
import com.webcheckers.model.checkers.Space;
import com.webcheckers.model.checkers.Space.SpaceType;
import com.webcheckers.model.checkers.Turn;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Handles testing the Turn class in the model tier. This includes testing all of the
 * turn validation cases and making sure the correct rule broken is returned
 */
@Tag("Model-tier")
public class TurnTest {
  /**
   * Handles testing the helper method that gets the piece at the given location
   * in the game.
   */
  @Test
  public void testGetPiece() {
    Piece testPiece = new Piece(PieceType.SINGLE, PieceColor.WHITE);
    Space testSpace = mock(Space.class);
    when(testSpace.getPiece()).thenReturn(testPiece);
    Game game = mock(Game.class);
    when(game.getSpace(any())).thenReturn(testSpace);

    Turn CuT = new Turn(PieceColor.RED);
    Piece returnedPiece = CuT.getPiece(game, null);
    assertNotNull(returnedPiece);
    assertEquals(testPiece, returnedPiece);
  }

  /**
   * Handles testing the method which checks to make sure that the color making the
   * move is the same color as the piece being moves
   */
  @Test
  public void testCorrectPieceUsed() {
    // Check for red player attempting to move white piece
    Turn CuT = new Turn(PieceColor.RED);
    assertFalse(CuT.correctPieceUsed(PieceColor.WHITE));

    // Check for white player attempting to move red piece
    CuT = new Turn(PieceColor.WHITE);
    assertFalse(CuT.correctPieceUsed(PieceColor.RED));

    // Check for red player attempting to move red piece
    CuT = new Turn(PieceColor.RED);
    assertTrue(CuT.correctPieceUsed(PieceColor.RED));

    // Check for white player attempting to move white piece
    CuT = new Turn(PieceColor.WHITE);
    assertTrue(CuT.correctPieceUsed(PieceColor.WHITE));
  }

  /**
   * Checks to make sure that a move to a white space returns false
   * and a move to a black space returns true
   */
  @Test
  public void testMoveToBlack() {
    Game game = mock(Game.class);
    Move move = mock(Move.class);
    when(move.getEnd()).thenReturn(null);
    Space blackSpace = new Space(0, null, SpaceType.BLACK);
    Space whiteSpace = new Space(0, null, SpaceType.WHITE);
    Turn CuT = new Turn(PieceColor.RED);

    // Test when space is black
    when(game.getSpace(any())).thenReturn(blackSpace);
    assertTrue(CuT.moveToBlack(game, move));

    // Test when space is white
    when(game.getSpace(any())).thenReturn(whiteSpace);
    assertFalse(CuT.moveToBlack(game, move));
  }

  /**
   * Makes sure that the new move is possible based on the previous move
   */
  @Test
  public void testMovePathPossible() {
    Turn CuT = new Turn(PieceColor.RED);
    Game game = mock(Game.class);
    Space space = mock(Space.class);
    Piece piece = mock(Piece.class);
    Move firstMove = mock(Move.class);
    Position firstMoveEndPos = mock(Position.class);

    when(firstMoveEndPos.getRow()).thenReturn(3);
    when(firstMoveEndPos.getCell()).thenReturn(3);
    when(firstMove.getEnd()).thenReturn(firstMoveEndPos);

    // First move is always possible
    assertTrue(CuT.movePathPossible(new Move(null, null)));

    // Add a valid move to the list
    CuT.getMoves().add(firstMove);

    // Test adding an invalid move
    Move secondMove = mock(Move.class);
    when(secondMove.getStart()).thenReturn(null);
    assertFalse(CuT.movePathPossible(secondMove));

    // Test adding a valid move
    when(secondMove.getStart()).thenReturn(firstMoveEndPos);
    assertTrue(CuT.movePathPossible(secondMove));
  }

  /**
   * Handles testing the ability to check if a move if diagonal or not
   */
  @Test
  public void testMoveDiagonal() {
    Turn CuT = new Turn(null);
    Position startPos = mock(Position.class);
    Position endPos = mock(Position.class);
    Move move = mock(Move.class);

    when(startPos.getRow()).thenReturn(0);
    when(startPos.getCell()).thenReturn(0);

    when(move.getStart()).thenReturn(startPos);
    when(move.getEnd()).thenReturn(endPos);

    // Not diagonal
    when(endPos.getRow()).thenReturn(4);
    when(endPos.getCell()).thenReturn(5);
    assertFalse(CuT.moveIsDiagonal(move));

    // Diagonal
    when(endPos.getRow()).thenReturn(1);
    when(endPos.getCell()).thenReturn(1);
    assertTrue(CuT.moveIsDiagonal(move));
  }

  /**
   * Test validation for pieces moving. This tests a king piece, white piece, and red piece
   * in a number of different directions to make sure each is valid at the correct time
   */
  @Test
  public void testMoveDirectionValid() {
    Position startPos = mock(Position.class);
    when(startPos.getRow()).thenReturn(3);
    when(startPos.getCell()).thenReturn(3);
    Position endPos = mock(Position.class);
    Move move = mock(Move.class);
    when(move.getStart()).thenReturn(startPos);
    when(move.getEnd()).thenReturn(endPos);

    Turn CuT = new Turn(null);
    Piece kingPiece = new Piece(PieceType.KING, PieceColor.WHITE);
    Piece whitePiece = new Piece(PieceType.SINGLE, PieceColor.WHITE);
    Piece redPiece = new Piece(PieceType.SINGLE, PieceColor.RED);

    // Moving down and to the left (king and white piece only)
    when(endPos.getRow()).thenReturn(2);
    when(endPos.getCell()).thenReturn(2);
    assertTrue(CuT.moveDirectionValid(kingPiece, move));
    assertTrue(CuT.moveDirectionValid(whitePiece, move));
    assertFalse(CuT.moveDirectionValid(redPiece, move));

    // Move down and to the right (king and white piece only)
    when(endPos.getCell()).thenReturn(4);
    assertTrue(CuT.moveDirectionValid(kingPiece, move));
    assertTrue(CuT.moveDirectionValid(whitePiece, move));
    assertFalse(CuT.moveDirectionValid(redPiece, move));

    // Move up and to the right (king and red piece only)
    when(endPos.getRow()).thenReturn(4);
    assertTrue(CuT.moveDirectionValid(kingPiece, move));
    assertFalse(CuT.moveDirectionValid(whitePiece, move));
    assertTrue(CuT.moveDirectionValid(redPiece, move));

    // Move up and to the left (king and red piece only)
    when(endPos.getCell()).thenReturn(2);
    assertTrue(CuT.moveDirectionValid(kingPiece, move));
    assertFalse(CuT.moveDirectionValid(whitePiece, move));
    assertTrue(CuT.moveDirectionValid(redPiece, move));
  }

  /**
   * Test to make sure that a space can correctly be identified as empty or not
   */
  @Test
  public void testSpaceIsEmpty() {
    Turn CuT = new Turn(null);
    Game game = mock(Game.class);
    Space space = mock(Space.class);
    Move move = mock(Move.class);
    when(game.getSpace(any())).thenReturn(space);
    when(move.getEnd()).thenReturn(null);

    // Space is full
    when(space.getPiece()).thenReturn(new Piece(PieceType.SINGLE, PieceColor.RED));
    assertFalse(CuT.spaceIsEmpty(game, move));

    // Space is empty
    when(space.getPiece()).thenReturn(null);
    assertTrue(CuT.spaceIsEmpty(game, move));
  }

  /**
   * Test to make sure a move is valid only if the move is a move to a single
   * cell away
   */
  @Test
  public void testSimpleMove() {
    Turn CuT = new Turn(null);
    Move move = mock(Move.class);
    Position startPos = mock(Position.class);
    Position endPos = mock(Position.class);

    when(startPos.getRow()).thenReturn(0);
    when(startPos.getCell()).thenReturn(0);
    when(move.getStart()).thenReturn(startPos);
    when(move.getEnd()).thenReturn(endPos);

    // Invalid simple move
    when(endPos.getRow()).thenReturn(0);
    when(endPos.getCell()).thenReturn(10);
    assertFalse(CuT.isValidSimpleMove(move));

    // Valid simple move
    when(endPos.getRow()).thenReturn(1);
    when(endPos.getCell()).thenReturn(1);
    assertTrue(CuT.isValidSimpleMove(move));
  }

  /**
   * Test for valid jumps. Check to make sure the jump is a valid num
   * spaces away, is jumping over a piece, and is jumping over the correct
   * color piece
   */
  @Test
  public void testValidJump() {
    Turn CuT = new Turn(PieceColor.RED);
    Game game = mock(Game.class);
    Space space = mock(Space.class);
    Piece piece = mock(Piece.class);
    Move move = mock(Move.class);
    Position startPos = mock(Position.class);
    Position endPos = mock(Position.class);
    when(move.getStart()).thenReturn(startPos);
    when(move.getEnd()).thenReturn(endPos);
    when(game.getSpace(any())).thenReturn(space);

    // Test move that is just a simple jump
    when(startPos.getRow()).thenReturn(0);
    when(endPos.getRow()).thenReturn(1);
    assertFalse(CuT.isValidJumpMove(move, game));

    // Test move that does not jump over a piece
    when(space.getPiece()).thenReturn(null);
    when(startPos.getCell()).thenReturn(0);
    when(endPos.getRow()).thenReturn(2);
    when(endPos.getCell()).thenReturn(2);
    assertFalse(CuT.isValidJumpMove(move, game));

    // Test jump over wrong color
    when(space.getPiece()).thenReturn(piece);
    when(piece.getColor()).thenReturn(PieceColor.RED);
    assertFalse(CuT.isValidJumpMove(move, game));

    // Test valid jump
    when(piece.getColor()).thenReturn(PieceColor.WHITE);
    assertTrue(CuT.isValidJumpMove(move, game));
  }

  /**
   * Handles testing of a mulit-jump. Checks to make sure the move path
   * follows the rules of checkers
   */
  @Test
  public void testValidMultiJump() {
    Turn CuT = new Turn(PieceColor.RED);
    Game game = mock(Game.class);
    Move move = mock(Move.class);
    Position startPosFirst = mock(Position.class);
    Position endPosFirst = mock(Position.class);
    Position startPosSecond = mock(Position.class);
    Position endPosSecond = mock(Position.class);

    when(startPosFirst.getRow()).thenReturn(0);
    when(startPosFirst.getCell()).thenReturn(0);
    when(endPosFirst.getCell()).thenReturn(1);
    when(endPosFirst.getCell()).thenReturn(1);
    when(move.getStart()).thenReturn(startPosFirst);
    when(move.getEnd()).thenReturn(endPosFirst);


    // No previous moves
    assertTrue(CuT.isValidMultiMove(move, game));

    // Path not possible
    CuT.getMoves().add(new Move(startPosFirst, endPosFirst));

    when(startPosSecond.getRow()).thenReturn(2);
    when(startPosFirst.getCell()).thenReturn(2);
    assertFalse(CuT.isValidMultiMove(new Move(startPosSecond, endPosSecond), game));

    // Past move was not a jump
    assertFalse(CuT.isValidMultiMove(new Move(endPosFirst, endPosSecond), game));
  }

  /**
   * Tests to make sure the check for turning a piece into a king is working
   */
  @Test
  public void testMakeKing() {
    Turn CuT = new Turn(null);
    // Invalid positions for a red piece
    assertFalse(CuT.isKingRow(PieceColor.RED, 0));
    assertFalse(CuT.isKingRow(PieceColor.RED, 5));

    // Valid position for red piece
    assertTrue(CuT.isKingRow(PieceColor.RED, 7));

    // Invalid positions for a white piece
    assertFalse(CuT.isKingRow(PieceColor.WHITE, 7));
    assertFalse(CuT.isKingRow(PieceColor.WHITE, 3));

    // Valid position for white piece
    assertTrue(CuT.isKingRow(PieceColor.WHITE, 0));
  }
}

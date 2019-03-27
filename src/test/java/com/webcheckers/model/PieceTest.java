package com.webcheckers.model;

import com.webcheckers.model.checkers.Piece;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class handles testing for the Piece java class.
 * It tests the functionality of each method available in Piece.
 *
 * @author Andrew Bado
 */
@Tag("Model-tier")
public class PieceTest {

  // tests that items can be successfully constructed
  @Test
  public void construction(){
    Piece p = new Piece(Piece.PieceType.SINGLE, Piece.PieceColor.RED);
    assertNotNull(p);
  }

  // tests that a piece's type can be retrieved
  @Test
  public void test_get_type(){
    // test type single
    Piece p = new Piece(Piece.PieceType.SINGLE, Piece.PieceColor.RED);
    assertEquals(Piece.PieceType.SINGLE, p.getType());

    // test type king
    p = new Piece(Piece.PieceType.KING, Piece.PieceColor.WHITE);
    assertEquals(Piece.PieceType.KING, p.getType());
  }

  // tests that a piece's color can be retrieved
  @Test
  public void test_get_color(){
    // test type red
    Piece p = new Piece(Piece.PieceType.SINGLE, Piece.PieceColor.RED);
    assertEquals(Piece.PieceColor.RED, p.getColor());

    // test type white
    p = new Piece(Piece.PieceType.KING, Piece.PieceColor.WHITE);
    assertEquals(Piece.PieceColor.WHITE, p.getColor());
  }
}

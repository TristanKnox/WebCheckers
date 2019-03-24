package com.webcheckers.model;

import com.webcheckers.model.checkers.Piece;
import com.webcheckers.model.checkers.Row;
import com.webcheckers.model.checkers.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Tag("Model-tier")
public class RowTest {

    private final int INDEX = 5;
    private final List<Space> spaces = new ArrayList<Space>(8);



    @BeforeEach
    public void setup(){
        for(Space space : spaces){
            space = mock(Space.class);
        }
    }

    /**
     * Test the row constructor when just passing the index number
     */
    @Test
    public void indexConstructor(){
        Row row = new Row(INDEX);
        assertEquals(INDEX,row.getIndex(),"The index of row was expected to be " + INDEX + " but was " + row.getIndex());
        assertNotNull(row.getSpaces());
    }

    /**
     * Test the row constructor when passing both spaces and the index
     */
    @Test
    public void indexAndSpacesConstructor(){
        Row row = new Row(spaces, INDEX);
        assertEquals(INDEX,row.getIndex(),"The index of row was expected to be " + INDEX + " but was " + row.getIndex());
        assertNotNull(row.getSpaces());
        assertEquals(spaces,row.getSpaces());
    }

    @Test
    public void spaceInitilazation(){
        Row row;
        for(int index = 0 ;  index < 8 ; index++){  //Iterate through the posible index's of each row
            row = new Row(index);                   //Create a row for the given index
            checkRowSpaces(row);
        }
    }

    private void checkRowSpaces(Row row){
        int rowIndex = row.getIndex();
        for(int spaceIndex = 0; spaceIndex < row.getSpaces().size(); spaceIndex++){ //Itterate over each space in that row
            Space space = row.getSpaces().get(spaceIndex);
            if(space.getType() == Space.SpaceType.WHITE){
                assertNull(space.getPiece());
            }
            else {
                Piece piece = space.getPiece();

                if (rowIndex < 3) {       //Bottom 3 rows
                    assertNotNull(piece);
                    assertEquals(Piece.PieceColor.RED,piece.getColor());
                } else if (rowIndex > 4) {  //Top 3 rows
                    assertNotNull(piece);
                    assertEquals(Piece.PieceColor.WHITE,piece.getColor());
                } else {                   //Middle 2 rows
                    assertNull(piece);
                }
            }
        }
    }

}

package com.webcheckers.model;

import com.webcheckers.model.checkers.Row;
import com.webcheckers.model.checkers.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Test
    public void indexConstructor(){
        Row row = new Row(INDEX);
        assertEquals(INDEX,row.getIndex(),"The index of row was expected to be " + INDEX + " but was " + row.getIndex());
        assertNotNull(row.getSpaces());
    }

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
        for(int index = 0 ;  index < 8 ; index++){
            row = new Row(index);
            for(Space space: row.getSpaces()){
                if(space.)
            }
        }
    }

}

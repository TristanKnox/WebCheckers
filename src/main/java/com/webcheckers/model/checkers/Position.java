package com.webcheckers.model.checkers;

public class Position {

    private int row;
    private int cell;

    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    public int getRow(){ return row; }
    public int getCell(){ return cell; }

    public String toString(){
        return "row: " + row + ", cell: " + cell;
    }
}

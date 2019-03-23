package com.webcheckers.model.checkers;

public class Position {

    private int row;
    private int cell;

    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj instanceof Position) {
            Position other = (Position) obj;
            return this.row == other.row && this.cell == other.cell;
        }
        return false;
    }

    public int getRow(){ return row; }
    public int getCell(){ return cell; }

    public String toString(){
        return "row: " + row + ", cell: " + cell;
    }
}

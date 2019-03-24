package com.webcheckers.model.checkers;

public class Move {

    private Position start;
    private Position end;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }


    public Position getStart(){ return start; }
    public Position getEnd(){ return end; }

    public String toString(){
        return "Start: " + start.toString() + "\nEnd: " + end.toString();
    }
}

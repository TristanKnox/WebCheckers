package com.webcheckers.model.checkers;

public class Move {

    private Position start;
    private Position end;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj instanceof Move) {
            Move other = (Move) obj;
            return this.start.equals(other.start) && this.end.equals(other.end);
        }
        return false;
    }

    public Position getStart(){ return start; }
    public Position getEnd(){ return end; }

    public String toString(){
        return "Start: " + start.toString() + "\nEnd: " + end.toString();
    }
}

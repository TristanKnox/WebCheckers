package com.webcheckers.model;

import com.webcheckers.model.checkers.Row;

import java.util.List;

public class BoardState {

    private List<Row> rows;
    private Player activePlayer;

    /**
     * Constructor
     * @param rows - the rows of the board beeing stored
     * @param activePlayer - the active player of the board state being stored
     */
    public BoardState(List<Row> rows, Player activePlayer){
        this.rows = rows;
        this.activePlayer = activePlayer;
    }

    /**
     * Gets the rows
     * @return - rows
     */
    public List<Row> getRows(){ return rows; }

    /**
     * Gets the activePlayer
     * @return - activePlayer
     */
    public Player getActivePlayer(){ return activePlayer; }
}

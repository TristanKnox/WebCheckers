package com.webcheckers.model;

import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece;
import com.webcheckers.model.checkers.Row;

import java.util.List;

public class BoardState {

    private List<Row> rows;
    private Piece.PieceColor activePlayer;

    /**
     * Constructor
     * @param game - the game with the state to store
     */
    public BoardState(Game game){
        this.rows = game.getCopyRows();
        this.activePlayer = game.getActiveColor();
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
    public Piece.PieceColor getActivePlayer(){ return activePlayer; }
}

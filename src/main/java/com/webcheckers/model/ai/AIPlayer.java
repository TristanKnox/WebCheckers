package com.webcheckers.model.ai;

import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece.PieceColor;

/**
 * The AIPlayer represents the AI that humans can play against. The AIPlayer
 * can check for when it is its turn then make a move depending on its difficulty
 * level.
 */
public class AIPlayer extends Player {

    /**
     * Currently supports two different difficulties, Easy and Medium
     */
    public enum Difficulty {
        EASY,
        HARD
    }

    /** Since the AI never starts a game, it is always white */
    private static PieceColor color = PieceColor.WHITE;
    /** Represents the difficulty of this AI */
    private Difficulty difficulty;

    /**
     * Create an AI player with a given name and difficulty level
     * @param name The name of the AI player
     * @param difficulty The difficulty level of the AI
     */
    public AIPlayer(String name, Difficulty difficulty) {
        super(name);
        this.difficulty = difficulty;
    }

    /**
     * The call to make a move against the passed in turn
     * @param game The game to make the move on
     */
    public void makeMove(Game game) {

    }
}

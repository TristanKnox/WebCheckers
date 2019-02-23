package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;

import java.util.HashMap;

public class GameCenter {

    private HashMap<Player, Game> activeGames; //player name to Game obj
    private HashMap<Player, Player> playersInMatch; //player to player for matches

    /**
     * creates the game-center object for the server application.
     */
    public GameCenter(){
        activeGames= new HashMap<>();
        playersInMatch = new HashMap<>();
    }

    /**
     * creates the game that the players will play on.
     * @param playerOne the first player
     * @param playerTwo the second player
     * @return the game that is created
     */
    public Game spawnGame(Player playerOne, Player playerTwo){
        Game game = new Game(playerOne,playerTwo);
        addPlayersToMatch(playerOne,playerTwo);
        addPlayerToGame(playerOne,game);
        addPlayerToGame(playerTwo,game);
        return game;
    }

    /**
     * Adds players to game to keep track of them.
     * adds twice to find them quicker.
     * @param p1 player one
     * @param p2 player two
     */
    public void addPlayersToMatch(Player p1, Player p2){
        playersInMatch.put(p1,p2);
        playersInMatch.put(p2,p1);
    }

    /**
     * Adds a player to the game listing.
     * @param player the player
     * @param game the game instance.
     */
    public void addPlayerToGame(Player player, Game game){
        activeGames.put(player, game);
    }

    /**
     * gets the other player of the game.
     * @param player player whose opponent wants to be found.
     * @return the players opponent, if it exists.
     */
    public Player getOtherPlayer(Player player) {
       return  playersInMatch.containsKey(player) ? playersInMatch.get(player) : null;
    }

    /**
     * gets the game that a player is playing on currently.
     * @param player the player
     * @return the game
     */
    public Game getGame(Player player){
        return activeGames.get(player);
    }

}

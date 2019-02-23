package com.webcheckers.appl;

import com.webcheckers.model.checkers.Game;

import java.util.HashMap;

public class GameCenter {

    //todo spawn game and what not.  Hold list of players like hashmap of <String, Games> and active games
    //todo spawn a game based off of two player objs. Return Game.
    private HashMap<String, Game> activeGames; //player name to Game obj
    private HashMap<Player, Player> playersInMatch; //player to player for matches

    /**
     * creates the gamecenter object for the server application.
     */
    public GameCenter(){
        activeGames= new HashMap<String, Game>();
        playersInMatch = new HashMap<>();
    }

    /**
     * creates the game that the players will play on.
     * @param playerOne
     * @param playerTwo
     * @return
     */
    public Game spawnGame(Player playerOne, Player playerTwo){
        //todo fill in with the game constructor. and return the following game
        return null;
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
        activeGames.put(player.getPlayer(), game);
    }


    /**
     * gets the other player of the game.
     * @param player player whose opponent wants to be found.
     * @return the players opponent, if it exists.
     */
    public Player getOtherPlayer(Player player) {
       return  playersInMatch.containsKey(player) ? playersInMatch.get(player) : null;
    }
    public Game getGaame(String username){
        return activeGames.get(username);
    }
}

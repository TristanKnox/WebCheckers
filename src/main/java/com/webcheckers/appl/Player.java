package com.webcheckers.appl;

/**
 *The Player class stores the username of the player
 */
//todo move it into a Model Package.
public class Player {
    private String player;

    /**
     * The constructor for the player
     * @param username the username that will be shown in the player lobby
     */
    public Player(String username){
        player = username;
    }

    /**
     * an accessor for the players username
     * @return the player's username
     */
    public String getPlayer(){
        return player;
    }




}

package com.webcheckers.appl;

import java.util.*;

/**
 * This Class holds all of the current userers loged into the WebCheckers App
 */
public class PlayerLoby {

    private Map<String, PlayerServices> curentUsers;


    //Constructor
    public PlayerLoby(){
        curentUsers = new HashMap<>();
    }

    /**
     * Adds a new user to currentUsers providing the username dose not already exist
     * @param username is the username that the user is attempting to login with
     * @return true if user was successfully added
     * @return false if username was already in use and the user was not added
     */
    public boolean addPlayer(String username){
        if (curentUsers.containsKey(username))
            return false;   //User name already in use
        curentUsers.put(username,new PlayerServices());     //username added as Key and a new PlayerService created as Value
        return true;
    }

    /**
     * Creates a list of usernames repersenting all curently loged in users
     * @return the list of usernames
     */
    public List<String> getAllUserNames(){
        Set<String> userSet = curentUsers.keySet();
        List<String> users = new ArrayList<>(userSet);
        return users;
    }

    /**
     * This will start a game for the two given players
     * @param player1 the username of player1
     * @param player2 the username of player2
     */
    public void startGame(String player1, String player2){
        //TODO
    }

    /**
     * This will remove the given user from currentUsers
     * @param username the user name
     * @return true if successful
     * @return false if unsuccessful
     */
    public boolean logout(String username){
        //TODO
        return false;
    }


}

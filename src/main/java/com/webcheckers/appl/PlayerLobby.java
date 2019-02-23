package com.webcheckers.appl;

import com.webcheckers.model.Player;
import java.util.*;


/**
 * This Class holds all of the current users logged into the WebCheckers App
 */
public class PlayerLobby {

    private Map<String, Player> currentUsers;


    //Constructor
    public PlayerLobby(){
        currentUsers = new HashMap<>();
    }

    /**
     * Adds a new user to currentUsers providing the username dose not already exist
     * @param username is the username that the user is attempting to login with
     * @return true if user was successfully added
     * @return false if username was already in use and the user was not added
     */
    public synchronized boolean addPlayer(String username){
        if (currentUsers.containsKey(username))
            return false;   //User name already in use

        currentUsers.put(username,new Player(username));     //username added as Key and a new PlayerService created as Value
        return true;
    }

    /**
     * Checks to see if a username is valid or not
     * @param username in question
     * @return true if username is valid
     * @return false if username is invalid
     */
    public boolean isValidUsername(String username){
        //TODO add Validation
        return true;
    }

    /**
     * Creates a list of usernames repersenting all curently loged in users
     * @return the list of usernames
     */
    public List<String> getAllUserNames(){
        Set<String> userSet = currentUsers.keySet();
        List<String> users = new ArrayList<>(userSet);
        return users;
    }

    /**
     * This will start a game for the two given players
     * @param player1 the username of player1
     * @param player2 the username of player2
     */
    public void startGame(Player player1, Player player2){
        //TODO
    }

    /**
     * This will remove the given user from currentUsers
     * @param username the user name
     * @return true if successful
     * @return false if unsuccessful
     */
    public synchronized boolean logout(String username){
        if(currentUsers.containsKey(username)){
            currentUsers.remove(username);
            return true;
        }
        else
            return false;
    }


}

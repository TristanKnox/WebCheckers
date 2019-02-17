package com.webcheckers.appl;

import java.util.HashMap;
import java.util.Map;

/**
 * This Class holds all of the current userers loged into the WebCheckers App
 */
public class PlayerLoby {

    Map<String, PlayerServices> curentUsers;


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


}

package com.webcheckers.appl;

import com.webcheckers.model.Player;
import java.util.*;


/**
 * This Class holds all of the current users logged into the WebCheckers App
 */
public class PlayerLobby {

    private Map<String, Player> currentUsers;
    private Map<String, Player> inGameUsers;
    private Map<String, Player> avalUsers;


    //Constructor
    public PlayerLobby() {
        currentUsers = new HashMap<>();
        inGameUsers = new HashMap<>();
        avalUsers = new HashMap<>();
    }

    /**
     * Adds a new user to currentUsers providing the username dose not already exist
     *
     * @param username is the username that the user is attempting to login with
     * @return false if username was already in use and the user was not added
     */
    public synchronized Player addPlayer(String username) {
        if (isValidUsername(username)) {
            Player temp = new Player(username);
            currentUsers.put(username, temp);
            avalUsers.put(username, temp);
            return temp;
        }
        return null;

    }

    /**
     * Checks to see if a username is valid or not
     *
     * @param username in question
     * @return false if username is invalid
     */
    public boolean isValidUsername(String username) {
        if (!currentUsers.containsKey(username)) {
            if (username.length() == 0) {
                return false;
            } else if (!Character.isLetter(username.charAt(0))) {
                return false;
            } else {
                for (int i = 0; i < username.length(); i++) {
                    char c = username.charAt(i);
                    int ascii = (int) username.charAt(i);
                    if (c == ' ') {
                        continue;
                    } else if ((48 <= ascii && ascii <= 57) || (65 <= ascii && ascii <= 90) || (97 <= ascii && ascii <= 122)) {
                        continue;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Creates a list of usernames repersenting all curently logged in users
     * @return the list of usernames
     *//*
    public List<String> getAllUserNames(){
        Set<String> userSet = currentUsers.keySet();
        List<String> users = new ArrayList<>(userSet);
        return users;
    }*/

    /**
     * Creates a list of usernames repersenting all curently loged in users
     *
     * @return the list of usernames
     */
    public List<Player> getAllAvalPlayers() {
        Collection<Player> players = avalUsers.values();
        List<Player> users = new ArrayList<>(players);
        return users;
    }

    /**
     * This will remove the given user from currentUsers
     *
     * @param username the user name
     * @return false if unsuccessful
     */
    public synchronized boolean logout(String username) {
        if (currentUsers.containsKey(username)) {
            currentUsers.remove(username);
            avalUsers.remove(username);
            return true;
        } else
            return false;
    }

    public Player getPlayer(String username) {
        return (currentUsers.get(username));
    }

    public void removePlayer(String username) {
        avalUsers.remove(username);
    }

    public Player getPlayerForGame(String username) {
        Player ret = getPlayer(username);
        removePlayer(username);
        return ret;
    }
    
    public boolean isInGame(Player player){
        return(avalUsers.containsValue(player));
    }
}


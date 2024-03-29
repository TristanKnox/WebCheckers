package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.model.ai.AIPlayer;
import com.webcheckers.model.ai.AIPlayer.Difficulty;
import java.util.*;


/**
 * This Class holds all of the current users logged into the WebCheckers App
 * @Authors Jacob Jirinec
 *
 */
public class PlayerLobby {
  /** Represents the easy AI in the game */
  public static final AIPlayer easyAI = new AIPlayer("Easy AI", Difficulty.EASY);
  /** Represents the hard AI in the game */
  public static final AIPlayer hardAI = new AIPlayer("Hard AI", Difficulty.HARD);

  private Map<String, Player> currentUsers;
  private Map<String, Player> avalUsers;

    //Constructor
  public PlayerLobby() {
    currentUsers = new HashMap<>();
    avalUsers = new HashMap<>();

    avalUsers.put(easyAI.getName(), easyAI);
    avalUsers.put(hardAI.getName(), hardAI);
    currentUsers.put(easyAI.getName(), easyAI);
    currentUsers.put(hardAI.getName(), hardAI);
  }

    /**
     * Adds a new user to currentUsers providing the username dose not already exist
     *
     * @param username is the username that the user is attempting to login with
     * @return false if username was already in use and the user was not added
     */
  public synchronized Outcome addPlayer(String username) {
    Outcome outcome = isValidUsername(username);
    if (outcome == Outcome.SUCCESS) {
      Player temp = new Player(username);
      currentUsers.put(username, temp);
      avalUsers.put(username, temp);
    }
    return outcome;
  }

  /**
   * Checks to see if a username is valid or not
   *
   * @param username in question
   * @return false if username is invalid
   */
  public Outcome isValidUsername(String username) {
    Outcome ret = Outcome.SUCCESS;
    if (!currentUsers.containsKey(username)) {
      if (username.length() == 0) {
        ret = Outcome.INVALID;
      }
      else if (!Character.isLetter(username.charAt(0))) {
        ret = Outcome.INVALID;
        }
      else {
        for (int i = 0; i < username.length(); i++) {
          char c = username.charAt(i);
          int ascii = (int) username.charAt(i);
          if (c == ' ') {
            continue;
          }
          else if ((48 <= ascii && ascii <= 57) || (65 <= ascii && ascii <= 90) || (97 <= ascii && ascii <= 122)) {
            continue;
            }
          else {
            ret = Outcome.INVALID;
          }
        }
      }
      return ret;
      }
    ret = Outcome.TAKEN;
    return ret;
  }


 /**
 * Creates a list of usernames repersenting all curently logged in users
 *
 * @return the list of usernames
 */
  public List<String> getAllUserNames() {
    Set<String> userSet = currentUsers.keySet();
    List<String> users = new ArrayList<>(userSet);
    return users;
  }

 /**
 * Creates a list of usernames representing all currently logged in users
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

  /**
 *
 * Gets the Player Object
 *
 * @param username username to get the object from
 * @return Player object associated with the username
 */
  public Player getPlayer(String username) {
    return (currentUsers.get(username));
  }


/**
 *
 *  Removes the player from the avalable user list
 *
 * @param username Username to remove from the list
 */
  public void removePlayer(String username) {
    Player player = avalUsers.get(username);
    if(!(player instanceof AIPlayer))
      avalUsers.remove(username);
  }

/**
 *
 * Gets the Player Object, and removes the player from avalble
 *
 * @param username user to perform the action on
 * @return Player object associated
 */
  public Player getPlayerForGame(String username) {
    Player ret = getPlayer(username);
    removePlayer(username);
    return ret;
  }
  
/**
 *
 * Checks to see if the given player is in game
 *
 * @param player Player to check
 * @return Boolean of the player status
 */
  public boolean isInGame(Player player) {
    return (!avalUsers.containsValue(player));
  }


  public void makeAvailable(Player player) {
    if(!(player instanceof AIPlayer))
      avalUsers.put(player.getName(),player);
  }
/**
 *
 * Gets the total number of users currently signed in
 *
 * @return the number of users
 */
  public int getNumberOfUsers(){
    return currentUsers.size();
  }

  /**
   * This method is used to sign out the player. For the sign out protocol.
   * @param player the player to be signed out
   */
  public void signOutPlayer(Player player){
    logout(player.getName());
  }
/**
 *
 * enum for Outcomes of Player sign in
 *
 */
  public enum Outcome {
    SUCCESS, TAKEN, INVALID
  }
}


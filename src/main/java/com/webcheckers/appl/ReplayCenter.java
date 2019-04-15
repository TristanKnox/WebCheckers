package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.util.ReplaySerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * The replayCenter class holds a list of saved games and the players that are watching a current
 * live game. with functionality between the game and everything else.
 * @Author Evan
 */
public class ReplayCenter {
  private HashMap<Player, Replay> activeReplays;
  private HashMap<Integer,Replay> archivedReplays;

  /**
   * makes the replay center
   */
  public ReplayCenter(){
    activeReplays = new HashMap<>();
    archivedReplays = new HashMap<>();
     Replay replay = ReplaySerializer.deserialize("replay.ser");
     if(replay != null) {
       System.out.println("Replay loaded");
       archivedReplays.put(replay.hashCode(), replay);
     }
  }

  /**
   * Converts a game to a replay and archives it
   * @param game - the game to be archived
   */
  public void storeReplay(Game game){
    Replay replay = new Replay(game);
    archivedReplays.put(replay.hashCode(),replay);
   // ReplaySerializer.serialize(replay);
  }

  /**
   * gets the game at given game id.
   * @param gameId the games Id from the position in the list
   * @return the game at the given index
   */
  public Replay getReplay(int gameId){
    return archivedReplays.get(gameId);
  }


  /**
   * gets the game to player mapping.
   * @param player the player in question
   * @return the game paired to the player.
   */
  public Replay getReplay(Player player){ return activeReplays.get(player);  }

  /**
   * returns true if there are replays available to watch
   * @return true if replays are available, false otherwise
   */
  public Boolean hasReplays(){
    return archivedReplays.size() > 0;
  }



  /**
   * Creates a new game to watch a replay of
   * @param watcher - the player that will be watching the replay
   * @param replayID - the id of the replay to watch
   */
  public void startReplay(Player watcher, int replayID){
    Replay replay = new Replay(getReplay(replayID));
    //Game game = new Game(replay.getPlayer1(), replay.getPlayer2(), replay.getNextBoardState());
    activeReplays.put(watcher,replay);
  }

  /**
   * Ends a replay when the exit button is pressed
   * @param player - the player with whom the replay is associated
   */
  public void endReplay(Player player){
    activeReplays.remove(player);
  }

  /**
   *
   * @param player
   */
  public void resetReplay(Player player){
    activeReplays.get(player).resetReplay();
  }

  /**
   * Will return a list of replays which users can select to watch
   *
   * @return a list of replays
   */
  public List<Replay> getReplayList(){
    return new ArrayList<>(archivedReplays.values());
  }

  /**
   * returns if there are no replays or not
   * @return the hashmap empty or not.
   */
  public boolean isEmpty(){
    return activeReplays.isEmpty();
  }
}

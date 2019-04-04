package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.model.checkers.Game;
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
  private HashMap<Player, Game> activeReplays;
  private HashMap<Integer,Replay> archivedReplays;

  /**
   * makes the replay center
   */
  public ReplayCenter(){
    activeReplays = new HashMap<>();
    archivedReplays = new HashMap<>();
  }

  /**
   * adds a replayer to a given game.
   * the game needs to be a copy of the game that it is in the list, so other players can look at
   * the same game from the beginning.
   * @param player the player session
   * @param game the game
   */
  public void addReplayer(Player player, Game game){
    activeReplays.put(player,game);
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
   * adds a game to the saved game list.
   * @param replay - the replay to archive
   */
  public void archiveReplay(Replay replay){ archivedReplays.put(replay.hashCode(),replay);  }

  /**
   * gets the game to player mapping.
   * @param player the player in question
   * @return the game paired to the player.
   */
  public Game getReplay(Player player){
    return activeReplays.get(player);
  }

  /**
   * Creates a new game to watch a replay of
   * @param watcher - the player that will be watching the replay
   * @param replayID - the id of the replay to watch
   */
  public void startReplay(Player watcher, int replayID){
    Replay replay = getReplay(replayID);
    Game game = new Game(replay.getPlayer1(),replay.getPlayer2(),replay.turns());
    activeReplays.put(watcher,game);
  }

}

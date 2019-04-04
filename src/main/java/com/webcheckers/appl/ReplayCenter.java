package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The replayCenter class holds a list of saved games and the players that are watching a current
 * live game. with functionality between the game and everything else.
 * @Author Evan
 */
public class ReplayCenter {
  private HashMap<Player, Game> watchedReplays;
  private List<Game> savedGames;

  /**
   * makes the replay center
   */
  public ReplayCenter(){
    watchedReplays = new HashMap<>();
    savedGames = new ArrayList<>();
  }

  /**
   * adds a replayer to a given game.
   * the game needs to be a copy of the game that it is in the list, so other players can look at
   * the same game from the beginning.
   * @param player the player session
   * @param game the game
   */
  public void addReplayer(Player player, Game game){
    watchedReplays.put(player,game);
  }

  /**
   * gets the game at given game id.
   * @param gameId the games Id from the position in the list
   * @return the game at the given index
   */
  public Game getGame(int gameId){
    return savedGames.get(gameId);
  }

  /**
   * adds a game to the saved game list.
   * @param game
   */
  public void addGame(Game game){
    savedGames.add(game);
  }

  /**
   * gets the game to player mapping.
   * @param player the player in question
   * @return the game paired to the player.
   */
  public Game getReplay(Player player){
    return watchedReplays.get(player);
  }

}

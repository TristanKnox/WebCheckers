package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.model.TestBoardBuilder;
import com.webcheckers.model.checkers.Game;

import java.util.HashMap;

/**
* The game center manipulates the holding of the matches and games for the web server
* @Autor Evan Nolan {@link https://github.com/emn3779}
*/
public class GameCenter {

  private HashMap<Player, Game> activeGames; //player name to Game obj
  private HashMap<Player, Player> playersInMatch; //player to player for matches

  /**
   * getter for the active games listing
   * @return the active games
   */
  public HashMap<Player, Game> getActiveGames() {
    return activeGames;
  }

  /**
   * getter for the match list
   * @return the player list of those who are in a match.
   */
  public HashMap<Player, Player> getPlayersInMatch() {
    return playersInMatch;
  }

  /**
  * creates the game-center object for the server application.
  */
  public GameCenter(){
    activeGames= new HashMap<>();
    playersInMatch = new HashMap<>();
  }

  /**
  * creates the game that the players will play on.
  * @param playerOne the first player
  * @param playerTwo the second player
  * @return the {@link Game} that is created
  */
  public Game spawnGame(Player playerOne, Player playerTwo){
    Game game = new Game(playerOne,playerTwo, TestBoardBuilder.BoardType.KING_MULTY_JUMP);//TODO change this for testing
    addPlayersToMatch(playerOne,playerTwo);
    addPlayerToGame(playerOne,game);
    addPlayerToGame(playerTwo,game);
    return game;
  }

  /**
  * Adds players to game to keep track of them.
  * adds twice to find them quicker.
  * @param p1 {@Link Player} one
  * @param p2 {@link Player} two
  */
  public void addPlayersToMatch(Player p1, Player p2){
    playersInMatch.put(p1,p2);
    playersInMatch.put(p2,p1);
  }

  /**
  * Adds a player to the game listing.
  * @param player the {@Link Player} object
  * @param game the {@Link Game} instance.
  */
  public void addPlayerToGame(Player player, Game game){
    activeGames.put(player, game);
  }

  /**
  * gets the other player of the game.
  * @param player {@link Player}  whose opponent wants to be found.
  * @return the players opponent, if it exists.
  */
  public Player getOtherPlayer(Player player) {
    return  playersInMatch.containsKey(player) ? playersInMatch.get(player) : null;
  }

  /**
  * gets the game that a player is playing on currently.
  * @param player the player
  * @return the {@link Game}
  */
  public Game getGame(Player player){
    return activeGames.get(player);
  }

  /**
   * removes the players from the match list
   * @param playerOne first player
   * @param playerTwo second player
   */
  public void removePlayersFromMatch(Player playerOne, Player playerTwo){
    playersInMatch.remove(playerOne);
    playersInMatch.remove(playerTwo);
  }

  /**
  * Removes player from an active game.
  * @param player the {@Link Player} to be removed from the game
  */
  public void removePlayerFromGame(Player player){
    activeGames.remove(player);
  }

}

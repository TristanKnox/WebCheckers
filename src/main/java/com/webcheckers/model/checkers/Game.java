package com.webcheckers.model.checkers;

import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Piece.PieceColor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Game class represents a single checkers game that takes place between two players.
 * It keeps tracks of the state of the board and the players involved
 *
 * @author Collin Bolles
 */
public class Game implements Iterable<Row> {

  /** Max 8 rows per board **/
  private static final int MAX_SIZE = 8;

  /** The player initiating the game **/
  private Player redPlayer;
  /** The player selected to play the game **/
  private Player whitePlayer;
  /** Represents each row of the board **/
  private List<Row> rows;
  /** The color of the player whose turn it is **/
  private PieceColor activateColor;

  /**
   * Creates an initial game with the rows initialized each player kept track of
   * @param playerOne The player to start the game
   * @param playerTwo The player invited to play the game
   */
  public Game(Player playerOne, Player playerTwo) {
    this.redPlayer = playerOne;
    this.whitePlayer = playerTwo;
    this.activateColor = PieceColor.RED;
    rows = new ArrayList<>();
    initializeRows();
  }

  /**
   * Handles initializing the boards. Each row initializes its own state
   */
  private void initializeRows() {
    for(int row = 0; row < MAX_SIZE; row++) {
      rows.add(new Row(row));
    }
  }

  public List<Row> getCopyRows() {
    return new ArrayList<>(rows);
  }

  /**
   * The iterator method required for the client UI. The iterator returns the iterator
   * from the list of rows
   * @return Iterator from the list of rows
   */
  @Override
  public Iterator<Row> iterator() {
    return rows.iterator();
  }

  public Player getRedPlayer() {
    return redPlayer;
  }

  public Player getWhitePlayer() {
    return whitePlayer;
  }

  public void setActivateColor(PieceColor color) {
    this.activateColor = color;
  }

  public PieceColor getActivateColor() {
    return activateColor;
  }

  /**
   * Get the color associated with the give player for this game.
   * If the player is not in this game then null is returned
   * @param player The player to check for in the game
   * @return The color associated with the player in the game, null if not in game
   */
  public PieceColor getPlayerColor(Player player) {
    if(player.equals(redPlayer))
      return PieceColor.RED;
    return player.equals(whitePlayer) ? PieceColor.WHITE : null;
  }
}

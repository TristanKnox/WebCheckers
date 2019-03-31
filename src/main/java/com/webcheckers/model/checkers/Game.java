package com.webcheckers.model.checkers;

import com.webcheckers.model.Player;
import com.webcheckers.model.TestBoardBuilder;
import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Turn.TurnResponse;
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

  public static enum EndGameCondition{
    OPPONENT_RESIGNED, RED_OUT_OF_PIECES
  }

  /** Max 8 rows per board **/
  public static final int MAX_SIZE = 8;

  /** The player initiating the game **/
  private Player redPlayer;
  /** The player selected to play the game **/
  private Player whitePlayer;
  /** Represents each row of the board **/
  private List<Row> rows;
  /** The color of the player whose turn it is **/

  private PieceColor activeColor;
  /** whether the game has ended **/
  private Boolean gameOver;

  /** Represents all of the turns made through out the duration of the game */
  private List<Turn> turns;

  /**
   * Creates an initial game with the rows initialized each player kept track of
   * @param playerOne The player to start the game
   * @param playerTwo The player invited to play the game
   */
  public Game(Player playerOne, Player playerTwo) {
    this.redPlayer = playerOne;
    this.whitePlayer = playerTwo;
    this.activeColor = PieceColor.RED;
    this.gameOver = false;
    rows = new ArrayList<>();
    initializeRows();
    this.turns = new ArrayList<>();
    turns.add(new Turn(activeColor));
  }

  /**
   * Create a cosome board setup for testing specific game sinarios
   * @param playerOne - The player to start the game
   * @param playerTwo - The player invited to the game
   * @param boardType - The board set up
   */
  public Game(Player playerOne, Player playerTwo, TestBoardBuilder.BoardType boardType){
    //Start game as normal
    this.redPlayer = playerOne;
    this.whitePlayer = playerTwo;
    this.activeColor = PieceColor.RED;
    rows = new ArrayList<>();
    initializeRows();
    this.turns = new ArrayList<>();
    turns.add(new Turn(activeColor));
    gameOver = false;
    //Take initialized board and refactor it based on board type given
    rows = TestBoardBuilder.getTestBoard(rows,boardType);

  }

  /**
   * Handles initializing the boards. Each row initializes its own state
   */
  private void initializeRows() {
    for(int row = 0; row < MAX_SIZE; row++) {
      rows.add(new Row(row));
    }
  }

  /**
   * Create a copied list of the rows used when developing the board view for each player
   * @return Copied list of the rows
   */
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

  /**
   * Get the red player
   * @return The red player (player 1)
   */
  public Player getRedPlayer() {
    return redPlayer;
  }

  /**
   * Get the white player
   * @return The white player (player 2)
   */
  public Player getWhitePlayer() {
    return whitePlayer;
  }

  /**
   * Get the color of the player whose turn it is
   * @return The color of the current players turn
   */
  public PieceColor getActiveColor() {
    return activeColor;
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

  /**
   * returns true if the game is over, false otherwise
   * @return the gameOver variable
   */
  public boolean isGameOver(){
    return this.gameOver;
  }

  /**
   * tells a game that it has ended
   */
  public void endGame(){
    this.gameOver = true;
  }

  /**
   * flips which player is active so that resignation worls properly
   */
  public void resignationEnabler(Player player) {
    if (this.getPlayerColor(player) == activeColor){
      activeColor = this.getPlayerColor(player) == PieceColor.RED ? PieceColor.WHITE : PieceColor.RED;
    }
  }
  /**
   * Get the space at the given location
   * @param pos The position to get the space from
   * @return The space located at a given location
   */
  public Space getSpace(Position pos) {
    return rows.get(pos.getRow()).getSpace(pos.getCell());
  }

  /**
   * Handles adding a move to the current active turn. The move is applied to the turn if the move
   * is valid. The result of the validation is returned based on if any rule was violated
   * @param move The move to be added to the current turn
   * @return TurnResponse based on the validity/any broken rules
   */
  public TurnResponse addMove(Move move) {
    Turn currentTurn = turns.get(turns.size() - 1);
    return currentTurn.addMove(this, move);
  }

  /**
   * Handles the logic of backing up a move. Calls backup on the most recent turn.
   * @precondition most recent turn has at least a single move
   */
  public void backupTurn() {
    Turn currentTurn = turns.get(turns.size() - 1);
    currentTurn.backupMove();
  }

  /**
   * Handles checking to make sure that a turn has a valid move. Should be called before attempting
   * to execute any turn
   * @return True if there is at least one valid turn in the most recent turn
   */
  public boolean currentTurnHasMove() {
    Turn currentTurn = turns.get(turns.size() - 1);
    return currentTurn.getMoves().size() > 0;
  }

  /**
   * Handles applying the current moves to the game sequentially updating the state of the game.
   * This method also updates the active color of the game.
   * @precondition There is a turn in the turn list
   * @precondition The current turn is a valid turn with no errors
   * @postcondition The next turn is created and the active color is changed to the next user
   */
  public void executeTurn() {
    Turn currentTurn = turns.get(turns.size() - 1);

    currentTurn.execute(this);

    // Flip active color
    this.activeColor = this.activeColor == PieceColor.RED ? PieceColor.WHITE : PieceColor.RED;
    turns.add(new Turn(activeColor));
    if(outOfPieces())
      this.endGame();
  }

  public boolean outOfPieces(){
    int whitePieces = 0;
    int redPieces = 0;
    for(Row r : rows){
      List<Space> spaces = r.getSpaces();
      for(Space s : spaces){
        if(s.getPiece() == null){
          continue;
        }
        else if( s.getPiece().getColor() == PieceColor.RED){
          redPieces++;
        }
        else{
          whitePieces++;
        }
      }
    }
    return(whitePieces == 0 || redPieces == 0);
  }
}

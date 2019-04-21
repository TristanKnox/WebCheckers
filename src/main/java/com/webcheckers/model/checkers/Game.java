package com.webcheckers.model.checkers;

import com.webcheckers.model.BoardState;
import com.webcheckers.model.Player;
import com.webcheckers.model.BoardBuilder;
import com.webcheckers.model.ai.AIPlayer;
import com.webcheckers.model.checkers.Piece.PieceColor;
import com.webcheckers.model.checkers.Piece.PieceType;
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
    RED_RESIGNED, WHITE_RESIGNED, RED_OUT_OF_PIECES, WHITE_OUT_OF_PIECES, RED_OUT_OF_MOVES, WHITE_OUT_OF_MOVES
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
  /** Why the game has ended */
  private EndGameCondition endGameCondition;
  /** Represents all of the turns made through out the duration of the game */
  private List<Turn> turns;
  /** The unique game id **/
  private int gameID;
  /** The total number of games constructed - used to get the id of this game **/
  private static int totalgames;

  /**
   * Constructor - this will create a new game with a given board type
   * To create a standard game the board type should be standard
   * @param playerOne - The player to start the game
   * @param playerTwo - The player invited to the game
   * @param boardType - The board set up
   */
  public Game(Player playerOne, Player playerTwo, BoardBuilder.BoardType boardType){
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
    rows = BoardBuilder.getTestBoard(rows,boardType);
    synchronized (Game.class){
      totalgames++;
      gameID = totalgames;
    }
  }

  /**
   * An overloaded constructor for the creation of a replay
   * @param playerOne the first player
   * @param playerTwo the second player
   * @param turns the list of turns.
   */
  public Game(Player playerOne, Player playerTwo, BoardState boardState){
    this.redPlayer = playerOne;
    this.whitePlayer = playerTwo;
    this.activeColor = boardState.getActivePlayer();
    this.gameOver = boardState.isGameOver();
    this.rows = boardState.getRows();
    this.endGameCondition = boardState.getEndGameCondition();
  }

  /**
   * geturns the game's unique ID
   * @return id
   */
  public int getID(){
    return gameID;
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
    List<Row> copy = new ArrayList<>();
    for(Row row: rows) {
      copy.add(new Row(row));
    }
    return copy;
    //return new ArrayList<>(rows);
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
    return player.equals(whitePlayer) ? PieceColor.WHITE : PieceColor.RED;
  }

  /**
   * Return the opponent of the given player
   * @param player - the player you want the opponent for
   * @return - the opponent
   */
  public Player getOpponent(Player player){
    PieceColor playerColor = getPlayerColor(player);
    return playerColor == PieceColor.RED ? whitePlayer : redPlayer;
  }

  /**
   * returns true if the game is over, false otherwise
   * @return the gameOver variable
   */
  public boolean isGameOver(){
    return this.gameOver;
  }

  /**
   * tells a game that it has ended as well as why
   */
  public void endGame(EndGameCondition condition){
    this.endGameCondition = condition;
    this.gameOver = true;
  }

  /**
   * A Getter method for endGameCondition
   * @return - the endGamecondition
   */
  public EndGameCondition getEndGameCondition(){ return this.endGameCondition; }

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
   * Gets a list of positions for each piece of the given color.
   * @param color The color of the piece to get the positions of
   * @return List of positions where each piece of a given color are
   */
  public List<Position> getPiecePositions(PieceColor color) {
    List<Position> positions = new ArrayList<>();
    for(Row row: rows)
      for(Space space: row.getSpaces())
        if(space.getPiece() != null && space.getPiece().getColor() == color)
          positions.add(new Position(row.getIndex(), space.getCellIdx()));
    return positions;
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
   * Checks to make sure that the turn is complete. A turn is complete if it is a single move,
   * single jump, or multi move that has made all jumps possible
   * @return True if the turn meets the above criteria
   * @precondition The list of turns is not empty
   * @precondition There is at least one move to check in turns
   */
  public boolean turnIsComplete() {
    Turn currentTurn = turns.get(turns.size() - 1);
    return currentTurn.isComplete(this);
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
    toggleActiveColor();
    turns.add(new Turn(activeColor));

    checkEndGame();

    // If player two is an AIPlayer, then have AI make a move
    if(!gameOver && whitePlayer instanceof AIPlayer && activeColor == PieceColor.WHITE) {
      AIPlayer ai = (AIPlayer)whitePlayer;
      Thread moveThread = new Thread(()-> ai.makeMove(this));
      moveThread.start();
    }

  }

  /**
   * Switches active Color to the next player
   */
  public void toggleActiveColor(){
    this.activeColor = this.activeColor == PieceColor.RED ? PieceColor.WHITE : PieceColor.RED;
  }

  /**

   * Flips the active color to the other player. Signaling the end of a turn
   * Checks both end game conditions ( OutOfPieces and OutOfMoves )
   * If either are true then the end game is triggered and the EndGameCondition is set
   */
  public void checkEndGame(){
    //Checks and sets EndGameConditions for outOfPieces
    if(getOutOfPieces() != null) {
      if (getOutOfPieces() == PieceColor.RED)
        this.endGame(EndGameCondition.RED_OUT_OF_PIECES);
      if(getOutOfPieces() == PieceColor.WHITE)
        this.endGame(EndGameCondition.WHITE_OUT_OF_PIECES);
    }
    //Checks and sets EndGameConditions for outOfMoves
    //Only check for this condition if the game has not already been set to game over
    if(!gameOver) {
      if (outOfMoves(activeColor)) {
        if (activeColor == PieceColor.RED)
          this.endGame(EndGameCondition.RED_OUT_OF_MOVES);
        if (activeColor == PieceColor.WHITE)
          this.endGame(EndGameCondition.WHITE_OUT_OF_MOVES);
      }
    }
  }

  /**
   * Checks to see if either player is out of pieces
   * @return - the PieceColor of the player that is out of pieces or null if neither player is out of pieces
   */
  public PieceColor getOutOfPieces(){
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
    if(redPieces <= 0)
      return PieceColor.RED;
    else if(whitePieces <= 0)
      return PieceColor.WHITE;
    return null;
  }



  /**
   * Checks to see if the active color can move
   * PreCondition - this should be called after the activeColor has been switched to the color of the player who is about
   * to take their turn but before they are notified it is their turn
   * @param activeColor - the color of the player who is about to take their turn
   * @return - true if the activeColor cannot move false if the activeColor can move
   */
  private boolean outOfMoves(PieceColor activeColor){
    Turn turn = new Turn(activeColor);
    List<Position> piecePositions = this.getPiecePositions(activeColor);
    for(Position piecePosition: piecePositions) {
      if(turn.pieceCanSimpleMove(piecePosition,this))
        return false;
      if(turn.pieceCanJump(piecePosition, this))
        return false;
    }
    return true;
  }


  /**
   * Flips the active color
   */
  public void flipActiveColor(){
    this.activeColor = this.activeColor == PieceColor.RED ? PieceColor.WHITE : PieceColor.RED;
  }

  /**
   * Returns the list of turns stored in this game
   * @return list of turns
   */
  public List<Turn> getTurnList(){return this.turns;}
}

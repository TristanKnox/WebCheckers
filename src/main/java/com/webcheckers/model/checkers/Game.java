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
    OPPONENT_RESIGNED, RED_OUT_OF_PIECES, WHITE_OUT_OF_PIECES, RED_OUT_OF_MOVES, WHITE_OUT_OF_MOVES
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
    this.endGameCondition = null;
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
    if(outOfPieces() != null) {
      if (outOfPieces() == PieceColor.RED)
        this.endGame(EndGameCondition.RED_OUT_OF_PIECES);
      if(outOfPieces() == PieceColor.WHITE)
        this.endGame(EndGameCondition.WHITE_OUT_OF_PIECES);
    }
  }

  public PieceColor outOfPieces(){
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

  private PieceColor outOfMoves(){
   return null; //TODO fix this latter
  }

  /**
   * Validates if a move is possible for a given piece
   * @param piecePosition - the position of the piece in question
   * @param piece - the piece in question
   * @return - true if a move is possible false if a move is not possible
   */
  private boolean pieceCaneMove(Position piecePosition, Piece piece){
    //If Piece is red check in the positive direction
    if(piece.getColor() == PieceColor.RED && canMoveInPosotiveDirection(piecePosition,piece.getColor()))
      return true;
    //If Piece is white and check in the negative direction
    if(piece.getColor() == PieceColor.WHITE && canMoveInNegativeDirection(piecePosition,piece.getColor()))
      return true;
    //If Piece is a King check in both directions
    if(piece.getType() == Piece.PieceType.KING){
      if(canMoveInNegativeDirection(piecePosition,piece.getColor()) || canMoveInPosotiveDirection(piecePosition, piece.getColor()))
        return true;
    }
    return false;

  }

  /**
   * Validates if a move is possible in the positive direction. the positive direction is the direction in which the row index is increasing
   * @param piecePosition - the position of the piece that is being checked if it can move or not
   * @param pieceColor - the color of the piece being checked
   * @return - true if the piece can move in the positive direction false if it cannot move in the positive direction
   */
  private boolean canMoveInPosotiveDirection(Position piecePosition, PieceColor pieceColor){
    boolean result;
    //Check to the left
    result = checkCanMoveWithDirection(1,-1,piecePosition,pieceColor);
    if(result == true)//If a move is found there is no need to continue
      return result;
    //Check to the right
    result = checkCanMoveWithDirection(1,1,piecePosition,pieceColor);
    return result;
  }

  /**
   * Validates if a move is possible in the negative direction. The negative direction is the direction in which the row index is decreasing
   * @param piecePosition - the position of the piece that is being checked if it can move or not
   * @param pieceColor - the color of the piece being checked
   * @return - true if the piece can move in the negative direction false if it cannot move in the negative direction
   */
  private boolean canMoveInNegativeDirection(Position piecePosition, PieceColor pieceColor){
    boolean result;
    //Check to the left
    result = checkCanMoveWithDirection(-1,-1,piecePosition,pieceColor);
    if(result == true)//If a move is found there is no need to continue
      return result;
    //Check to the right
    result = checkCanMoveWithDirection(-1,1,piecePosition,pieceColor);
    return result;
  }

  /**
   * Checks if a move can be made in a given direction
   * @param rowDirection - an int representing the direction to look with regards to the rows
   * @param columDiretion - an int representing the direction to look with regards to the column
   * @param piecePosition - the position of the piece that is being check if it can move or not
   * @param pieceColor - the color of the piece that is being checked
   * @return - true if there is a move available or false if there is no move available
   */
  private boolean checkCanMoveWithDirection(int rowDirection, int columDiretion,Position piecePosition, PieceColor pieceColor){
    //This is the location that will be checked to see if a move can be moved here
    Position locationInQuestion;
    Space spaceInQuestion;
    //Sets the locationInQuestion
    locationInQuestion = new Position(piecePosition.getRow() + rowDirection,piecePosition.getCell() + columDiretion);
    //Insure that the locationInQuestion exists on the board
    if(isValidLocation(locationInQuestion)){
      //Get the space associated with the locationInQuestion
      spaceInQuestion = getSpace(locationInQuestion);
      //If no other piece is there it is possible to move here
      if(spaceInQuestion.getPiece() == null)
        return true;
      //Otherwise as long as the piece that is there is not the same color a jump may be posible
      else if(spaceInQuestion.getPiece().getColor() != pieceColor)
        locationInQuestion = new Position(locationInQuestion.getRow() + rowDirection,locationInQuestion.getCell() + columDiretion);//
      //Insure that the locationInQuestion exists on the board
      if(isValidLocation(locationInQuestion)){
        //Get the space associated with the locationInQuestion
        spaceInQuestion = getSpace(locationInQuestion);
        //If no other piece is there it is possible to move here
        if(spaceInQuestion.getPiece() == null)
          return true;
      }
    }
    return false;
  }

  /**
   * Validates that a given position exists on the board
   * @param position - the postion in question
   * @return true if its a good location false if not a valid location
   */
  private boolean isValidLocation(Position position){
    boolean result = false;
    if(position.getRow() >= 0  && position.getRow() <= 7)
      if(position.getCell() >= 0 && position.getCell() <=7)
        result = true;
    return result;
  }
}

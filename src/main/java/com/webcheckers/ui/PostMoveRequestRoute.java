package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Move;
import com.webcheckers.model.checkers.Turn;
import com.webcheckers.model.checkers.Turn.TurnResponse;
import com.webcheckers.util.Message;
import spark.*;

import java.net.URLDecoder;

/**
 * This class hanlds posted moves and validating them
 */
public class PostMoveRequestRoute implements Route {

  public static final Message SPACE_TAKE_MSG = Message.error("Space is already take");
  public static final Message INCORRECT_PIECE_USED_MSG = Message.error("Piece does not belong to you");
  public static final Message MOVE_TO_WHITE_SPACE_MSG = Message.error("Cannot move to white space");
  public static final Message INVALID_MULTI_MOVE_MSG = Message.error("That is not a valid multi jump");
  public static final Message NOT_DIAGONAL_MSG = Message.error("Move has to be diagonal");
  public static final Message INVALID_DIRECTION_MSG = Message.error("That piece cannot move in that direction");
  public static final Message INVALID_SIMPLE_MOVE_MSG = Message.error("Cannot move piece to here");
  public static final Message MUST_JUMP_MSG = Message.error("You have to jump");
  public static final Message VALID_MOVE_MSG = Message.info("Move is valid");
  public static final Message GENERIC_ERROR_MSG = Message.error("No message associated with this error");

  GameCenter gameCenter;

  public PostMoveRequestRoute(GameCenter gameCenter){
    this.gameCenter = gameCenter;
  }

  /**
   * Returns a message associated with the given turn response. Any move that
   * is invalid will return a message of type error with the description of why
   * the move is invalid. Note that each time a new turn response is created,
   * this method needs to be updated with ability to return a message
   * associated with the response.
   * @param turnResponse The response associated with the given move attempt
   * @return A message with any rule broken
   */
  private Message getMoveResponseMessage(TurnResponse turnResponse) {
    switch(turnResponse) {
      case SPACE_TAKEN:
        return SPACE_TAKE_MSG;
      case INCORRECT_PIECE_USED:
        return INCORRECT_PIECE_USED_MSG;
      case MOVE_TO_WHITE_SPACE:
        return MOVE_TO_WHITE_SPACE_MSG;
      case INVALID_MULTI_MOVE:
        return INVALID_MULTI_MOVE_MSG;
      case NOT_DIAGONAL:
        return NOT_DIAGONAL_MSG;
      case INVALID_DIRECTION:
        return INVALID_DIRECTION_MSG;
      case INVALID_SIMPLE_MOVE:
        return INVALID_SIMPLE_MOVE_MSG;
      case MUST_JUMP:
        return MUST_JUMP_MSG;
      case VALID_TURN:
        return VALID_MOVE_MSG;
      default:
        return GENERIC_ERROR_MSG;
    }
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    Gson gson = new Gson();
    Session session = request.session();
    //Get the player
    Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
    //Get the game associated with the player
    Game game = gameCenter.getGame(player);
    //Get the posted move
    String moveJson = request.body();
    System.out.println(moveJson);
    //Decode the moveJson to a usable format
    moveJson = URLDecoder.decode(moveJson,"UTF-8");

    //Convert the JsonMove to a Move object
    Move move = gson.fromJson(moveJson.substring(11),Move.class);
    //Validates the move
    Turn.TurnResponse moveResponse = game.addMove(move);
    //Sets up the message to be returned
    Message msg = getMoveResponseMessage(moveResponse);

    return gson.toJson(msg);
  }
}

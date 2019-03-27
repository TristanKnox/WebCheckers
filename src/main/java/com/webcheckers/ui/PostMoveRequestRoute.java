package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Move;
import com.webcheckers.model.checkers.Turn;
import com.webcheckers.util.Message;
import spark.*;

import java.net.URLDecoder;

/**
 * This class hanlds posted moves and validating them
 */
public class PostMoveRequestRoute implements Route {

  public static final String VALID_MOVE = "Move is valid";
  public static final String INVALID_MOVE = "Invalid Move: ";


  GameCenter gameCenter;

  public PostMoveRequestRoute(GameCenter gameCenter){
    this.gameCenter = gameCenter;
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
    //Decode the moveJson to a usable formate
    moveJson = URLDecoder.decode(moveJson,"UTF-8");

    //Convert the JsonMove to a Move object
    Move move = gson.fromJson(moveJson.substring(11),Move.class);
    //Validats the movee
    Turn.TurnResponse moveResponce = game.addMove(move);
    //Sets up the mesage to be returnd
    Message msg;
    if(moveResponce == Turn.TurnResponse.VALID_TURN)
      msg = Message.info(this.VALID_MOVE);
    else
      msg = Message.error(this.INVALID_MOVE + moveResponce);
    return gson.toJson(msg);
  }
}
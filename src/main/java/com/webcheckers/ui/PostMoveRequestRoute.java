package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Move;
import com.webcheckers.model.checkers.Turn;
import com.webcheckers.util.Message;
import spark.*;

import java.net.URLDecoder;

public class PostMoveRequestRoute implements Route {

    TemplateEngine templateEngine;
    GameCenter gameCenter;

    public PostMoveRequestRoute(TemplateEngine templateEngine, GameCenter gameCenter){
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session session = request.session();
        Player player = session.attribute(GetHomeRoute.PLAYER_KEY);
        Game game = gameCenter.getGame(player);
        String moveJson = request.body();
        moveJson = URLDecoder.decode(moveJson,"UTF-8");
        System.out.println(moveJson);//TODO remove me
        Gson gson = new Gson();
        Move move = gson.fromJson(moveJson.substring(11),Move.class);//TODO uncomment this once Move and Position has bee made
        System.out.println(move.toString());//TODO remvoe me
        Turn.TurnResponse moveResponce = game.addMove(move);
        Message msg;
        if(moveResponce == Turn.TurnResponse.VALID_TURN)
            msg = Message.info("Move is good");
        else
            msg = Message.error("Invalid move: " + moveResponce);

       // return gson.toJson(Message.info("Move Good"));
        //return gson.toJson(Message.error("Bad Move sucker!!!!!!!!!!!!"));
        System.out.println(msg.toString());
        return gson.toJson(msg);
    }
}

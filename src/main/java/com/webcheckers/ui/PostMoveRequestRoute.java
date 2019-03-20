package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.checkers.Move;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

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
        String s = request.body();

        s = URLDecoder.decode(s,"UTF-8");
        System.out.println(s);
        Gson gson = new Gson();
        Move move = gson.fromJson(s.substring(11),Move.class);//TODO uncomment this once Move and Position has bee made
        System.out.println(move.toString());

        return null;
    }
}

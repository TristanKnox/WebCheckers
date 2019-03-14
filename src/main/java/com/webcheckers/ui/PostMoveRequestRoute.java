package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class PostMoveRequestRoute implements Route {

    TemplateEngine templateEngine;

    public PostMoveRequestRoute(TemplateEngine templateEngine){this.templateEngine = templateEngine;}
    @Override
    public Object handle(Request request, Response response) throws Exception {

        System.out.println(request.body());

        return null;
    }
}

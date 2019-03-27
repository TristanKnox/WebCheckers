package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;


import com.google.gson.Gson;

import com.webcheckers.appl.PlayerLobby;


import spark.Request;
import spark.Response;
import spark.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostResignationRouteTest {
  private PostResignationRoute CuT;

  //mock objects
  private Request request;
  private Session session;

  private GameCenter gameServices;
  private PlayerLobby playerLobby;
  private String playerName = "KAKAKAK";
  private String playerName2 = "Do you think you can dance";

  private String mes_str;
  private Gson gson;


}

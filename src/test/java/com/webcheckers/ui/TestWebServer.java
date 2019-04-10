package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;

public class TestWebServer {
  private TemplateEngine templateEngine;
  private Gson gson;
  private WebServer CuT;
  @BeforeEach
  public void setUp(){
    templateEngine = mock(TemplateEngine.class);
    gson = new Gson();
  }
  @Test
  public void testCtor(){
    templateEngine = mock(TemplateEngine.class);
    gson = new Gson();
    WebServer webServer = new WebServer(templateEngine,gson);
    assertNotNull(webServer);
  }

}

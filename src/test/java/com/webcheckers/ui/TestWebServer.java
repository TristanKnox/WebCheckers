package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.google.gson.Gson;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;

public class TestWebServer {
  private TemplateEngine templateEngine;
  private Gson gson;
  private WebServer CuT;
  private Logger LOG;
  @BeforeEach
  public void setUp(){
    templateEngine = mock(TemplateEngine.class);
    gson = new Gson();
    CuT = new WebServer(templateEngine,gson);
    LOG = new Logger(Logger.getLogger(WebServer.class.getName()));
  }
  @Test
  public void testCtor(){
    templateEngine = mock(TemplateEngine.class);
    gson = new Gson();
    WebServer webServer = new WebServer(templateEngine,gson);
    assertNotNull(webServer);
  }
  @Test
  public void testInitialize(){
    CuT.initialize();

  }

}

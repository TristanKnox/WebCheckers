package com.webcheckers.ui.ReplayRoutes;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.ReplayCenter;
import com.webcheckers.model.BoardState;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;
import com.webcheckers.model.checkers.Game;
import com.webcheckers.model.checkers.Piece;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

public class GetReplayRouteTest {
  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine templateEngine;
  private ReplayCenter replayCenter;
  private GetReplayRoute CuT;
  private Player watcher;
  private Replay replay;
  private Player p1;
  private Player p2;
  private BoardState boardState;


  @BeforeEach
  public void setUp(){
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    replayCenter = mock(ReplayCenter.class);
    templateEngine = mock(TemplateEngine.class);
    watcher = mock(Player.class);
    p1 = mock(Player.class);
    p2 = mock(Player.class);
    replay = mock(Replay.class);
    when(replay.getPlayer1()).thenReturn(p1);
    when(replay.getPlayer2()).thenReturn(p2);
    when(p1.getName()).thenReturn("Tom");
    when(p2.getName()).thenReturn("Sam");

    boardState = mock(BoardState.class);
    when(replayCenter.getReplay(watcher)).thenReturn(replay);
    CuT = new GetReplayRoute(replayCenter,templateEngine);
  }
  @Test
  public void testCtor(){
    GetReplayRoute cut = new GetReplayRoute(replayCenter,templateEngine);
    assertNotNull(cut);
  }
  //todo
  @Test
  public void testHandle(){
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(watcher);
    when(replay.hasNext()).thenReturn(true);
    when(replay.hasPrev()).thenReturn(false);
    when(replay.getCurrentBoardState()).thenReturn(boardState);
    when(boardState.getActivePlayer()).thenReturn(Piece.PieceColor.RED);

    CuT.handle(request,response);
    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    testHelper.assertViewModelAttribute(
            GetGameRoute.GAME_TITLE_ATTR, GetGameRoute.GAME_TITLE);
    testHelper.assertViewModelAttribute(
            "currentUser", watcher);
    testHelper.assertViewModelAttribute(
            "redPlayer", p1);
    testHelper.assertViewModelAttribute(
            "whitePlayer", p2);
    testHelper.assertViewModelAttribute(
            "activeColor", Piece.PieceColor.RED);
    testHelper.assertViewModelAttribute(
            "viewMode", "REPLAY");
  }

  @Test
  public void testEndMsg(){
    Game game = mock(Game.class);
    when(game.getRedPlayer()).thenReturn(p1);
    when(game.getWhitePlayer()).thenReturn(p2);
    String msg = "Game Over: ";
    String win = " WINS";
    String resign = " has resigned";
    String outP = " is out of pieces. ";
    String outM = " is out of moves. ";
    String actual;
    String expected;
    when(game.getEndGameCondition()).thenReturn(Game.EndGameCondition.RED_RESIGNED);
    actual = CuT.getEndGameMessage(game);
    expected = msg + p1.getName() + resign;
    assert(actual.equals(expected));
    when(game.getEndGameCondition()).thenReturn(Game.EndGameCondition.WHITE_RESIGNED);
    actual = CuT.getEndGameMessage(game);
    expected = msg + p2.getName() + resign;
    assert(actual.equals(expected));
    when(game.getEndGameCondition()).thenReturn(Game.EndGameCondition.WHITE_OUT_OF_MOVES);
    actual = CuT.getEndGameMessage(game);
    expected = msg + p2.getName() + outM + p1.getName()+win;
    assert(actual.equals(expected));
    when(game.getEndGameCondition()).thenReturn(Game.EndGameCondition.RED_OUT_OF_MOVES);
    actual = CuT.getEndGameMessage(game);
    expected = msg + p1.getName() + outM + p2.getName()+win;
    assert(actual.equals(expected));
    when(game.getEndGameCondition()).thenReturn(Game.EndGameCondition.WHITE_OUT_OF_PIECES);
    actual = CuT.getEndGameMessage(game);
    expected = msg + p2.getName() + outP + p1.getName()+win;
    assert(actual.equals(expected));
    when(game.getEndGameCondition()).thenReturn(Game.EndGameCondition.RED_OUT_OF_PIECES);
    actual = CuT.getEndGameMessage(game);
    expected = msg + p1.getName() + outP + p2.getName()+win;
    assert(actual.equals(expected));
  }
}

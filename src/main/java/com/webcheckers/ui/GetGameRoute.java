package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class GetGameRoute implements Route {

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());
  private final TemplateEngine templateEngine;
  private static final String GAME_TITLE = "Checkers";

  public GetGameRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetGameRoute is initialized.");
  }

  public Object handle(Request request, Response response) {
    LOG.finer("GetGameRoute is invoked.");
    /*
     * TODO:
     * Add logic to insert data into the game ftl
     */
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", GAME_TITLE);
    // Fake user data
    vm.put("currentUser", new Person("Bobbothy"));
    vm.put("redPlayer", new Person("Samilly"));
    vm.put("whitePlayer", new Person("Bobbothy"));
    vm.put("activeColor", "white");
    // Fake view mode
    vm.put("viewMode", "PLAY");
    // Fake board
    vm.put("board", new BoardView());


    // render the View
    return templateEngine.render(new ModelAndView(vm , "game.ftl"));
  }

  // Begin junk classes
  /**
   * TODO:
   * Remove when Player object complete
   */
  public class Person {
    private String name;

     Person(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public String toString() {
      return "{Person " + " '" + name + "'}";
    }
  }

  /**
   * TODO:
   * Remove when game board complete
   */
  public class BoardView implements Iterable<Row> {
    private List<Row> rows;
    private final int MAX_SIZE = 7;

    public BoardView() {
      rows = new ArrayList<>();
      generateBoard();
    }

    private void generateBoard() {
      for(int row = 0; row < MAX_SIZE; row++)
        rows.add(new Row(row));
    }


    @Override
    public Iterator<Row> iterator() {
      return new Iterator<Row>() {
        private int count = 0;
        @Override
        public boolean hasNext() {
          return count < rows.size();
        }

        @Override
        public Row next() {
          return rows.get(count++);
        }
      };
    }
  }

  public class Row implements Iterable<Space> {

    private List<Space> spaces;
    private int index;
    private final int MAX_SIZE = 7;

    public Row(int index) {
      this.index = index;
      spaces = new ArrayList<>();
      generateSpaces();
    }

    public int getIndex() {
      return index;
    }

    private void generateSpaces() {
      // Check for spaces with pieces
      PieceColor color = null;
      if (index < 3)
        color = PieceColor.RED;
      else if (index > 5)
        color = PieceColor.WHITE;
      for (int col = 0; col < MAX_SIZE; col++) {
        Space space = null;
        if ((index == 0 || index == 2 || index == 6) && col % 2 != 0)
          space = new Space(col, new Piece(PieceType.SINGLE, color), true);
        else if ((index == 1 || index == 5 || index == 7) && col % 2 == 0)
          space = new Space(col, new Piece(PieceType.SINGLE, color), true);
        else if((index == 3 && col % 2 != 0) || (index == 4 && col % 2 == 0))
          space = new Space(col, null, true);
        else
          space = new Space(col, null, false);
        spaces.add(space);
      }
    }

    @Override
    public Iterator<Space> iterator() {
      return new Iterator<Space>() {
        private int count = 0;

        @Override
        public boolean hasNext() {
          return count < spaces.size();
        }

        @Override
        public Space next() {
          return spaces.get(count++);
        }
      };
    }
  }

  public class Space {
    private int cellIdx;
    private Piece piece;
    private boolean valid;

    public Space(int cellIdx, Piece piece, boolean valid) {
      this.cellIdx = cellIdx;
      this.piece = piece;
      this.valid = valid;
    }

    public int getCellIdx() {
      return cellIdx;
    }

    public boolean isValid() {
      return valid;
    }
  }

  public class Piece {
    private PieceType type;
    private PieceColor color;

    public Piece(PieceType type, PieceColor color) {
      this.type = type;
      this.color = color;
    }

    public PieceType getType() {
      return type;
    }

    public PieceColor getColor() {
      return color;
    }
  }

  public enum PieceType {
    SINGLE,
    KING
  }

  public enum PieceColor {
    RED,
    WHITE
  }

  // End junk classes
}

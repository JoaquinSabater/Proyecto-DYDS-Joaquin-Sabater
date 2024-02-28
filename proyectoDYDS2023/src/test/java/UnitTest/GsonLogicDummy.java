package UnitTest;

import com.google.gson.JsonArray;
import model.Game;
import model.GsonLogic;
import model.SearchResult;

import java.io.IOException;

public class GsonLogicDummy extends GsonLogic {

    boolean searchListInWiki = false;

    @Override
    public JsonArray searchListInWiki(String gameName) throws IOException {
        searchListInWiki = true;
        return null;
    }

    @Override
    public Game searchInWiki(SearchResult sr) throws IOException {
        Game game = new Game();
        game.setName("Test");
        return game;
    }


}

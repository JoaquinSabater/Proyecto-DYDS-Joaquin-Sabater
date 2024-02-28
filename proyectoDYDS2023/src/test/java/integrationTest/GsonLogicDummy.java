package integrationTest;

import com.google.gson.JsonArray;
import model.Game;
import model.GsonLogic;
import model.SearchResult;

import java.io.IOException;

public class GsonLogicDummy extends GsonLogic {

    boolean searchListInWiki = false;

    @Override
    public JsonArray searchListInWiki(String gameName) throws IOException {
        JsonArray ja = new JsonArray();
        ja.add(true);
        return ja;
    }

    @Override
    public Game searchInWiki(SearchResult sr) throws IOException {
        return null;
    }

}

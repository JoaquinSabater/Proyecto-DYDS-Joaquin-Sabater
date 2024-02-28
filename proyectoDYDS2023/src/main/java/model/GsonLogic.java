package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GsonLogic {
    private WikipediaSearchAPI searchAPI;

    private WikipediaPageAPI pageAPI;
    private JsonArray jsonResults;

    private Gson gson;

    private Game game;

    private String selectedResultTitle = null;
    private String LastSerchedText = "";

    public GsonLogic(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        searchAPI = retrofit.create(WikipediaSearchAPI.class);
        pageAPI = retrofit.create(WikipediaPageAPI.class);
    }

    public JsonArray searchListInWiki(String gameName) throws IOException {
        Response<String> callForSearchResponse;
        callForSearchResponse = searchAPI.searchForTerm(gameName + " articletopic:\"video-games\"").execute();
        gson = new Gson();
        JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
        JsonObject query = jobj.get("query").getAsJsonObject();
        Iterator<JsonElement> resultIterator = query.get("search").getAsJsonArray().iterator();
        jsonResults = query.get("search").getAsJsonArray();
        return jsonResults;
    }

    public Game searchInWiki(SearchResult sr) throws IOException {
        game = new Game();
        Response<String> callForPageResponse = pageAPI.getExtractByPageID(sr.pageID).execute();
        JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
        JsonObject query2 = jobj2.get("query").getAsJsonObject();
        JsonObject pages = query2.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        JsonElement searchResultExtract2 = page.get("extract");
        if (searchResultExtract2 == null) {
            game.setTextContent("No Results");
        } else {
            LastSerchedText = "<h1>" + sr.title + "</h1>";
            selectedResultTitle = sr.title;
            LastSerchedText += searchResultExtract2.getAsString().replace("\\n", "\n");
            LastSerchedText = textToHtml(LastSerchedText);
            game.setTextContent(LastSerchedText);
            game.setName(sr.title);
        }
        return game;
    }

    public static String textToHtml(String text) {
        StringBuilder builder = new StringBuilder();
        builder.append("<font face=\"arial\">");
        String fixedText = text
                .replace("'", "`"); //Replace to avoid SQL errors, we will have to find a workaround..
        builder.append(fixedText);
        builder.append("</font>");
        return builder.toString();
    }
}

package model;

import com.google.gson.JsonArray;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface GameModel {
    void searchGames(String gameName) throws IOException;

    void searchGameSelected(SearchResult sr) throws IOException, SQLException;

    public Game getGame();

    void getHistory() throws SQLException;

    public JsonArray getJsonResults();

    public void saveGameInDB(Game game) throws SQLException;

    public void getInfoGameSaveInDB(String title) throws SQLException;

    public void setListener (GameSearchLisener myListener);

    public void setListenerStored(GameSearchLisener myListener);

    void setListenerHistory(GameSearchLisener myListener);

    void setListenerStoredToGetText(GameSearchLisener myListener);

    void getGamesSaveInDB() throws SQLException;

    String getTitleInfo();

    ArrayList<String> getListOfTitles();

    ArrayList<String> getHistoryList();
}

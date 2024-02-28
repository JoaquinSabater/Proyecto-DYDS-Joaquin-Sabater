package model;


import com.google.gson.JsonArray;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


public class GameModelImpl implements GameModel {
    private JsonArray jsonResults;

    private GsonLogic gsonLogic;

    private GameSearchLisener myListener;

    private GameSearchLisener myListenerStored;

    private GameSearchLisener myListenerHistory;

    private GameSearchLisener myListenerStoredToGetText;

    private ArrayList<String> titles;

    private ArrayList<String> history;

    private String infoOfTheGame;

    private DataBase db;

    private Game game;
    public GameModelImpl(GsonLogic gsonLogic,DataBase db){
        this.db =db;
        this.gsonLogic = gsonLogic;
        titles = new ArrayList<>();
        history = new ArrayList<>();
        try {
            getGamesSaveInDB();
            getHistory();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void searchGames(String gameName) throws IOException {
        jsonResults = gsonLogic.searchListInWiki(gameName);
        myListener.gameListFound();
    }

    @Override
    public void searchGameSelected(SearchResult sr) throws IOException, SQLException {
        game = gsonLogic.searchInWiki(sr);
        db.insertGameIntoHistory(sr.title);
        myListener.gameFound();
        myListenerHistory.updateGameHistory();
    }

    @Override
    public void getHistory() throws SQLException {
        history = db.getHistory();
    }

    public JsonArray getJsonResults() {
        return jsonResults;
    }

    @Override
    public void saveGameInDB(Game game) throws SQLException {
        db.saveGameInDB(game);
        myListenerStored.gameToSaveReady();
    }

    @Override
    public void getInfoGameSaveInDB(String title) throws SQLException {
        infoOfTheGame = db.getInfoGameSaveInDB(title);
        myListenerStoredToGetText.weNeedGameInfo();
    }

    public void getGamesSaveInDB() throws SQLException {
        titles = db.getGamesSaveInDB();
    }

    @Override
    public String getTitleInfo() {
        return infoOfTheGame;
    }

    public ArrayList<String> getListOfTitles(){
        return titles;
    }

    @Override
    public ArrayList<String> getHistoryList() {
        return history;
    }

    public Game getGame() {
        return game;
    }

    public void setListener (GameSearchLisener myListener){this.myListener = myListener;}

    public void setListenerStored(GameSearchLisener myListener){this.myListenerStored = myListener;}

    public void setListenerHistory(GameSearchLisener myListener){this.myListenerHistory = myListener;}

    @Override
    public void setListenerStoredToGetText(GameSearchLisener myListener) {
        this.myListenerStoredToGetText = myListener;
    }

}

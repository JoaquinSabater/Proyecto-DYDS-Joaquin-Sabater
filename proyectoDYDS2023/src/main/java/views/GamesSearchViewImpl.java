package views;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import controller.GameController;
import model.Game;
import model.GameModel;
import model.GameSearchLisener;
import model.SearchResult;
import javax.swing.*;
import java.awt.*;

public class GamesSearchViewImpl implements GameSearchView{
    private JPanel searchPanel;
    private JTextField gameToSearch;
    private JButton searchButton;
    private JTextPane dataTheGame;
    private JButton saveLocallyButton;

    private Game game;

    private JsonArray jsonResults;

    private GameModel gameModel;

    private GameController gameController;

    public GamesSearchViewImpl(GameModel gameModel,GameController gameController){
        dataTheGame.setContentType("text/html");
        this.gameController = gameController;
        this.gameModel = gameModel;
        initListeners();
    }


    private void initListeners(){
        searchButton.addActionListener(actionEvent ->gameController.initSearch(gameToSearch.getText()));
        gameModel.setListener(new GameSearchLisener() {
            @Override
            public void gameListFound(){processList();}

            @Override
            public void gameFound() {}

            @Override
            public void gameToSaveReady() {}

            @Override
            public void weNeedGameInfo() {}

            @Override
            public void updateGameHistory() {

            }
        });
        saveLocallyButton.addActionListener(actionEvent -> gameController.saveGame(game));
    }

    private void processList() {
        jsonResults = gameModel.getJsonResults();
        JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
        for (JsonElement je : jsonResults) {
            JsonObject searchResult = je.getAsJsonObject();
            String searchResultTitle = searchResult.get("title").getAsString();
            String searchResultPageId = searchResult.get("pageid").getAsString();
            String searchResultSnippet = searchResult.get("snippet").getAsString();
            SearchResult sr = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
            searchOptionsMenu.add(sr);
            sr.addActionListener(actionEvent -> gameController.searchGameSelected(sr));
            gameModel.setListener(new GameSearchLisener() {
                @Override
                public void gameListFound(){}

                @Override
                public void gameFound(){processGame();}


                public void gameToSaveReady() {}

                @Override
                public void weNeedGameInfo() {

                }

                @Override
                public void updateGameHistory() {

                }
            });
        }
        searchOptionsMenu.show(gameToSearch, gameToSearch.getX(), gameToSearch.getY());
    }

    private void processGame(){
        game = gameModel.getGame();
        dataTheGame.setText(game.getTextContent());
        initListeners();
    }

    public Container getContent(){return this.searchPanel;}


    public void setWorkingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(false);
        dataTheGame.setEnabled(false);
    }
    public void setWatingStatus() {
        for(Component c: this.searchPanel.getComponents()) c.setEnabled(true);
        dataTheGame.setEnabled(true);
    }

    //Methods performed for the test

    public void setGameToSearch(String text){
        gameToSearch.setText(text);
    }

    public JsonArray getJsonResults(){
        return jsonResults;
    }

    public void pressSearchButton(){
        searchButton.doClick();
    }
}

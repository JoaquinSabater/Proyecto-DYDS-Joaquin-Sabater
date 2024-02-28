package controller;

import model.SearchResult;
import model.GameModel;
import model.Game;
import views.GameHistoryView;
import views.GameSearchMenu;
import views.GameSearchView;
import views.GameStoredView;

public interface GameController {

    void initSearch(String contentText);

    void searchGameSelected(SearchResult sr);

    void saveGame(Game game);

    void setGameSearchMenu(GameSearchMenu gameSearchMenu);

    void initSearchHistory(String contentText);

    void getInfoGameStored(String title);

    void setGameHistory(GameHistoryView gameHistoryView);

    void setGameSearch(GameSearchView gameSearchView);

    void setGameStored(GameStoredView gameStoredView);

}

package controller;



import model.Game;
import model.GameModel;
import views.GameSearchMenu;
import views.GameSearchView;
import views.GameStoredView;
import views.GameHistoryView;
import model.SearchResult;
import java.io.IOException;
import java.sql.SQLException;

public class GameControllerImpl  implements GameController{

    private GameHistoryView gameHistoryView;

    private GameSearchMenu gameSearchMenu;

    private GameSearchView gameSearchView;

    private GameStoredView gameStoredView;

    private GameModel gameModel;

    private Thread taskThread;

    public GameControllerImpl(GameModel gameModel){
        this.gameModel = gameModel;
    }

    public void initSearch(String contentText){
            taskThread = new Thread(() -> {
                try {
                    gameSearchView.setWorkingStatus();
                    gameModel.searchGames(contentText);
                    gameSearchView.setWatingStatus();
                } catch (IOException e) {
                    gameSearchMenu.error("Error de la busqueda en wikipedia");
                }
            });
            taskThread.start();
    }

    public void initSearchHistory(String contentText){
        taskThread = new Thread(() -> {
            try {
                gameSearchView.setWorkingStatus();
                gameSearchMenu.backToSearch();
                gameModel.searchGames(contentText);
                gameSearchView.setWatingStatus();
            } catch (IOException e) {
                gameSearchMenu.error("Error de la busqueda en wikipedia");
            }
        });
        taskThread.start();
    }

    @Override
    public void searchGameSelected(SearchResult sr) {
        taskThread = new Thread(() -> {
            try {
                gameSearchView.setWorkingStatus();
                gameModel.searchGameSelected(sr);
                gameSearchView.setWatingStatus();
            } catch (IOException | SQLException e) {
                gameSearchMenu.error("Error de la busqueda en wikipedia");
            }
        });
        taskThread.start();
    }

    @Override
    public void saveGame(Game game) {
        taskThread = new Thread(() -> {
            try {
                gameSearchView.setWorkingStatus();
                gameModel.saveGameInDB(game);
                gameSearchView.setWatingStatus();
            } catch (SQLException e) {
                gameSearchMenu.error("Error en la base de datos");
            }
        });
        taskThread.start();
    }

    @Override
    public void getInfoGameStored(String title) {
        taskThread = new Thread(() -> {
            try {
                gameModel.getInfoGameSaveInDB(title);
            } catch (SQLException e) {
                gameSearchMenu.error("Error en la base de datos");
            }
        });
        taskThread.start();
    }

    @Override
    public void setGameHistory(GameHistoryView gameHistoryView) {this.gameHistoryView = gameHistoryView;}

    public void setGameSearchMenu(GameSearchMenu gameSearchMenu){this.gameSearchMenu = gameSearchMenu;}

    @Override
    public void setGameSearch(GameSearchView gameSearchView) {this.gameSearchView = gameSearchView;}

    @Override
    public void setGameStored(GameStoredView gameStoredView) {this.gameStoredView = gameStoredView;}

}

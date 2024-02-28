package integrationTest;

import model.GameSearchLisener;

public class GameSearchLisenerDummy implements GameSearchLisener {
    boolean listFound = false;

    boolean gameFound = false;

    boolean updateGameHistory = false;

    boolean gameToSaveReady = false;

    boolean weNeedInfo = false;
    @Override
    public void gameListFound() {
        listFound = true;
    }

    @Override
    public void gameFound() {
        gameFound = true;
    }

    @Override
    public void gameToSaveReady() {
        gameToSaveReady= true;
    }

    @Override
    public void weNeedGameInfo() {
        weNeedInfo = true;
    }

    @Override
    public void searchedGameList() {

    }

    @Override
    public void updateGameHistory() {
        updateGameHistory = true;
    }
}

package model;

public interface GameSearchLisener {
    void gameListFound();

    void gameFound();

    void gameToSaveReady();

    void weNeedGameInfo();

    void updateGameHistory();
}

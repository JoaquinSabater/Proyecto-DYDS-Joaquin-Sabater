package UnitTest;

import org.junit.Before;
import org.junit.Test;
import model.*;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SercherTest {

    GameModel gameModel;

    GameSearchLisenerDummy lisenerDummy;

    GameSearchLisenerDummy lisenerDummyHistory;

    GameSearchLisenerDummy lisenerDummyStored;

    GsonLogicDummy gsonLogic;

    DataBaseDummy dataBaseDummy;

    @Before
    public void setUp() throws Exception {
        gsonLogic = new GsonLogicDummy();
        dataBaseDummy = new DataBaseDummy();
        gameModel = new GameModelImpl(gsonLogic,dataBaseDummy);
        lisenerDummy = new GameSearchLisenerDummy();
        lisenerDummyHistory = new GameSearchLisenerDummy();
        lisenerDummyStored = new GameSearchLisenerDummy();
    }

    //Testing SearchGames attributes

    @Test
    public void testSearchGamesListener() throws IOException {
        gameModel.setListener(lisenerDummy);
        gameModel.searchGames("Hola como estas");
        assertTrue(lisenerDummy.listFound);
    }

    @Test
    public void testSearchGamesJson() throws IOException {
        gameModel.setListener(lisenerDummy);
        gameModel.searchGames("God of war");
        assertTrue(gsonLogic.searchListInWiki);
    }

    //Testing SearchGamesSelected attributes

    @Test
    public void testSearchGameSelectedGame() throws SQLException, IOException {
        gameModel.setListener(lisenerDummy);
        gameModel.setListenerHistory(lisenerDummyHistory);
        SearchResult sr = new SearchResult("hola","como","estas");
        gameModel.searchGameSelected(sr);
        assertTrue(gameModel.getGame().getTitle() == "Test");
    }

    @Test
    public void testSearchGameSelectedInsertInHistory() throws SQLException, IOException {
        gameModel.setListener(lisenerDummy);
        gameModel.setListenerHistory(lisenerDummyHistory);
        SearchResult sr = new SearchResult("hola","como","estas");
        gameModel.searchGameSelected(sr);
        gameModel.getHistory();
        assertTrue(gameModel.getHistoryList().get(0) == "Test");
    }

    @Test
    public void testSearchGameSelectedMyListener() throws SQLException, IOException {
        gameModel.setListener(lisenerDummy);
        gameModel.setListenerHistory(lisenerDummyHistory);
        SearchResult sr = new SearchResult("hola","como","estas");
        gameModel.searchGameSelected(sr);
        assertTrue(lisenerDummy.gameFound);
    }

    @Test
    public void testSearchGameSelectedMyListenerHistory() throws SQLException, IOException {
        gameModel.setListener(lisenerDummy);
        gameModel.setListenerHistory(lisenerDummyHistory);
        SearchResult sr = new SearchResult("hola","como","estas");
        gameModel.searchGameSelected(sr);
        assertTrue(lisenerDummyHistory.updateGameHistory);
    }

    //Testing SaveGameInDB attributes

    @Test
    public void testSaveGameInDBconecction() throws SQLException {
        gameModel.setListenerStored(lisenerDummyStored);
        Game game = new Game();
        gameModel.saveGameInDB(game);
        assertTrue(dataBaseDummy.gameSaved);
    }

    @Test
    public void testSaveGameInDBMyListenerStored() throws SQLException {
        Game game = new Game();
        gameModel.setListenerStored(lisenerDummyStored);
        gameModel.saveGameInDB(game);
        assertTrue(lisenerDummyStored.gameToSaveReady);
    }

    //Testing getInfoGameSaveInDB attributes

    @Test
    public void testGetInfoGameSaveInDBInfoOfTehGame() throws SQLException {
        gameModel.setListenerStoredToGetText(lisenerDummyStored);
        gameModel.getInfoGameSaveInDB("Hola");
        String spectedResult = "Chau";
        assertEquals(spectedResult,gameModel.getTitleInfo());
    }

    @Test
    public void testGetInfoGameSaveInDBmyListenerStoredToGetText() throws SQLException {
        gameModel.setListenerStoredToGetText(lisenerDummyStored);
        gameModel.getInfoGameSaveInDB("Hola");
        assertTrue(lisenerDummyStored.weNeedInfo);
    }

}

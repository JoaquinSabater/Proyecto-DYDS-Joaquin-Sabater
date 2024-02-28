package integrationTest;

import com.google.gson.JsonArray;
import org.junit.Before;
import org.junit.Test;
import controller.*;
import model.*;
import views.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class SearchTestIntegration {

    public static long timeBase = 1;

    GsonLogic gsonLogic;

    DataBase db;

    GameModel model;

    GameController controller;

    GamesSearchViewImpl view;

    GameStoredViewImpl stored;

    GameHistoryViewImpl history;

    @Before
    public void setUp() throws Exception {
        gsonLogic = new GsonLogicDummy();

        db = new DataBaseDummy();

        model = new GameModelImpl(gsonLogic,db);

        controller = new GameControllerImpl(model);

        view = new GamesSearchViewImpl(model,controller);

        stored = new GameStoredViewImpl(model,controller);

        history = new GameHistoryViewImpl(model,controller);

        GameSearchMenu menu = new GameSearchMenu(view,stored,history);

        controller.setGameSearch(view);
        controller.setGameStored(stored);
        controller.setGameHistory(history);
        controller.setGameSearchMenu(menu);
    }

    //Integration tests regarding the stored view
    @Test
    public void testFullComboBoxAtTheBeginning(){
        ArrayList<String> expectedResult = new ArrayList<String>();
        expectedResult.add("God of War");
        expectedResult.add("Suikoden");
        ArrayList<String> result = stored.getTitles();
        assertEquals(expectedResult,result);
    }

    @Test
    public void testGetInfoForTheDataBase(){
        stored.selectTheFirstInTheComboBox();
        waitForTask();
        String expectedResult = "info";
        assertEquals(expectedResult,stored.getDataOfTheGame());
    }

    //Integration tests regarding the History view
    @Test
    public void testHistoryFillComboBox(){
        ArrayList<String> expectedResult = new ArrayList<String>();
        expectedResult.add("God of War");
        expectedResult.add("Suikoden");
        ArrayList<String> Result = history.getHistoryInView();
        assertEquals(expectedResult,Result);
    }

    void waitForTask(){
        try {
            Thread.sleep(timeBase * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

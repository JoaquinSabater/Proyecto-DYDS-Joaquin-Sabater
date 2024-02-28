package UnitTest;

import model.DataBase;
import model.Game;

import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseDummy extends DataBase {

    ArrayList<String> Fakehistory;

    boolean gameSaved = false;

    @Override
    public void insertGameIntoHistory(String name) throws SQLException {
        Fakehistory = new ArrayList<String>();
        Fakehistory.add("Test");
    }

    @Override
    public ArrayList<String> getHistory() throws SQLException {
        return Fakehistory;
    }

    @Override
    public void saveGameInDB(Game game) throws SQLException {
        gameSaved = true;
    }

    @Override
    public String getInfoGameSaveInDB(String title) throws SQLException {
        if(title == "Hola"){
            return "Chau";
        }
        return null;
    }
}

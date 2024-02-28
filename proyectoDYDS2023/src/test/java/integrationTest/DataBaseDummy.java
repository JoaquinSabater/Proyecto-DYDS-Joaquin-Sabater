package integrationTest;

import model.DataBase;
import model.Game;

import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseDummy extends DataBase {

    @Override
    public void insertGameIntoHistory(String name) throws SQLException {
        super.insertGameIntoHistory(name);
    }

    @Override
    public ArrayList<String> getHistory() throws SQLException {
        ArrayList<String> fakeHistory = new ArrayList<String>();
        fakeHistory.add("God of War");
        fakeHistory.add("Suikoden");
        return fakeHistory;
    }

    @Override
    public void saveGameInDB(Game game) throws SQLException {
        super.saveGameInDB(game);
    }

    @Override
    public String getInfoGameSaveInDB(String title) throws SQLException {
        if(title == "God of War"){
            return "info";
        }
        return null;
    }

    @Override
    public ArrayList<String> getGamesSaveInDB() throws SQLException {
        ArrayList<String> games = new ArrayList<>();
        games.add("God of War");
        games.add("Suikoden");
        return games;
    }

}

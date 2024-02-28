package model;

import com.google.gson.JsonArray;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    private ArrayList<String> titles;

    private ArrayList<String> history;

    private GsonLogic gsonLogic;

    public DataBase(){
        titles = new ArrayList<>();
        history = new ArrayList<>();
        String url = "jdbc:sqlite:./dictionary.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.
                statement.executeUpdate("create table catalog (id INTEGER, title string PRIMARY KEY, extract string, source integer)");
                statement.executeUpdate("create table history (fecha Date,title String,PRIMARY KEY(fecha,title));");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertGameIntoHistory(String name) throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        java.util.Date date = new java.util.Date();
        java.sql.Date sqldate = new java.sql.Date(date.getTime());
        statement.executeUpdate("replace into history (fecha,title) values ('"+sqldate+"','"+name+"')");
        history.add("["+sqldate+"] "+name+" ");

        connection.close();
    }


    public ArrayList<String> getHistory() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        ResultSet rs = statement.executeQuery("select * from history");
        rs.next();
        while(rs.next()) {
            history.add("["+rs.getString("fecha")+"] "+rs.getString("title")+" ");
        }

        connection.close();

        return history;
    }

    public void saveGameInDB(Game game) throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        statement.executeUpdate("replace into catalog values(null, '"+ game.getTitle() + "', '"+ game.getTextContent() + "', 1)");
        getGamesSaveInDB();

        connection.close();
    }

    public String getInfoGameSaveInDB(String title) throws SQLException {
        String infoOfTheGame;
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        ResultSet rs = statement.executeQuery("select * from catalog WHERE title = '" + title + "'" );
        rs.next();
        infoOfTheGame = gsonLogic.textToHtml(rs.getString("extract"));

        connection.close();

        return infoOfTheGame;
    }

    public ArrayList<String> getGamesSaveInDB() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        titles.clear();
        ResultSet rs = statement.executeQuery("select * from catalog");
        while(rs.next()) {titles.add(rs.getString("title"));}

        connection.close();

        return titles;
    }

}

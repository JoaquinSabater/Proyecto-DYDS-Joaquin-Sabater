package controller;

import model.DataBase;
import model.GameModel;
import model.GameModelImpl;
import model.GsonLogic;
import views.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GsonLogic gsonLogic = new GsonLogic();

        DataBase db = new DataBase();

        GameModel model = new GameModelImpl(gsonLogic,db);

        GameController controller = new GameControllerImpl(model);

        GameSearchView view = new GamesSearchViewImpl(model,controller);

        GameStoredView stored = new GameStoredViewImpl(model,controller);

        GameHistoryView history = new GameHistoryViewImpl(model,controller);

        GameSearchMenu menu = new GameSearchMenu(view,stored,history);

        controller.setGameSearch(view);
        controller.setGameStored(stored);
        controller.setGameHistory(history);
        controller.setGameSearchMenu(menu);

        JFrame frame = new JFrame("Video Game Info");
        frame.setContentPane(menu.getPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

package views;

import controller.GameController;
import model.Game;
import model.GameModel;
import model.GameSearchLisener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameStoredViewImpl implements GameStoredView{
    private JPanel StoredPanel;
    private JComboBox storedComboBox;
    private JTextPane dataGameSaved;

    private GameModel gameModel;

    private GameController gameController;

    ArrayList<String> titles;

    String dataOfTheGame;

    public GameStoredViewImpl(GameModel gameModel,GameController gameController){
        dataGameSaved.setContentType("text/html");
        this.gameModel = gameModel;
        this.gameController = gameController;
        titles = gameModel.getListOfTitles();
        storedComboBox.setModel(new DefaultComboBoxModel(titles.stream().sorted().toArray()));
        inilisener();
    }

    void inilisener(){
        gameModel.setListenerStored(new GameSearchLisener() {
            @Override
            public void gameListFound(){}

            @Override
            public void gameFound() {}

            @Override
            public void gameToSaveReady(){fillComboBox();}

            @Override
            public void weNeedGameInfo() {}

            @Override
            public void updateGameHistory() {

            }
        });
        storedComboBox.addActionListener(actionEvent -> gameController.getInfoGameStored(storedComboBox.getSelectedItem().toString()));
        gameModel.setListenerStoredToGetText(new GameSearchLisener() {
            @Override
            public void gameListFound(){}

            @Override
            public void gameFound() {}

            @Override
            public void gameToSaveReady(){}

            @Override
            public void weNeedGameInfo() {infoOfTheGameSave();}


            @Override
            public void updateGameHistory() {}
        });
    }

    private void infoOfTheGameSave(){
        dataOfTheGame = gameModel.getTitleInfo();
        dataGameSaved.setText(dataOfTheGame);
    }

    private void fillComboBox(){
        titles = gameModel.getListOfTitles();
        storedComboBox.setModel(new DefaultComboBoxModel(titles.stream().sorted().toArray()));
    }

    public Container getContent(){return this.StoredPanel;}

    public void setWorkingStatus() {
        for(Component c: this.StoredPanel.getComponents()) c.setEnabled(false);
        dataGameSaved.setEnabled(false);
    }
    public void setWatingStatus() {
        for(Component c: this.StoredPanel.getComponents()) c.setEnabled(true);
        dataGameSaved.setEnabled(true);
    }

    //Methods performed for the test

    public ArrayList<String> getTitles(){
        return titles;
    }

    public void selectTheFirstInTheComboBox(){
        storedComboBox.setSelectedIndex(0);
    }

    public String getDataOfTheGame(){
        return dataOfTheGame;
    }

}

package views;

import controller.GameController;
import model.GameModel;
import model.GameSearchLisener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameHistoryViewImpl  implements GameHistoryView {
    private JPanel historyPanel;
    private JComboBox historyComboBox;

    ArrayList<String> history;

    String gameSelected;

    GameModel gameModel;

    GameController gameController;

    public GameHistoryViewImpl(GameModel gameModel, GameController gameController){
        this.gameModel = gameModel;
        this.gameController = gameController;
        fillHistoryComboBox();
    }

    private void initLiseners(){
        gameModel.setListenerHistory(new GameSearchLisener() {
            @Override
            public void gameListFound(){}

            @Override
            public void gameFound() {}

            @Override
            public void gameToSaveReady() {}

            @Override
            public void weNeedGameInfo() {}

            @Override
            public void updateGameHistory() {fillHistoryComboBox();}
        });
    }

    private void fillHistoryComboBox(){
        history = new ArrayList<String>();
        history = gameModel.getHistoryList();
        historyComboBox.setModel(new DefaultComboBoxModel(history.stream().sorted().toArray()));
        historyComboBox.addActionListener(actionEvent -> gameController.initSearchHistory(borrarContenidoParentesis(historyComboBox.getSelectedItem().toString())));
        initLiseners();
    }

    private static String borrarContenidoParentesis(String texto) {
        StringBuilder sb = new StringBuilder();
        boolean dentroParentesis = false;
        boolean espacioDespuesDeParentesis = false;
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c == '[') {
                dentroParentesis = true;
                continue;
            }
            if (c == ']') {
                dentroParentesis = false;
                espacioDespuesDeParentesis = true;
                continue;
            }
            if (!dentroParentesis) {
                if (espacioDespuesDeParentesis && c == ' ') {
                    continue;
                }
                sb.append(c);
                espacioDespuesDeParentesis = false;
            }
        }
        return sb.toString();
    }

    @Override
    public Container getContent() {
        return historyPanel;
    }

    //Methods performed for the test

    public ArrayList<String> getHistoryInView(){
        return history;
    }

}

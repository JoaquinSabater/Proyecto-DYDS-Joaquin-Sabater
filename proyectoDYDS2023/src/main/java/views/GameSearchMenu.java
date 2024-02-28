package views;

import javax.swing.*;
import java.awt.*;

public class GameSearchMenu {
    private JTabbedPane menu;
    private JPanel Panel;
    private JPanel MenuPanel;

    GameSearchView view;

    public GameSearchMenu(GameSearchView view,GameStoredView stored,GameHistoryView history){
        menu.remove(0);
        this.view = view;
        menu.add("Search Games",view.getContent());
        menu.add("Stored Games",stored.getContent());
        menu.add("History Games",history.getContent());
        menu.setSelectedIndex(0);
    }

    public void backToSearch(){
        menu.setSelectedIndex(0);
    }

    public Container getPanel() {
        return MenuPanel;
    }

    public void error(String error){
        JOptionPane.showMessageDialog(MenuPanel,error);
    }
}

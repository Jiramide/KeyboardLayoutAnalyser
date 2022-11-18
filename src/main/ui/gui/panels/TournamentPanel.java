package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import model.Tournament;
import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

public class TournamentPanel extends JPanel {

    private MainWindow parent;
    private BoxLayout boxLayout;
    private List<Tournament> tournaments = new ArrayList<>();

    private JPanel header;
    private JList<Tournament> tournamentJList;
    private JPanel interactionButtons;

    public TournamentPanel(MainWindow parent) {
        super();

        this.parent = parent;
        boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);

        createHeader();
        createList();
        createInteractionButtons();
        layoutComponents();
    }

    private void createInteractionButtons() {

    }

    private void createList() {

    }

    private void layoutComponents() {
        add(header);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void createHeader() {
        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        JButton backButton = parent.createNavigationButton(Page.Selection);
        backButton.setText("<");

        JLabel title = new JLabel();
        title.setText("🏆  Tournament");

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(title);
        header.add(Box.createHorizontalGlue());

        header.setVisible(true);
    }
}

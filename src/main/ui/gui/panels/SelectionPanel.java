package ui.gui.panels;

import java.awt.*;
import javax.swing.*;

import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

/*
 * A class representing the page main navigation page
 */
public class SelectionPanel extends JPanel {

    private MainWindow parent;

    private BoxLayout boxLayout;

    private JButton corporaButton;
    private JButton keyboardButton;
    private JButton tournamentButton;

    // EFFECTS: creates the panel for the main navigation page
    public SelectionPanel(MainWindow parent) {
        super();

        this.parent = parent;

        boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        setLayout(boxLayout);

        createNavigationButtons();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the navigation buttons leading to the three main pages (Corpora, Keyboard, Tournament)
    //          and adds them into the panel
    private void createNavigationButtons() {
        corporaButton = parent.createNavigationButton(Page.CorporaMain);
        corporaButton.setText("Corpora");
        corporaButton.setVisible(true);
        add(corporaButton);

        keyboardButton = parent.createNavigationButton(Page.KeyboardMain);
        keyboardButton.setText("Keyboard");
        keyboardButton.setVisible(true);
        add(keyboardButton);

        tournamentButton = parent.createNavigationButton(Page.Tournament);
        tournamentButton.setText("Tournament");
        tournamentButton.setVisible(true);
        add(tournamentButton);
    }


}

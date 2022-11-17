package ui.gui.panels;

import java.awt.*;
import javax.swing.*;

import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

/*
 * A class representing the page main navigation page
 */
public class SelectionPanel extends JPanel {

    private static final Dimension SELECTION_BUTTON_SPACING = new Dimension(0, 10);

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
        setVisible(true);
        createNavigationButtons();
        layoutComponents();
    }

    // MODIFIES: this
    // EFFECTS: adds the components in proper order with spacing elements to provide a bit of style
    private void layoutComponents() {
        add(Box.createVerticalGlue());
        add(corporaButton);
        add(Box.createRigidArea(SELECTION_BUTTON_SPACING));
        add(keyboardButton);
        add(Box.createRigidArea(SELECTION_BUTTON_SPACING));
        add(tournamentButton);
        add(Box.createVerticalGlue());
    }

    // MODIFIES: this
    // EFFECTS: creates the navigation buttons leading to the three main pages (Corpora, Keyboard, Tournament)
    //          and adds them into the panel
    private void createNavigationButtons() {
        corporaButton = parent.createNavigationButton(Page.CorporaMain);
        corporaButton.setText("📕    Corpora");
        corporaButton.setVisible(true);
        corporaButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        keyboardButton = parent.createNavigationButton(Page.KeyboardMain);
        keyboardButton.setText("⌨    Keyboard");
        keyboardButton.setVisible(true);
        keyboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        tournamentButton = parent.createNavigationButton(Page.Tournament);
        tournamentButton.setText("🏆    Tournament");
        tournamentButton.setVisible(true);
        tournamentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }


}

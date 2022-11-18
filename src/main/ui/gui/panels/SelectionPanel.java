package ui.gui.panels;

import java.awt.*;
import javax.swing.*;

import ui.gui.App;
import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

/*
 * A class representing the page main navigation page
 */
public class SelectionPanel extends JPanel {

    private static final Dimension SELECTION_BUTTON_SPACING = new Dimension(0, 10);

    private App app;

    private BoxLayout boxLayout;

    private JComponent title;
    private JButton corporaButton;
    private JButton keyboardButton;
    private JButton tournamentButton;

    // EFFECTS: creates the panel for the main navigation page
    public SelectionPanel(App app) {
        super();

        this.app = app;
        boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);

        setLayout(boxLayout);
        setVisible(true);
        createTitle();
        createNavigationButtons();
        layoutComponents();
    }

    // MODIFIES: this
    // EFFECTS: adds the components in proper order with spacing elements to provide a bit of style
    private void layoutComponents() {
        add(Box.createVerticalGlue());
        add(title);
        add(Box.createRigidArea(SELECTION_BUTTON_SPACING));
        add(corporaButton);
        add(Box.createRigidArea(SELECTION_BUTTON_SPACING));
        add(keyboardButton);
        add(Box.createRigidArea(SELECTION_BUTTON_SPACING));
        add(tournamentButton);
        add(Box.createVerticalGlue());
    }

    // MODIFIES: this
    // EFFECTS: creates the title, which contains an image icon on top of a title label
    private void createTitle() {
        title = new JPanel();
        title.setLayout(new BoxLayout(title, BoxLayout.PAGE_AXIS));

        JLabel titlePhoto = new JLabel(MainWindow.ICON);
        titlePhoto.setVisible(true);
        titlePhoto.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(MainWindow.TITLE);
        titleLabel.setVisible(true);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        title.add(titlePhoto);
        title.add(titleLabel);
    }

    // MODIFIES: this
    // EFFECTS: creates the navigation buttons leading to the three main pages (Corpora, Keyboard, Tournament)
    //          and adds them into the panel
    private void createNavigationButtons() {
        MainWindow mainWindow = app.getMainWindow();

        corporaButton = mainWindow.createNavigationButton(Page.CorporaMain);
        corporaButton.setText("📕    Corpora");
        corporaButton.setVisible(true);
        corporaButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        keyboardButton = mainWindow.createNavigationButton(Page.KeyboardMain);
        keyboardButton.setText("⌨    Keyboard");
        keyboardButton.setVisible(true);
        keyboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        tournamentButton = mainWindow.createNavigationButton(Page.Tournament);
        tournamentButton.setText("🏆    Tournament");
        tournamentButton.setVisible(true);
        tournamentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}

package ui.gui.panels;

import java.awt.*;
import javax.swing.*;

import ui.gui.App;
import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

public class KeyboardMainPanel extends JPanel {

    private static final Dimension SELECTION_BUTTON_SPACING = new Dimension(0, 10);

    private App app;

    private BoxLayout boxLayout;

    private JComponent header;
    private JButton layoutButton;
    private JButton geometryButton;

    // EFFECTS: creates the panel for the main navigation page
    public KeyboardMainPanel(App app) {
        super();

        this.app = app;
        boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);

        setLayout(boxLayout);
        setVisible(true);
        createHeader();
        createNavigationButtons();
        layoutComponents();
    }

    // MODIFIES: this
    // EFFECTS: adds the components in proper order with spacing elements to provide a bit of style
    private void layoutComponents() {
        add(header);
        add(Box.createVerticalGlue());
        add(layoutButton);
        add(Box.createRigidArea(SELECTION_BUTTON_SPACING));
        add(geometryButton);
        add(Box.createVerticalGlue());
    }

    // MODIFIES: this
    // EFFECTS: creates the title, which contains an image icon on top of a title label
    private void createHeader() {
        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        JButton backButton = app.getMainWindow().createNavigationButton(Page.Selection);
        backButton.setText("<");

        JLabel titleLabel = new JLabel("⌨  Keyboard");
        titleLabel.setVisible(true);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(titleLabel);
        header.add(Box.createHorizontalGlue());
    }

    // MODIFIES: this
    // EFFECTS: creates the navigation buttons leading to the three main pages (KeyboardGeometry, Layout)
    //          and adds them into the panel
    private void createNavigationButtons() {
        MainWindow mainWindow = app.getMainWindow();

        layoutButton = mainWindow.createNavigationButton(Page.LayoutMain);
        layoutButton.setText("📕    Layout");
        layoutButton.setVisible(true);
        layoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        geometryButton = mainWindow.createNavigationButton(Page.KeyboardGeometryMain);
        geometryButton.setText("⌨    Geometry");
        geometryButton.setVisible(true);
        geometryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}

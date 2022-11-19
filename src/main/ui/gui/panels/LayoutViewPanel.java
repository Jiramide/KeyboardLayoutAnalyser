package ui.gui.panels;

import model.Layout;
import ui.gui.App;

import javax.swing.*;
import java.awt.*;

import static ui.gui.MainWindow.Page;

public class LayoutViewPanel extends JPanel {

    private App app;

    private LayoutMain parent;
    private JButton backButton;
    private JLabel layoutName;
    private JTextArea layoutContent;

    // EFFECTS: creates a LayoutViewPanel that's parented to a LayoutMainPanel
    public LayoutViewPanel(App app, LayoutMain parent) {
        super();

        this.app = app;
        this.parent = parent;


        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setUpHeader();
        add(Box.createRigidArea(new Dimension(0, 5)));
        setUpContent();
        add(Box.createRigidArea(new Dimension(0, 5)));
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the header that contains the back button and the layout's name
    private void setUpHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        backButton = app.getMainWindow().createNavigationButton(Page.LayoutMain);
        backButton.setText("<");

        layoutName = new JLabel();

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(5, 0)));
        header.add(layoutName);
        header.add(Box.createHorizontalGlue());

        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(header);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for listing out the content of a layout, may be truncated
    private void setUpContent() {
        layoutContent = new JTextArea();
        layoutContent.setLineWrap(true);
        layoutContent.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane contentScrollPane = new JScrollPane(layoutContent);
        contentScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentScrollPane.setVisible(true);

        add(contentScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: sets the layout and sets up the panel to show this current layout
    public void setLayout(Layout layout) {
        layoutName.setText("📕  " + layout.getName());
        layoutContent.setText(layout.getLayoutString());
    }
}

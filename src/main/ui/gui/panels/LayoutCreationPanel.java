package ui.gui.panels;

import model.Layout;
import ui.gui.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.gui.MainWindow.Page;

/*
 * A class representing the panel where you can create layouts
 */
public class LayoutCreationPanel extends JPanel {

    private App app;
    private LayoutMainPanel parent;

    private JPanel header;
    private JButton backButton;
    private JLabel title;
    private JPanel metadata;
    private JPanel layoutNameLine;
    private JLabel corpusNameTitle;
    private JTextField layoutName;
    private JScrollPane layoutContentScroll;
    private JTextArea layoutContent;
    private JPanel interactionButtons;
    private JButton cancelButton;
    private JButton createButton;

    // EFFECTS: creates a LayoutCreationPanel with the given parent
    public LayoutCreationPanel(App app, LayoutMainPanel parent) {
        super();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.app = app;
        this.parent = parent;

        setUpHeader();
        setUpMetadataInput();
        setUpLayoutContent();
        setUpInteractionButtons();
        layoutComponents();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: clears the all data inputted into the panel
    public void clear() {
        layoutName.setText("");
        layoutContent.setText("");
    }

    // MODIFIES: this
    // EFFECTS: layouts out the components in their proper positions
    private void layoutComponents() {
        add(header);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(metadata);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(layoutContentScroll);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(interactionButtons);
    }

    // MODIFIES: this
    // EFFECTS: sets up the header containing the back button and the title
    private void setUpHeader() {
        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        backButton = app.getMainWindow().createNavigationButton(Page.LayoutMain);
        backButton.setText("<");

        title = new JLabel("  Create layout");
        title.setVisible(true);

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(title);

        header.setVisible(true);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: sets up the metadata fields
    private void setUpMetadataInput() {
        metadata = new JPanel();
        metadata.setLayout(new BoxLayout(metadata, BoxLayout.PAGE_AXIS));

        layoutNameLine = new JPanel();
        layoutNameLine.setLayout(new BoxLayout(layoutNameLine, BoxLayout.LINE_AXIS));

        corpusNameTitle = new JLabel("Name: ");
        corpusNameTitle.setVisible(true);

        layoutName = new JTextField();
        // BoxLayout does not obey preferredSize's height if you're working in a LINE_AXIS context,
        // so the next best thing is to set the maximum size to cap the height, while making the
        // width stupidly large so that it (realistically) doesn't get affected by this capping.
        // 1 pt (font) is said to be 0.75 pixels, so the computation fontSize / 0.75 works to get the
        // pixels required to represent this font. An additional 5 pixels is added in order to ensure
        // that all letters get reasonably displayed (i.e. those that hang below like j)
        layoutName.setMaximumSize(new Dimension(999999999, (int) (layoutName.getFont().getSize() / 0.75) + 5));

        layoutNameLine.add(corpusNameTitle);
        layoutNameLine.add(Box.createRigidArea(new Dimension(10, 0)));
        layoutNameLine.add(layoutName);

        layoutNameLine.setVisible(true);
        layoutNameLine.setAlignmentX(Component.LEFT_ALIGNMENT);

        metadata.add(layoutNameLine);
        metadata.setVisible(true);
        metadata.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: sets up the layout content fields
    private void setUpLayoutContent() {
        layoutContent = new JTextArea();
        layoutContent.setVisible(true);

        layoutContentScroll = new JScrollPane(layoutContent);
        layoutContentScroll.setVisible(true);
        layoutContentScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: sets up the interaction buttons
    private void setUpInteractionButtons() {
        interactionButtons = new JPanel();
        interactionButtons.setLayout(new BoxLayout(interactionButtons, BoxLayout.LINE_AXIS));

        cancelButton = createCancelButton();
        createButton = createCreateButton();

        interactionButtons.add(Box.createHorizontalGlue());
        interactionButtons.add(cancelButton);
        interactionButtons.add(createButton);

        interactionButtons.setVisible(true);
        interactionButtons.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // EFFECTS: creates the cancel button
    private JButton createCancelButton() {
        cancelButton = app.getMainWindow().createNavigationButton(Page.LayoutMain);
        cancelButton.setText("Cancel");

        cancelButton.addActionListener(new ActionListener() {
            // MODIFIES: this
            // EFFECTS: clears all input fields
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        return cancelButton;
    }

    // EFFECTS: creates the create button
    private JButton createCreateButton() {
        createButton = app.getMainWindow().createNavigationButton(Page.LayoutMain);
        createButton.setText("Create");

        LayoutCreationPanel panel = this;

        createButton.addActionListener(new ActionListener() {
            // MODIFIES: this, parent
            // EFFECTS: creates the layout and inserts it into the list, or shows an error dialog if
            //          one of the fields is empty
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = layoutName.getText();
                String content = layoutContent.getText();

                if (name.trim().equals("") || content.trim().equals("")) {
                    JOptionPane.showMessageDialog(
                            panel,
                            "Name or content can't be blank!",
                            "Error creating layout",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                parent.addLayout(new Layout(name, "", content));
                clear();
            }
        });

        return createButton;
    }
}

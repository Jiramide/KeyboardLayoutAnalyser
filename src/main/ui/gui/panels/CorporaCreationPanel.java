package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.corpora.*;
import ui.gui.App;

import static ui.gui.MainWindow.Page;

public class CorporaCreationPanel extends JPanel {

    private App app;
    private CorporaMainPanel parent;

    private JPanel header;
    private JButton backButton;
    private JLabel title;
    private JPanel metadata;
    private JPanel corpusNameLine;
    private JLabel corpusNameTitle;
    private JTextField corpusName;
    private JScrollPane corpusContentScroll;
    private JTextArea corpusContent;
    private JPanel interactionButtons;
    private JButton cancelButton;
    private JButton createButton;

    // EFFECTS: creates a CorporaCreationPanel with the given parent
    public CorporaCreationPanel(App app, CorporaMainPanel parent) {
        super();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.app = app;
        this.parent = parent;

        setUpHeader();
        setUpMetadataInput();
        setUpCorpusContent();
        setUpInteractionButtons();
        layoutComponents();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: clears the all data inputted into the panel
    public void clear() {
        corpusName.setText("");
        corpusContent.setText("");
    }

    // MODIFIES: this
    // EFFECTS: layouts out the components in their proper positions
    private void layoutComponents() {
        add(header);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(metadata);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(corpusContentScroll);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(interactionButtons);
    }

    // MODIFIES: this
    // EFFECTS: sets up the header containing the back button and the title
    private void setUpHeader() {
        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        backButton = app.getMainWindow().createNavigationButton(Page.CorporaMain);
        backButton.setText("<");

        title = new JLabel("📔  Create corpus");
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

        corpusNameLine = new JPanel();
        corpusNameLine.setLayout(new BoxLayout(corpusNameLine, BoxLayout.LINE_AXIS));

        corpusNameTitle = new JLabel("Name: ");
        corpusNameTitle.setVisible(true);

        corpusName = new JTextField();
        // BoxLayout does not obey preferredSize's height if you're working in a LINE_AXIS context,
        // so the next best thing is to set the maximum size to cap the height, while making the
        // width stupidly large so that it (realistically) doesn't get affected by this capping.
        // 1 pt (font) is said to be 0.75 pixels, so the computation fontSize / 0.75 works to get the
        // pixels required to represent this font. An additional 5 pixels is added in order to ensure
        // that all letters get reasonably displayed (i.e. those that hang below like j)
        corpusName.setMaximumSize(new Dimension(999999999, (int) (corpusName.getFont().getSize() / 0.75) + 5));

        corpusNameLine.add(corpusNameTitle);
        corpusNameLine.add(Box.createRigidArea(new Dimension(10, 0)));
        corpusNameLine.add(corpusName);

        corpusNameLine.setVisible(true);
        corpusNameLine.setAlignmentX(Component.LEFT_ALIGNMENT);

        metadata.add(corpusNameLine);
        metadata.setVisible(true);
        metadata.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: sets up the corpus content fields
    private void setUpCorpusContent() {
        corpusContent = new JTextArea();
        corpusContent.setVisible(true);

        corpusContentScroll = new JScrollPane(corpusContent);
        corpusContentScroll.setVisible(true);
        corpusContentScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
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
        cancelButton = app.getMainWindow().createNavigationButton(Page.CorporaMain);
        cancelButton.setText("Cancel");

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        return cancelButton;
    }

    // EFFECTS: creates the create button
    private JButton createCreateButton() {
        createButton = app.getMainWindow().createNavigationButton(Page.CorporaMain);
        createButton.setText("Create");

        CorporaCreationPanel panel = this;

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = corpusName.getText();
                String content = corpusContent.getText();

                if (name.trim().equals("") || content.trim().equals("")) {
                    JOptionPane.showMessageDialog(
                            panel,
                            "Name or content can't be blank!",
                            "Error creating corpus",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                parent.addCorpus(new StringCorpus(name, "", content));
                clear();
            }
        });

        return createButton;
    }
}

package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.corpora.*;
import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

public class CorporaCreationPanel extends JPanel {

    private MainWindow mainWindow;
    private CorporaMainPanel parent;

    private JPanel header;
    private JButton backButton;
    private JLabel title;
    private JPanel metadata;
    private JTextField corpusName;
    private JScrollPane corpusContentScroll;
    private JTextArea corpusContent;
    private JPanel interactionButtons;
    private JButton cancelButton;
    private JButton createButton;

    // EFFECTS: creates a CorporaCreationPanel with the given parent
    public CorporaCreationPanel(CorporaMainPanel parent, MainWindow mainWindow) {
        super();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.mainWindow = mainWindow;
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

        backButton = mainWindow.createNavigationButton(Page.CorporaMain);
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

        JPanel corpusNameLine = new JPanel();
        corpusNameLine.setLayout(new BoxLayout(corpusNameLine, BoxLayout.LINE_AXIS));

        JLabel corpusNameTitle = new JLabel("Name: ");
        corpusNameTitle.setVisible(true);

        corpusName = new JTextField();

        corpusNameLine.add(corpusNameTitle);
        corpusNameLine.add(corpusName);

        corpusNameLine.setVisible(true);

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
        cancelButton = mainWindow.createNavigationButton(Page.CorporaMain);
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
        createButton = mainWindow.createNavigationButton(Page.CorporaMain);
        createButton.setText("Create");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = corpusName.getText();
                String content = corpusContent.getText();

                parent.addCorpus(new StringCorpus(name, "", content));
                clear();
            }
        });

        return createButton;
    }
}

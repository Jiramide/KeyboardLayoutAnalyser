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

    }

    // MODIFIES: this
    // EFFECTS: clears the all data inputted into the panel
    public void clear() {

    }

    // MODIFIES: this
    // EFFECTS: layouts out the components in their proper positions
    private void layoutComponents() {

    }

    // MODIFIES: this
    // EFFECTS: sets up the header containing the back button and the title
    private void setUpHeader() {

    }

    // MODIFIES: this
    // EFFECTS: sets up the metadata fields
    private void setUpMetadataInput() {

    }

    // MODIFIES: this
    // EFFECTS: sets up the interaction buttons
    private void setUpInteractionButtons() {

    }

    // EFFECTS: creates the cancel button
    private JButton createCancelButton() {
        return null;
    }

    // EFFECTS: creates the create button
    private JButton createCreateButton() {
        return null;
    }


}

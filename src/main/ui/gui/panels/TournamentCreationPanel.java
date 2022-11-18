package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import model.KeyboardGeometry;
import model.Layout;
import model.Tournament;
import model.corpora.Corpus;
import model.effortmodel.EffortModel;
import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

public class TournamentCreationPanel extends JPanel {

    private MainWindow mainWindow;
    private TournamentPanel parent;

    private Corpus corpus;
    private EffortModel effortModel;
    private List<KeyboardGeometry> keyboardGeometries;
    private List<Layout> layouts;

    private JPanel header;
    private JButton backButton;
    private JLabel title;
    private JPanel metadata;
    private JLabel corpusText;
    private JComboBox<String> corpusChooser;
    private JLabel effortModelText;
    private JComboBox<String> effortModelChooser;
    private JScrollPane keyboards;
    private List<KeyboardInformationPanel> keyboardsInfo;
    private JComboBox<String> keyboardChooser;
    private JComboBox<String> layoutChooser;
    private JButton addKeyboard;
    private JPanel interactionButtons;
    private JButton cancelButton;
    private JButton createButton;

    // EFFECTS: creates a TournamentCreationPanel with the parent
    public TournamentCreationPanel(TournamentPanel parent, MainWindow mainWindow) {

    }

    // MODIFIES: this
    // EFFECTS: clears all input fields
    public void clear() {

    }

    // EFFECTS: creates a tournament from the input fields
    private Tournament createTournamentFromFields() {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: layouts the components in their proper positions
    private void layoutComponents() {

    }

    // MODIFIES: this
    // EFFECTS: creates the header that contains the back button and title
    private void setUpHeader() {

    }

    // MODIFIES: this
    // EFFECTS: creates the metadata panel that asks for the corpus and effort model used
    private void setUpMetadata() {

    }

    // MODIFIES: this
    // EFFECTS: creates the keyboards list that shows all keyboards
    private void setUpKeyboards() {

    }

    // MODIFIES: this
    // EFFECTS: set up the interaction buttons that allow you to cancel or run the tournament
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

    /*
     * A class that represents an information panel about the keyboard; allows for
     * interaction buttons like removal
     */
    private static class KeyboardInformationPanel extends JPanel {

        private TournamentCreationPanel parent;
        private KeyboardGeometry geometry;
        private Layout layout;

        private JLabel geometryLabel;
        private JLabel layoutLabel;
        private JPanel interactionButtons;
        private JButton viewButton;
        private JButton removeButton;

        // EFFECTS: creates a KeyboardInformationPanel with the given geometry and layout
        public KeyboardInformationPanel(TournamentCreationPanel parent, KeyboardGeometry geometry, Layout layout) {

        }

        // MODIFIES: this
        // EFFECTS: lays out the components in their proper positions
        private void layoutComponents() {

        }

        // MODIFIES: this
        // EFFECTS: sets up the label for the geometry
        private void setUpGeometryLabel() {

        }

        // MODIFIES: this
        // EFFECTS: sets up the label for the layout
        private void setupLayoutLabel() {

        }

        // MODIFIES: this
        // EFFECTS: sets up the interaction buttons
        private void setUpInteractionButtons() {

        }

        // EFFECTS: creates the view button
        private JButton createViewButton() {
            return null;
        }

        // EFFECTS: creates the remove button
        private JButton createRemoveButton() {
            return null;
        }
    }
}

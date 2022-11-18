package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
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
    protected List<KeyboardInformationPanel> keyboardsInfo;
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
    private class KeyboardInformationPanel extends JPanel {

        private KeyboardGeometry geometry;
        private Layout layout;

        private JLabel geometryLabel;
        private JLabel layoutLabel;
        private JPanel interactionButtons;
        private JButton viewButton;
        private JButton removeButton;

        // EFFECTS: creates a KeyboardInformationPanel with the given geometry and layout
        public KeyboardInformationPanel(KeyboardGeometry geometry, Layout layout) {
            super();

            this.geometry = geometry;
            this.layout = layout;

            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

            setUpGeometryLabel();
            setupLayoutLabel();
            setUpInteractionButtons();
            setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            layoutComponents();
            setVisible(true);
        }

        // MODIFIES: this
        // EFFECTS: lays out the components in their proper positions
        private void layoutComponents() {
            add(geometryLabel);
            add(Box.createRigidArea(new Dimension(10, 0)));
            add(layoutLabel);
            add(Box.createHorizontalGlue());
            add(interactionButtons);
        }

        // MODIFIES: this
        // EFFECTS: sets up the label for the geometry
        private void setUpGeometryLabel() {
            geometryLabel = new JLabel(geometry.getName());
        }

        // MODIFIES: this
        // EFFECTS: sets up the label for the layout
        private void setupLayoutLabel() {
            layoutLabel = new JLabel(layout.getName());
        }

        // MODIFIES: this
        // EFFECTS: sets up the interaction buttons
        private void setUpInteractionButtons() {
            interactionButtons = new JPanel();
            interactionButtons.setLayout(new BoxLayout(interactionButtons, BoxLayout.LINE_AXIS));

            viewButton = createViewButton();
            removeButton = createRemoveButton();

            interactionButtons.add(viewButton);
            interactionButtons.add(Box.createRigidArea(new Dimension(10, 0)));
            interactionButtons.add(removeButton);

            interactionButtons.setVisible(true);
        }

        // EFFECTS: creates the view button
        private JButton createViewButton() {
            viewButton = new JButton("👁    View");
            viewButton.setEnabled(false);
            viewButton.setVisible(true);

            return viewButton;
        }

        // EFFECTS: creates the remove button
        private JButton createRemoveButton() {
            removeButton = new JButton("➖    Remove");
            removeButton.setVisible(true);

            KeyboardInformationPanel panel = this;

            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Iterator<KeyboardInformationPanel> iterator = keyboardsInfo.listIterator();

                    while (iterator.hasNext()) {
                        KeyboardInformationPanel next = iterator.next();

                        if (panel == next) {
                            iterator.remove();
                            keyboards.remove(panel);

                            break;
                        }
                    }

                    removeButton.setEnabled(false);
                }
            });

            return removeButton;
        }
    }
}

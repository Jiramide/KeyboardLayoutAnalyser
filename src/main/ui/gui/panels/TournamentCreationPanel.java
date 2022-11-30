package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.*;
import model.corpora.Corpus;
import model.effortmodel.EffortModel;
import ui.gui.App;

import static ui.gui.MainWindow.Page;

/*
 * A class representing the panel where you can create tournaments
 */
public class TournamentCreationPanel extends JPanel {

    private App app;
    private TournamentPanel parent;

    private Tournament currentTournament;

    private Corpus corpus;
    private EffortModel effortModel;

    private String[] corporaChoices;
    private String[] effortModelChoices;
    private String[] keyboardGeometryChoices;
    private String[] layoutChoices;

    private Dimension comboBoxSize;
    private JPanel header;
    private JButton backButton;
    private JLabel title;
    private JPanel metadata;
    private JComboBox<String> corpusChooser;
    private JComboBox<String> effortModelChooser;
    private JScrollPane keyboards;
    private JPanel keyboardsPanel;
    protected List<KeyboardInformationPanel> keyboardsInfo;
    private JPanel additionFields;
    private KeyboardGeometry currentGeometry;
    private JComboBox<String> keyboardChooser;
    private Layout currentLayout;
    private JComboBox<String> layoutChooser;
    private JButton addKeyboardButton;
    private JPanel interactionButtons;
    private JButton cancelButton;
    private JButton createButton;

    // EFFECTS: creates a TournamentCreationPanel with the parent
    public TournamentCreationPanel(App app, TournamentPanel parent) {
        super();

        this.app = app;
        this.parent = parent;
        keyboardsInfo = new ArrayList<>();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        createCorporaChoices();
        createEffortModelChoices();
        createKeyboardGeometryChoices();
        createLayoutChoices();
        setUpCorpusChooser();
        comboBoxSize = new Dimension(80000, (int) (12 / 0.75 + 5));
        setUpEffortModelChooser();
        setUpKeyboardGeometryChooser();
        setUpLayoutChooser();
        setUpHeader();
        setUpMetadata();
        setUpKeyboards();
        setUpInteractionButtons();
        layoutComponents();
    }

    // MODIFIES: this
    // EFFECTS: clears all input fields
    public void clear() {
        createCorporaChoices();
        createEffortModelChoices();
        createKeyboardGeometryChoices();
        createLayoutChoices();

        corpusChooser.setModel(new DefaultComboBoxModel<>(corporaChoices));
        corpusChooser.setSelectedIndex(-1);

        effortModelChooser.setModel(new DefaultComboBoxModel<>(effortModelChoices));
        effortModelChooser.setSelectedIndex(-1);

        keyboardChooser.setModel(new DefaultComboBoxModel<>(keyboardGeometryChoices));
        keyboardChooser.setSelectedIndex(-1);

        layoutChooser.setModel(new DefaultComboBoxModel<>(layoutChoices));
        layoutChooser.setSelectedIndex(-1);

        for (KeyboardInformationPanel keyboardInformationPanel : keyboardsInfo) {
            keyboardsPanel.remove(keyboardInformationPanel);
        }

        keyboardsInfo = new ArrayList<>();
        currentTournament = new Tournament();
    }

    // MODIFIES: this
    // EFFECTS: layouts the components in their proper positions
    private void layoutComponents() {
        add(header);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(metadata);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(keyboards);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(interactionButtons);
    }

    // MODIFIES: this
    // EFFECTS: creates the header that contains the back button and title
    private void setUpHeader() {
        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        backButton = app.getMainWindow().createNavigationButton(Page.Tournament);
        backButton.setText("<");

        title = new JLabel("🏆  Create tournament");

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(title);

        header.setVisible(true);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // EFFECTS: creates a metadata field with the given name and choices
    private JPanel createMetadataField(String name, JComboBox<String> choices) {
        JPanel metadataField = new JPanel();
        metadataField.setLayout(new BoxLayout(metadataField, BoxLayout.LINE_AXIS));

        JLabel metadataName = new JLabel(name + ": ");

        metadataField.add(metadataName);
        metadataField.add(Box.createRigidArea(new Dimension(10, 0)));
        metadataField.add(choices);

        metadataField.setVisible(true);
        metadataField.setAlignmentX(Component.LEFT_ALIGNMENT);

        return metadataField;
    }

    // MODIFIES: this
    // EFFECTS: creates the combo box for corpora
    private void setUpCorpusChooser() {
        corpusChooser = new JComboBox<>(corporaChoices);
        corpusChooser.setVisible(true);
        corpusChooser.setMaximumSize(comboBoxSize);

        corpusChooser.addItemListener(new ItemListener() {
            // MODIFIES: this
            // EFFECTS: gets the currently selected corpus
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String corpusName = (String) e.getItem();
                    corpus = app.getAppState().getCorpora().get(corpusName);

                    currentTournament.setCorpus(corpus);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the combo box for effort models
    private void setUpEffortModelChooser() {
        effortModelChooser = new JComboBox<>(effortModelChoices);
        effortModelChooser.setVisible(true);
        effortModelChooser.setMaximumSize(comboBoxSize);

        effortModelChooser.addItemListener(new ItemListener() {
            // MODIFIES: this
            // EFFECTS: gets the currently selected effort model
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String effortModelName = (String) e.getItem();
                    effortModel = app.getAppState().getEffortModels().get(effortModelName);

                    currentTournament.setEffortModel(effortModel);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the combo box for effort models
    private void setUpKeyboardGeometryChooser() {
        keyboardChooser = new JComboBox<>(keyboardGeometryChoices);
        keyboardChooser.setVisible(true);
        keyboardChooser.setMaximumSize(comboBoxSize);

        keyboardChooser.addItemListener(new ItemListener() {
            // MODIFIES: this
            // EFFECTS: gets the currently selected keyboard geometry
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String keyboardGeometryName = (String) e.getItem();
                    currentGeometry = app.getAppState().getKeyboardGeometries().get(keyboardGeometryName);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the combo box for effort models
    private void setUpLayoutChooser() {
        layoutChooser = new JComboBox<>(layoutChoices);
        layoutChooser.setVisible(true);
        layoutChooser.setMaximumSize(comboBoxSize);

        layoutChooser.addItemListener(new ItemListener() {
            // MODIFIES: this
            // EFFECTS: gets the currently selected layout
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String layoutName = (String) e.getItem();
                    currentLayout = app.getAppState().getLayouts().get(layoutName);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the metadata panel that asks for the corpus and effort model used
    private void setUpMetadata() {
        metadata = new JPanel();
        metadata.setLayout(new BoxLayout(metadata, BoxLayout.LINE_AXIS));

        setUpCorpusChooser();
        setUpEffortModelChooser();

        JPanel corpusMetadata = createMetadataField("Corpus", corpusChooser);
        JPanel effortModelMetadata = createMetadataField("EffortModel", effortModelChooser);

        metadata.add(corpusMetadata);
        metadata.add(Box.createHorizontalGlue());
        metadata.add(effortModelMetadata);

        metadata.setVisible(true);
        metadata.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // EFFECTS: returns the name of a named object
    private static final Function<INameable, String> GET_NAME = new Function<INameable, String>() {
        @Override
        public String apply(INameable nameable) {
            return nameable.getName();
        }
    };

    // MODIFIES: this
    // EFFECTS: creates the list of choices for corpora
    private void createCorporaChoices() {
        corporaChoices = (String[]) app
                .getAppState()
                .getCorpora()
                .stream()
                .map(GET_NAME)
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    // MODIFIES: this
    // EFFECTS: creates the list of choices for effort models
    private void createEffortModelChoices() {
        effortModelChoices = (String[]) app
                .getAppState()
                .getEffortModels()
                .stream()
                .map(GET_NAME)
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    // MODIFIES: this
    // EFFECTS: creates the list of choices for keyboard geometries
    private void createKeyboardGeometryChoices() {
        keyboardGeometryChoices = (String[]) app
                .getAppState()
                .getKeyboardGeometries()
                .stream()
                .map(GET_NAME)
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    // MODIFIES: this
    // EFFECTS: creates the list of choices for effort models
    private void createLayoutChoices() {
        layoutChoices = (String[]) app
                .getAppState()
                .getLayouts()
                .stream()
                .map(GET_NAME)
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    // MODIFIES: this
    // EFFECTS: creates the keyboards list that shows all keyboards
    private void setUpKeyboards() {
        keyboardsPanel = new JPanel();
        keyboardsPanel.setLayout(new BoxLayout(keyboardsPanel, BoxLayout.PAGE_AXIS));
        keyboardsPanel.setVisible(true);

        additionFields = new JPanel();
        additionFields.setLayout(new BoxLayout(additionFields, BoxLayout.LINE_AXIS));
        additionFields.setVisible(true);

        addKeyboardButton = createAddKeyboardButton();

        additionFields.add(keyboardChooser);
        additionFields.add(new JLabel("  +  "));
        additionFields.add(layoutChooser);
        additionFields.add(Box.createHorizontalGlue());
        additionFields.add(addKeyboardButton);

        keyboardsPanel.add(additionFields);

        keyboards = new JScrollPane(keyboardsPanel);
        keyboards.setVisible(true);
        keyboards.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JButton createAddKeyboardButton() {
        addKeyboardButton = new JButton();
        addKeyboardButton.setText("Add keyboard");

        addKeyboardButton.addActionListener(new ActionListener() {
            // MODIFIES: this
            // EFFECTS: creates a KeyboardInfoPanel consisting of the currently selected geometry and layout
            //          and adds it into the ui
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardInformationPanel infoPanel = new KeyboardInformationPanel(currentGeometry, currentLayout);

                currentTournament.addKeyboard(infoPanel.getKeyboard());
                keyboardsPanel.add(infoPanel, keyboardsInfo.size());
                keyboardsInfo.add(infoPanel);

                keyboardsPanel.revalidate();
            }
        });

        return addKeyboardButton;
    }

    // MODIFIES: this
    // EFFECTS: set up the interaction buttons that allow you to cancel or run the tournament
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
        cancelButton = app.getMainWindow().createNavigationButton(Page.Tournament);
        cancelButton.setText("Cancel");

        return cancelButton;
    }

    // EFFECTS: creates the create button
    private JButton createCreateButton() {
        createButton = app.getMainWindow().createNavigationButton(Page.Tournament);
        createButton.setText("Create");

        createButton.addActionListener(new ActionListener() {
            // MODIFIES: this, parent
            // EFFECTS: adds tournament to the list of tournaments the main panel is using
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.addTournament(currentTournament);
            }
        });

        return createButton;
    }

    /*
     * A class that represents an information panel about the keyboard; allows for
     * interaction buttons like removal
     */
    private class KeyboardInformationPanel extends JPanel {

        private Keyboard keyboard;
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
            this.keyboard = new Keyboard(geometry, layout);

            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

            setUpGeometryLabel();
            setupLayoutLabel();
            setUpInteractionButtons();
            setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            layoutComponents();
            setVisible(true);
        }

        // EFFECTS: gets the keyboard that represents this panel
        public Keyboard getKeyboard() {
            return keyboard;
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
                // MODIFIES: this, parent, tournament
                // EFFECTS: removes the KeyboardInformationPanel from the ui and the associated keyboard from the
                //         tournament
                @Override
                public void actionPerformed(ActionEvent e) {
                    Iterator<KeyboardInformationPanel> iterator = keyboardsInfo.listIterator();

                    currentTournament.removeKeyboard(getKeyboard());

                    while (iterator.hasNext()) {
                        KeyboardInformationPanel next = iterator.next();

                        if (panel == next) {
                            iterator.remove();
                            keyboardsPanel.remove(panel);
                            keyboards.remove(panel);

                            keyboardsPanel.revalidate();
                            keyboardsPanel.repaint();

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

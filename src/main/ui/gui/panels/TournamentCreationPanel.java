package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.*;
import model.corpora.Corpus;
import model.effortmodel.EffortModel;
import ui.gui.App;

import static ui.gui.MainWindow.Page;

public class TournamentCreationPanel extends JPanel {

    private App app;
    private TournamentPanel parent;

    private Corpus corpus;
    private EffortModel effortModel;

    private String[] corporaChoices;
    private String[] effortModelChoices;
    private String[] keyboardGeometryChoices;
    private String[] layoutChoices;

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

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        createCorporaChoices();
        createEffortModelChoices();
        createKeyboardGeometryChoices();
        createLayoutChoices();
        setUpCorpusChooser();
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

    }

    // EFFECTS: creates a tournament from the input fields
    private Tournament createTournament() {
        Tournament tournament = new Tournament(corpus, effortModel);

        for (KeyboardInformationPanel keyboardInformationPanel : keyboardsInfo) {
            tournament.addKeyboard(keyboardInformationPanel.createKeyboard());
        }

        return tournament;
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

        corpusChooser.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String corpusName = (String) e.getItem();
                corpus = app.getAppState().getCorpora().get(corpusName);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the combo box for effort models
    private void setUpEffortModelChooser() {
        effortModelChooser = new JComboBox<>(effortModelChoices);
        effortModelChooser.setVisible(true);

        effortModelChooser.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String effortModelName = (String) e.getItem();
                effortModel = app.getAppState().getEffortModels().get(effortModelName);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the combo box for effort models
    private void setUpKeyboardGeometryChooser() {
        keyboardChooser = new JComboBox<>(effortModelChoices);
        keyboardChooser.setVisible(true);

        keyboardChooser.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String keyboardGeometryName = (String) e.getItem();
                currentGeometry = app.getAppState().getKeyboardGeometries().get(keyboardGeometryName);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the combo box for effort models
    private void setUpLayoutChooser() {
        layoutChooser = new JComboBox<>(effortModelChoices);
        layoutChooser.setVisible(true);

        effortModelChooser.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String layoutName = (String) e.getItem();
                currentLayout = app.getAppState().getLayouts().get(layoutName);
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
                .toArray();
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
                .toArray();
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
                .toArray();
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
                .toArray();
    }

    // MODIFIES: this
    // EFFECTS: creates the keyboards list that shows all keyboards
    private void setUpKeyboards() {
        keyboardsPanel = new JPanel();
        keyboardsPanel.setLayout(new BoxLayout(keyboardsPanel, BoxLayout.PAGE_AXIS));

        additionFields = new JPanel();
        additionFields.setLayout(new BoxLayout(additionFields, BoxLayout.LINE_AXIS));

        keyboardChooser = new JComboBox<>(keyboardGeometryChoices);
        layoutChooser = new JComboBox<>(layoutChoices);
        addKeyboardButton = createAddKeyboardButton();

        additionFields.add(keyboardChooser);
        additionFields.add(new JLabel("  +  "));
        additionFields.add(layoutChooser);
        additionFields.add(Box.createHorizontalGlue());
        additionFields.add(addKeyboardButton);

        keyboards = new JScrollPane(keyboardsPanel);
        keyboards.setVisible(true);
        keyboards.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JButton createAddKeyboardButton() {
        addKeyboardButton = new JButton();
        addKeyboardButton.setText("Add keyboard");

        addKeyboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardInformationPanel infoPanel = new KeyboardInformationPanel(currentGeometry, currentLayout);
                keyboardsPanel.add(infoPanel, keyboardsInfo.size());
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
        createButton = app.getMainWindow().createNavigationButton(Page.Tournament);
        createButton.setText("Create");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tournament tournament = createTournament();
                parent.addTournament(tournament);

                clear();
            }
        });

        return createButton;
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

        // EFFECTS: creates a keyboard from the fields
        public Keyboard createKeyboard() {
            return new Keyboard(geometry, layout);
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
                            keyboardsPanel.remove(panel);
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

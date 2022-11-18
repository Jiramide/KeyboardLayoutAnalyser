package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import model.corpora.Corpus;
import model.corpora.CorpusReader;
import model.corpora.StringCorpus;
import org.json.JSONException;
import persistence.Reader;
import persistence.Writer;
import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

/*
 * A class representing the main panel for corpora
 */
public class CorporaMainPanel extends JPanel {

    private MainWindow parent;

    private BoxLayout boxLayout;

    private CorporaViewPanel viewPanel;
    private CorporaCreationPanel creationPanel;

    private JComponent header;
    private JScrollPane listScrollPane;
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private JComponent interactionButtons;

    // EFFECTS: creates a CorporaMainPanel parented to the given MainWindow
    public CorporaMainPanel(MainWindow parent) {
        super();

        this.parent = parent;
        boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        viewPanel = new CorporaViewPanel(this, parent);
        creationPanel = new CorporaCreationPanel(this, parent);

        parent.addPage(Page.CorporaView, viewPanel);
        parent.addPage(Page.CorporaCreation, creationPanel)

        setLayout(boxLayout);
        setVisible(true);
        createHeader();
        createList();
        createInteractionButtons();
        layoutComponents();
    }

    // MODIFIES: this
    // EFFECTS: layout out the components in their proper positions
    private void layoutComponents() {
        add(header);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(listScrollPane);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(interactionButtons);
    }

    // EFFECTS: creates a button with the given text and ActionListener
    private JButton createInteractionButton(String text, ActionListener actionListener) {
        JButton interactionButton = new JButton(text);
        interactionButton.setVisible(true);
        interactionButton.addActionListener(actionListener);

        return interactionButton;
    }

    // EFFECTS: creates a button responsible for removing corpora
    private JButton createRemoveButton() {
        return createInteractionButton(
                "➖    Remove",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<String> corporaToRemove = list.getSelectedValuesList();

                        for (String corpusName : corporaToRemove) {
                            removeCorpus(corpusName);
                        }
                    }
                }
        );
    }

    // EFFECTS: creates a button responsible for loading corpora
    private JButton createLoadButton() {
        return createInteractionButton(
                "🔃    Load",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String corpusName = JOptionPane.showInputDialog("Corpus name:");
                        loadFromFile(corpusName);
                    }
                }
        );
    }

    // EFFECTS: creates a button responsible for viewing corpora
    private JButton createViewButton() {
        return createInteractionButton(
                "👁    View",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Corpus corpus = getCorpusByName(list.getSelectedValue());

                        if (corpus != null) {
                            viewPanel.setCorpus(corpus);
                            parent.goTo(Page.CorporaView);
                        }
                    }
                }
        );
    }

    // EFFECTS: creates a button responsible for saving corpora
    private JButton createSaveButton() {
        return createInteractionButton(
                "💾    Save",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<String> corporaToSave = list.getSelectedValuesList();

                        for (String corpusName : corporaToSave) {
                            saveToFile(corpusName);
                        }
                    }
                }
        );
    }

    // EFFECTS: creates a create button
    private JButton createCreateButton() {
        JButton addButton = parent.createNavigationButton(Page.CorporaCreation);
        addButton.setText("➕    Create");

        return addButton;
    }

    // MODIFIES: this
    // EFFECTS: creates all interaction buttons for the panel
    private void createInteractionButtons() {
        interactionButtons = new JPanel();
        interactionButtons.setLayout(new BoxLayout(interactionButtons, BoxLayout.LINE_AXIS));

        JButton createButton = createCreateButton();
        JButton removeButton = createRemoveButton();
        JButton loadButton = createLoadButton();
        JButton viewButton = createViewButton();
        JButton saveButton = createSaveButton();

        interactionButtons.add(Box.createHorizontalGlue());
        interactionButtons.add(createButton);
        interactionButtons.add(removeButton);
        interactionButtons.add(viewButton);
        interactionButtons.add(loadButton);
        interactionButtons.add(saveButton);

        interactionButtons.setVisible(true);
        interactionButtons.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // EFFECTS: gets the corpus with the given name; if none are found, return null
    private Corpus getCorpusByName(String name) {
        List<Corpus> corpora = parent.getCorpora();

        for (Corpus corpus : corpora) {
            if (corpus.getName().equals(name)) {
                return corpus;
            }
        }

        return null;
    }

    // EFFECTS: writes the Corpus with the given name to file
    public void saveToFile(String name) {
        Corpus toSave = getCorpusByName(name);
        Path path = Paths.get(".", "data", "Corpora", name + ".corpus.json");
        Writer writer = new Writer(path.toString());

        try {
            writer.open();

            if (toSave != null) {
                writer.write(toSave);
            }
        } catch (IOException e) {
            System.out.println("[ERROR]: Something has gone wrong while saving a Corpus.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a corpus from file and loads it into the application
    public void loadFromFile(String name) {
        Path path = Paths.get(".", "data", "Corpora", name + ".corpus.json");
        Reader reader = new Reader(path.toString());

        try {
            Corpus loaded = reader.readStringCorpus();
            addCorpus(loaded);
        } catch (IOException e) {
            System.out.println("[ERROR]: That wasn't a valid file!");
        } catch (JSONException e) {
            System.out.println("[ERROR]: The file you are reading is not a Corpus.");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the list view for all corpora
    private void createList() {
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);

        List<Corpus> corpora = parent.getCorpora();

        for (Corpus corpus : corpora) {
            listModel.addElement(corpus.getName());
        }

        list.setAlignmentX(Component.LEFT_ALIGNMENT);
        list.setVisible(true);

        listScrollPane = new JScrollPane(list);
        listScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // MODIFIES: this, parent
    // EFFECTS: adds a corpora
    public void addCorpus(Corpus corpus) {
        listModel.addElement(corpus.getName());
        parent.getCorpora().add(corpus);
    }

    // MODIFIES: this, parent
    // EFFECTS: removes a corpora
    public void removeCorpus(String name) {
        listModel.removeElement(name);

        List<Corpus> corpora = parent.getCorpora();
        corpora.remove(getCorpusByName(name));
    }

    // MODIFIES: this
    // EFFECTS: creates the heaader containing the title and a back button
    private void createHeader() {
        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        JButton backButton = parent.createNavigationButton(Page.Selection);
        backButton.setText("<");

        JLabel title = new JLabel("Corpora");

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(title);
        header.add(Box.createHorizontalGlue());

        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setVisible(true);
    }
}

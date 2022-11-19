package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import model.KeyboardGeometry;
import org.json.JSONException;
import persistence.Reader;
import persistence.Writer;
import ui.gui.App;

import static ui.gui.MainWindow.Page;

/*
 * A class representing the main panel for keyboard geometry
 */
public class KeyboardGeometryMainPanel extends JPanel {

    private App app;

    private BoxLayout boxLayout;

    private KeyboardGeometryViewPanel viewPanel;
    private KeyboardGeometryCreationPanel creationPanel;

    private JComponent header;
    private JScrollPane listScrollPane;
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private JComponent interactionButtons;

    // EFFECTS: creates a KeyboardGeometryMainPanel parented to the given MainWindow
    public KeyboardGeometryMainPanel(App app) {
        super();

        this.app = app;
        boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        viewPanel = new KeyboardGeometryViewPanel(app, this);
        creationPanel = new KeyboardGeometryCreationPanel(app, this);

        app.getMainWindow().addPage(Page.KeyboardGeometryView, viewPanel);
        app.getMainWindow().addPage(Page.KeyboardGeometryCreation, creationPanel);

        setLayout(boxLayout);
        setVisible(true);
        createHeader();
        createList();
        createInteractionButtons();
        layoutComponents();
        setVisible(true);
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

    // EFFECTS: creates a button responsible for removing geometry
    private JButton createRemoveButton() {
        return createInteractionButton(
                "➖    Remove",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<String> geometriesToRemove = list.getSelectedValuesList();

                        for (String geometryName : geometriesToRemove) {
                            removeGeometry(geometryName);
                        }
                    }
                }
        );
    }

    // EFFECTS: creates a button responsible for loading geometry
    private JButton createLoadButton() {
        return createInteractionButton(
                "🔃    Load",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String geometryName = JOptionPane.showInputDialog("KeyboardGeometry name:");
                        loadFromFile(geometryName);
                    }
                }
        );
    }

    // EFFECTS: creates a button responsible for viewing geometry
    private JButton createViewButton() {
        return createInteractionButton(
                "👁    View",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        KeyboardGeometry geometry = getGeometryByName(list.getSelectedValue());

                        if (geometry != null) {
                            viewPanel.setGeometry(geometry);
                            app.getMainWindow().goTo(Page.KeyboardGeometryView);
                        }
                    }
                }
        );
    }

    // EFFECTS: creates a button responsible for saving geometry
    private JButton createSaveButton() {
        return createInteractionButton(
                "💾    Save",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<String> geometriesToSave = list.getSelectedValuesList();

                        for (String geometry : geometriesToSave) {
                            saveToFile(geometry);
                        }
                    }
                }
        );
    }

    // EFFECTS: creates a create button
    private JButton createCreateButton() {
        JButton addButton = new JButton();
        addButton.setText("➕    Create");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.getMainWindow().goTo(Page.KeyboardGeometryCreation);
                creationPanel.visit();
            }
        });

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

    // EFFECTS: gets the geometry with the given name; if none are found, return null
    private KeyboardGeometry getGeometryByName(String name) {
        List<KeyboardGeometry> keyboardGeometries = app.getAppState().getKeyboardGeometries();

        for (KeyboardGeometry geometry : keyboardGeometries) {
            if (geometry.getName().equals(name)) {
                return geometry;
            }
        }

        return null;
    }

    // EFFECTS: writes the geometry with the given name to file
    public void saveToFile(String name) {
        KeyboardGeometry toSave = getGeometryByName(name);
        Path path = Paths.get(".", "data", "KeyboardGeometries", name + ".kg.json");
        Writer writer = new Writer(path.toString());

        try {
            writer.open();

            if (toSave != null) {
                writer.write(toSave);
            }
        } catch (IOException e) {
            System.out.println("[ERROR]: Something has gone wrong while saving a KeyboardGeometry.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a geometry from file and loads it into the application
    public void loadFromFile(String name) {
        Path path = Paths.get(".", "data", "KeyboardGeometries", name + ".kg.json");
        Reader reader = new Reader(path.toString());

        try {
            KeyboardGeometry loaded = reader.readKeyboardGeometry();
            addKeyboardGeometry(loaded);
        } catch (IOException e) {
            System.out.println("[ERROR]: That wasn't a valid file!");
        } catch (JSONException e) {
            System.out.println("[ERROR]: The file you are reading is not a KeyboardGeometry.");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the list view for all geometry
    private void createList() {
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);

        List<KeyboardGeometry> geometries = app.getAppState().getKeyboardGeometries();

        for (KeyboardGeometry geometry : geometries) {
            listModel.addElement(geometry.getName());
        }

        list.setAlignmentX(Component.LEFT_ALIGNMENT);
        list.setVisible(true);

        listScrollPane = new JScrollPane(list);
        listScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // MODIFIES: this, parent
    // EFFECTS: adds a geometry
    public void addKeyboardGeometry(KeyboardGeometry keyboardGeometry) {
        listModel.addElement(keyboardGeometry.getName());
        app.getAppState().getKeyboardGeometries().add(keyboardGeometry);
    }

    // MODIFIES: this, parent
    // EFFECTS: removes a geometry
    public void removeGeometry(String name) {
        listModel.removeElement(name);
        app.getAppState().getKeyboardGeometries().remove(name);
    }

    // MODIFIES: this
    // EFFECTS: creates the header containing the title and a back button
    private void createHeader() {
        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        JButton backButton = app.getMainWindow().createNavigationButton(Page.Selection);
        backButton.setText("<");

        JLabel title = new JLabel("KeyboardGeometry");

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(title);
        header.add(Box.createHorizontalGlue());

        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setVisible(true);
    }
}

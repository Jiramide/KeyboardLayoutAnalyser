package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import model.Layout;
import org.json.JSONException;
import persistence.Reader;
import persistence.Writer;
import ui.gui.App;

import static ui.gui.MainWindow.Page;

/*
 * A class representing the main layout panel that shows all layouts and provides
 * interations to those layouts
 */
public class LayoutMainPanel extends JPanel {

    private App app;

    private BoxLayout boxLayout;

    private LayoutViewPanel viewPanel;
    private LayoutCreationPanel creationPanel;

    private JComponent header;
    private JScrollPane listScrollPane;
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private JComponent interactionButtons;

    // EFFECTS: creates a LayoutMainPanel parented to the given MainWindow
    public LayoutMainPanel(App app) {
        super();

        this.app = app;
        boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        viewPanel = new LayoutViewPanel(app, this);
        creationPanel = new LayoutCreationPanel(app, this);

        app.getMainWindow().addPage(Page.LayoutView, viewPanel);
        app.getMainWindow().addPage(Page.LayoutCreation, creationPanel);

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

    // EFFECTS: creates a button responsible for removing layout
    private JButton createRemoveButton() {
        return createInteractionButton(
                "➖    Remove",
                new ActionListener() {
                    // MODIFIES: this, parent
                    // EFFECTS: removes all selected layouts
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<String> layoutsToRemove = list.getSelectedValuesList();

                        for (String layoutName : layoutsToRemove) {
                            removeLayout(layoutName);
                        }
                    }
                }
        );
    }

    // EFFECTS: creates a button responsible for loading layout
    private JButton createLoadButton() {
        return createInteractionButton(
                "🔃    Load",
                new ActionListener() {
                    // MODIFIES: this, parent
                    // EFFECTS: queries the user for a layout name and loads it into the app
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String layoutName = JOptionPane.showInputDialog("Layout name:");
                        loadFromFile(layoutName);
                    }
                }
        );
    }

    // EFFECTS: creates a button responsible for viewing layout
    private JButton createViewButton() {
        return createInteractionButton(
                "👁    View",
                new ActionListener() {
                    // MODIFIES: this, viewPanel
                    // EFFECTS: views the currently selected layout
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Layout layout = getLayoutByName(list.getSelectedValue());

                        if (layout != null) {
                            viewPanel.setLayout(layout);
                            app.getMainWindow().goTo(Page.LayoutView);
                        }
                    }
                }
        );
    }

    // EFFECTS: creates a button responsible for saving layout
    private JButton createSaveButton() {
        return createInteractionButton(
                "💾    Save",
                new ActionListener() {
                    // MODIFIES: this, parent
                    // EFFECTS: saves all selected layouts into file
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        java.util.List<String> layoutToSave = list.getSelectedValuesList();

                        for (String layoutName : layoutToSave) {
                            saveToFile(layoutName);
                        }
                    }
                }
        );
    }

    // EFFECTS: creates a create button
    private JButton createCreateButton() {
        JButton addButton = app.getMainWindow().createNavigationButton(Page.LayoutCreation);
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

    // EFFECTS: gets the layout with the given name; if none are found, return null
    private Layout getLayoutByName(String name) {
        java.util.List<Layout> layouts = app.getAppState().getLayouts();

        for (Layout layout : layouts) {
            if (layout.getName().equals(name)) {
                return layout;
            }
        }

        return null;
    }

    // EFFECTS: writes the layout with the given name to file
    public void saveToFile(String name) {
        Layout toSave = getLayoutByName(name);
        Path path = Paths.get(".", "data", "Layout", name + ".layout.json");
        Writer writer = new Writer(path.toString());

        try {
            writer.open();

            if (toSave != null) {
                writer.write(toSave);
            }
        } catch (IOException e) {
            System.out.println("[ERROR]: Something has gone wrong while saving a Layout.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a layout from file and loads it into the application
    public void loadFromFile(String name) {
        Path path = Paths.get(".", "data", "Layout", name + ".layout.json");
        Reader reader = new Reader(path.toString());

        try {
            Layout loaded = reader.readLayout();
            addLayout(loaded);
        } catch (IOException e) {
            System.out.println("[ERROR]: That wasn't a valid file!");
        } catch (JSONException e) {
            System.out.println("[ERROR]: The file you are reading is not a Layout.");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the list view for all layouts
    private void createList() {
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);

        List<Layout> layouts = app.getAppState().getLayouts();

        for (Layout layout : layouts) {
            listModel.addElement(layout.getName());
        }

        list.setAlignmentX(Component.LEFT_ALIGNMENT);
        list.setVisible(true);

        listScrollPane = new JScrollPane(list);
        listScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // MODIFIES: this, parent
    // EFFECTS: adds a layout
    public void addLayout(Layout layout) {
        listModel.addElement(layout.getName());
        app.getAppState().getLayouts().add(layout);
    }

    // MODIFIES: this, parent
    // EFFECTS: removes a layout
    public void removeLayout(String name) {
        listModel.removeElement(name);
        app.getAppState().getLayouts().remove(name);
    }

    // MODIFIES: this
    // EFFECTS: creates the header containing the title and a back button
    private void createHeader() {
        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        JButton backButton = app.getMainWindow().createNavigationButton(Page.KeyboardMain);
        backButton.setText("<");

        JLabel title = new JLabel("Layout");

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(title);
        header.add(Box.createHorizontalGlue());

        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setVisible(true);
    }
}

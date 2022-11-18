package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import model.Keyboard;
import model.Tournament;
import ui.gui.App;
import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

/*
 * A class representing the main viewing panel for Tournaments
 */
public class TournamentPanel extends JPanel {

    private App app;

    private BoxLayout boxLayout;
    private List<Tournament> tournaments = new ArrayList<>();

    private JPanel header;
    private JScrollPane tournamentScrollPane;
    private DefaultListModel<Tournament> tournamentListModel;
    private JList<Tournament> tournamentJList;
    private JPanel interactionButtons;

    private TournamentCreationPanel tournamentCreationPanel;

    // EFFECTS: creates a TournamentPanel which is parented to the MainWindow
    public TournamentPanel(App app) {
        super();

        this.app = app;
        boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        tournamentCreationPanel = new TournamentCreationPanel(app, this);

        setLayout(boxLayout);
        createHeader();
        createList();
        createInteractionButtons();
        layoutComponents();
    }

    // MODIFIES: this
    // EFFECTS: inserts a new tournament into the application
    public void addTournament(Tournament tournament) {
        tournaments.add(tournament);
        tournamentListModel.add(0, tournament);
    }

    // EFFECTS: creates a button responsible for directing you to the TournamentCreation page
    private JButton createNewTournamentButton() {
        JButton newTournamentButton = app.getMainWindow().createNavigationButton(Page.TournamentCreation);
        newTournamentButton.setText("➕    Create new tournament");

        return newTournamentButton;
    }

    // MODIFIES: this
    // EFFECTS: creates the set of interaction buttons that allow you to interact with tournaments
    //          and create tournaments
    private void createInteractionButtons() {
        interactionButtons = new JPanel();
        interactionButtons.setLayout(new BoxLayout(interactionButtons, BoxLayout.LINE_AXIS));

        JButton newTournamentButton = createNewTournamentButton();

        interactionButtons.add(Box.createHorizontalGlue());
        interactionButtons.add(newTournamentButton);
        interactionButtons.add(Box.createHorizontalGlue());

        interactionButtons.setVisible(true);
        interactionButtons.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: creates the list view (in a scrolling panel) that allows you to view all tournaments
    //          in a list
    private void createList() {
        tournamentListModel = new DefaultListModel<>();
        tournamentJList = new JList<>(tournamentListModel);
        tournamentJList.setCellRenderer(new TournamentCellRenderer());

        tournamentJList.setAlignmentX(Component.LEFT_ALIGNMENT);
        tournamentJList.setVisible(true);

        tournamentScrollPane = new JScrollPane(tournamentJList);
        tournamentScrollPane.setVisible(true);
        tournamentScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: layouts the components in their proper positions
    private void layoutComponents() {
        add(header);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(tournamentScrollPane);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(interactionButtons);
    }

    // MODIFIES: this
    // EFFECTS: creates the header, which contains the back button and the title
    private void createHeader() {
        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        JButton backButton = app.getMainWindow().createNavigationButton(Page.Selection);
        backButton.setText("<");

        JLabel title = new JLabel();
        title.setText("🏆  Tournament");

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(title);
        header.add(Box.createHorizontalGlue());

        header.setVisible(true);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /*
     * A class responsible for producing the Components that render a Tournament item in a JList
     * Some implementation details were taken from DefaultListCellRenderer in order
     * to maintain consistency
     */
    private static class TournamentCellRenderer implements ListCellRenderer<Tournament> {

        // EFFECTS: creates a Component rendering a single Tournament

        // Since I wanted to make this CellRenderer render cells as close to the default as possible,
        // many implementation details were taken from DefaultListCellRenderer. Some implementation
        // details from DefaultListCellRenderer requires looking up in a DefaultLookup class, which
        // is imported from sun (which seems to require a compiler flag in order to successfully import)
        // Unfortunately, I can't change the compiler flags of the auto grader bot, so I can't use
        // that package within my code; hopefully this explains some changes
        @Override
        public Component getListCellRendererComponent(
                JList<? extends Tournament> list,
                Tournament value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            JPanel cell = createCellPanel();

            // Taken from DefaultListCellRenderer
            if (isSelected) {
                cell.setBackground(list.getSelectionBackground());
                cell.setForeground(list.getSelectionForeground());
            } else {
                cell.setBackground(list.getBackground());
                cell.setForeground(list.getForeground());
            }

            cell.add(createHeader(value, index));
            cell.add(createCorpusAndEffortModelIntoPanel(value));
            cell.add(createKeyboardsPanel(value));

            cell.setComponentOrientation(list.getComponentOrientation());

            return cell;
        }

        // EFFECTS: creates the panel containing all keyboards in the tournament
        private Component createKeyboardsPanel(Tournament value) {
            List<String> keyboardNames = new ArrayList<>();

            for (Keyboard keyboard : value.getKeyboards()) {
                keyboardNames.add(keyboard.getGeometry().getName() + " with " + keyboard.getLayout().getName());
            }

            JList<String> keyboards = new JList<>((String[]) keyboardNames.toArray());

            return new JScrollPane(keyboards);
        }

        // EFFECTS: creates a line that contains information about what corpus and effort model is used
        private Component createCorpusAndEffortModelIntoPanel(Tournament value) {
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.LINE_AXIS));

            JLabel corpusName = new JLabel(value.getCorpus().getName());
            JLabel effortModelName = new JLabel(value.getEffortModel().getName());

            infoPanel.add(corpusName);
            infoPanel.add(Box.createHorizontalGlue());
            infoPanel.add(effortModelName);

            infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            infoPanel.setVisible(true);

            return infoPanel;
        }

        // EFFECTS: creates the header that contains an identification for the tournament
        private Component createHeader(Tournament value, int index) {
            JPanel header = new JPanel();
            header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

            JLabel title = new JLabel("🏆  Tournament #" + (index + 1));
            title.setVisible(true);

            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setVisible(true);

            return header;
        }

        // EFFECTS: creates the panel that contains all the components
        private JPanel createCellPanel() {
            JPanel cell = new JPanel();
            cell.setLayout(new BoxLayout(cell, BoxLayout.PAGE_AXIS));

            return cell;
        }
    }
}

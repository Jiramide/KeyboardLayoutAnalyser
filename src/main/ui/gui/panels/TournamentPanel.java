package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import model.Keyboard;
import model.Tournament;
import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

public class TournamentPanel extends JPanel {

    private MainWindow parent;
    private BoxLayout boxLayout;
    private List<Tournament> tournaments = new ArrayList<>();

    private JPanel header;
    private JScrollPane tournamentScrollPane;
    private ListModel<Tournament> tournamentListModel;
    private JList<Tournament> tournamentJList;
    private JPanel interactionButtons;

    public TournamentPanel(MainWindow parent) {
        super();

        this.parent = parent;
        boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);

        createHeader();
        createList();
        createInteractionButtons();
        layoutComponents();
    }

    private void createInteractionButtons() {

    }

    private void createList() {
        tournamentListModel = new DefaultListModel<>();
        tournamentJList = new JList<>(tournamentListModel);
        tournamentJList.setCellRenderer(new TournamentCellRenderer());

        tournamentJList.setAlignmentX(Component.LEFT_ALIGNMENT);
        tournamentJList.setVisible(true);

        tournamentScrollPane = new JScrollPane(tournamentJList);
        tournamentScrollPane.setVisible(true);
    }

    private void layoutComponents() {
        add(header);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(tournamentScrollPane);
    }

    private void createHeader() {
        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        JButton backButton = parent.createNavigationButton(Page.Selection);
        backButton.setText("<");

        JLabel title = new JLabel();
        title.setText("🏆  Tournament");

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(title);
        header.add(Box.createHorizontalGlue());

        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setVisible(true);
    }

    private static class TournamentCellRenderer implements ListCellRenderer<Tournament> {

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

        private Component createKeyboardsPanel(Tournament value) {
            List<String> keyboardNames = new ArrayList<>();

            for (Keyboard keyboard : value.getKeyboards()) {
                keyboardNames.add(keyboard.getGeometry().getName() + " with " + keyboard.getLayout().getName());
            }

            JList<String> keyboards = new JList<>((String[]) keyboardNames.toArray());

            return new JScrollPane(keyboards);
        }

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

        private Component createHeader(Tournament value, int index) {
            JPanel header = new JPanel();
            header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

            JLabel title = new JLabel("🏆  Tournament #" + (index + 1));
            title.setVisible(true);

            header.setAlignmentX(Component.LEFT_ALIGNMENT);
            header.setVisible(true);

            return header;
        }

        private JPanel createCellPanel() {
            JPanel cell = new JPanel();
            cell.setLayout(new BoxLayout(cell, BoxLayout.PAGE_AXIS));

            return cell;
        }
    }
}

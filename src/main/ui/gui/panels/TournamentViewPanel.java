package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Tournament;
import ui.gui.App;
import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

public class TournamentViewPanel extends JPanel {

    private App app;
    private TournamentPanel parent;

    private Tournament tournament;

    private JPanel header;
    private JButton backButton;
    private JLabel tournamentTitle;
    private JPanel metadata;
    private JLabel corpusTitle;
    private JLabel effortModelTitle;
    private JScrollPane graphScroll;
    private JPanel graph;

    private static final Color[] BAR_COLORS = {
            new Color(240, 0, 0),
            new Color(0, 255, 0),
            new Color(0, 0, 255)
    };

    // EFFECTS: creates a TournamentViewPanel with the given app and parent
    public TournamentViewPanel(App app, TournamentPanel parent) {

    }

    // MODIFIES: this
    // EFFECTS: sets the tournament to be viewed
    public void setTournament(Tournament tournament) {

    }

    // MODIFIES: this
    // EFFECTS: lays out the components in their proper positions
    private void layoutComponents() {

    }

    // MODIFIES: this
    // EFFECTS: sets up the header
    private void setUpHeader() {

    }

    // MODIFIES: this
    // EFFECTS: sets up the metadata
    private void setUpMetadata() {

    }

    // MODIFIES: this
    // EFFECTS: sets up the graph
    private void setUpGraph() {

    }

    // MODIFIES: this
    // EFFECTS: renders the graph
    private void renderGraph() {

    }
}

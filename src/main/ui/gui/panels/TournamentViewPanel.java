package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.util.Map;
import java.util.List;

import model.Keyboard;
import model.Tournament;
import ui.gui.App;

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
            new Color(255, 0, 0),
            new Color(0, 255, 0),
            new Color(0, 0, 255)
    };

    // EFFECTS: creates a TournamentViewPanel with the given app and parent
    public TournamentViewPanel(App app, TournamentPanel parent) {
        super();

        this.app = app;
        this.parent = parent;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setVisible(true);

        setUpHeader();
        setUpMetadata();
        setUpGraph();
        layoutComponents();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the tournament to be viewed
    public void setTournament(Tournament tournament) {
        this.tournament = tournament;

        renderTitle();
        renderMetadata();
        renderGraph();

        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: lays out the components in their proper positions
    private void layoutComponents() {
        add(header);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(metadata);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(graphScroll);
    }

    // MODIFIES: this
    // EFFECTS: sets up the header
    private void setUpHeader() {
        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        backButton = app.getMainWindow().createNavigationButton(Page.Tournament);
        backButton.setText("<");

        tournamentTitle = new JLabel("🏆  Tournament");

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(10, 0)));
        header.add(tournamentTitle);

        header.setVisible(true);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: sets up the metadata
    private void setUpMetadata() {
        metadata = new JPanel();
        metadata.setLayout(new BoxLayout(metadata, BoxLayout.LINE_AXIS));

        corpusTitle = new JLabel();
        effortModelTitle = new JLabel();

        metadata.add(corpusTitle);
        metadata.add(Box.createHorizontalGlue());
        metadata.add(effortModelTitle);

        metadata.setVisible(true);
        metadata.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: sets up the graph
    private void setUpGraph() {
        graph = new JPanel();
        graph.setLayout(new BoxLayout(graph, BoxLayout.PAGE_AXIS));
        graph.setVisible(true);

        graphScroll = new JScrollPane(graph);
        graphScroll.setVisible(true);
        graphScroll.setAlignmentX(LEFT_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: renders the title
    private void renderTitle() {

    }

    // MODIFIES: this
    // EFFECTS: renders the metadata
    private void renderMetadata() {
        corpusTitle.setText("Corpus: " + tournament.getCorpus().getName());
        effortModelTitle.setText("EffortModel: " + tournament.getEffortModel().getName());
    }

    // MODIFIES: this
    // EFFECTS: renders the graph
    private void renderGraph() {
        remove(graphScroll);

        graphScroll = new JScrollPane(new BarGraph(
                tournament.getSortedRankings(),
                tournament.computeScores()
        ));

        graphScroll.setVisible(true);
        graphScroll.setAlignmentX(LEFT_ALIGNMENT);

        add(graphScroll);
        repaint();
        revalidate();
    }

    /*
     * A class representing a bar graph panel
     */
    private static class BarGraph extends JPanel {

        public static final int BAR_HEIGHT = 50;
        public static final int SPACE_BETWEEN_BARS = 25;

        private double max;
        private List<Keyboard> sortedRanking;
        private Map<Keyboard, Double> scores;

        // EFFECTS: creates a bar graph with the given data
        public BarGraph(List<Keyboard> sortedRanking, Map<Keyboard, Double> scores) {
            this.sortedRanking = sortedRanking;
            this.scores = scores;

            max = scores.get(sortedRanking.get(sortedRanking.size() - 1));

            repaint();
            setVisible(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int size = sortedRanking.size() * (BAR_HEIGHT + SPACE_BETWEEN_BARS) - SPACE_BETWEEN_BARS;
            int currentY = 0;
            int index = 0;
            FontMetrics fontMetrics = g.getFontMetrics(g.getFont());

            for (Keyboard keyboard : sortedRanking) {
                double score = scores.get(keyboard);

                g.setColor(BAR_COLORS[index % 3]);
                g.fillRect(0, currentY, (int) (score / max * getWidth()), BAR_HEIGHT);
                g.setColor(Color.BLACK);
                g.drawString(
                        keyboard.getGeometry().getName() + " + " + keyboard.getLayout().getName(),
                        10,
                        currentY + BAR_HEIGHT / 2
                );


                g.drawString(
                        Double.toString(score),
                        getWidth() - fontMetrics.stringWidth(Double.toString(score)) - 10,
                        currentY + BAR_HEIGHT / 2
                );

                index += 1;
                currentY += BAR_HEIGHT + SPACE_BETWEEN_BARS;
            }
        }

        // EFFECTS: returns a dimension large enough to encompass all bars
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(
                    getWidth(),
                    sortedRanking.size() * (BAR_HEIGHT + SPACE_BETWEEN_BARS) - SPACE_BETWEEN_BARS
            );
        }
    }
}

package ui.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.KeyboardGeometry;
import model.Layout;
import model.Tournament;
import model.corpora.Corpus;

import model.corpora.StringCorpus;
import ui.gui.panels.*;

/*
 * A class representing the main window of the GUI
 */
public class MainWindow {

    public static final String TITLE = "Keyboard Layout Optimizer (using) Corpus Crunching";
    public static final ImageIcon ICON = new ImageIcon();

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public static final int SIDE_PADDING = 5;

    private JFrame frame = new JFrame(TITLE);
    private JPanel mainPanel = new JPanel();
    private CardLayout cardLayout = new CardLayout();

    private List<Corpus> corpora = new ArrayList<>();
    private List<KeyboardGeometry> keyboardGeometries = new ArrayList<>();
    private List<Layout> layouts = new ArrayList<>();

    public MainWindow() {
        setUpContentPanel();
        setUpPaddedPanel();

        addPage(Page.Selection, new SelectionPanel(this));
        addPage(Page.CorporaMain, new CorporaMainPanel(this));
        addPage(Page.KeyboardMain, new KeyboardMainPanel(this));
        addPage(Page.Tournament, new TournamentPanel(this));

        goTo(Page.Selection);
    }

    private void setUpPaddedPanel() {
        frame.setLayout(new BorderLayout());

        Component padding = Box.createRigidArea(new Dimension(SIDE_PADDING, SIDE_PADDING));
        frame.add(padding, BorderLayout.PAGE_START);
        frame.add(padding, BorderLayout.BEFORE_LINE_BEGINS);
        frame.add(padding, BorderLayout.PAGE_END);
        frame.add(padding, BorderLayout.AFTER_LINE_ENDS);

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpContentPanel() {
        mainPanel.setLayout(cardLayout);

        mainPanel.setVisible(true);
    }

    public List<Corpus> getCorpora() {
        return corpora;
    }

    public List<KeyboardGeometry> getKeyboardGeometries() {
        return keyboardGeometries;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public void addPage(Page page, JComponent panel) {
        mainPanel.add(panel, PAGE_TO_ID_MAP.get(page));
    }


    // MODIFIES: this
    // EFFECTS: shows the page panel associated with the page enum
    public void goTo(Page destination) {
        System.out.println("going to " + PAGE_TO_ID_MAP.get(destination));
        cardLayout.show(mainPanel, PAGE_TO_ID_MAP.get(destination));
    }

    // EFFECTS: creates a button which, upon clicked, will go to the page passed
    public JButton createNavigationButton(Page destination) {
        JButton navigationButton = new JButton();

        navigationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goTo(destination);
            }
        });

        return navigationButton;
    }

    /*
     * An enum representing the different pages that you can travel to
     * This enum exists mostly for allowing for compile time name checking and
     * avoids the risk of runtime errors due to attempting to travel to
     * a name that doesn't associate to a page.
     */
    public enum Page {
        Selection,

        CorporaMain,
        CorporaCreation,
        CorporaView,

        KeyboardMain,
        KeyboardCreation,
        KeyboardView,

        Tournament
    }

    private static final Map<Page, String> PAGE_TO_ID_MAP = Map.of(
            Page.Selection, "Selection",

            Page.CorporaMain, "CorporaMain",
            Page.CorporaCreation, "CorporaCreation",
            Page.CorporaView, "CorporaView",

            Page.KeyboardMain, "KeyboardMain",
            Page.KeyboardCreation, "KeyboardCreation",
            Page.KeyboardView, "KeyboardView",

            Page.Tournament, "Tournament"
    );


}

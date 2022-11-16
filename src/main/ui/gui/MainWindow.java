package ui.gui;

import javax.swing.*;
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

import ui.gui.panels.*;

/*
 * A class representing the main window of the GUI
 */
public class MainWindow {

    public static final String TITLE = "Keyboard Layout Optimizer (using) Corpus Crunching";
    public static final ImageIcon ICON = new ImageIcon();

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;


    private JFrame frame = new JFrame(TITLE);
    private CardLayout cardLayout = new CardLayout();

    /*
    private SelectionPanel selectionPanel;

    private CorporaMainPanel corporaMainPanel;
    private CorporaCreationPanel corporaCreationPanel;
    private CorporaViewPanel corporaViewPanel;

    private KeyboardMainPanel keyboardMainPanel;
    private KeyboardConstructionPanel keyboardConstructionPanel;
    private KeyboardViewPanel keyboardViewPanel;

    private TournamentPanel tournamentPanel;
     */


    private List<Corpus> corpora = new ArrayList<>();
    private List<KeyboardGeometry> keyboardGeometries = new ArrayList<>();
    private List<Layout> layouts = new ArrayList<>();

    public MainWindow() {
        frame.setLayout(cardLayout);

        frame.add(new SelectionPanel(this), PAGE_TO_ID_MAP.get(Page.Selection));

        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        goTo(Page.Selection);
    }

    private void createPanel() {

    }

    private void createPages() {
        /*
        corporaMainPanel = new CorporaMainPanel();
        frame.add(corporaMainPanel, PAGE_TO_ID_MAP.get(Page.CorporaMain)
        */
    }

    // MODIFIES: this
    // EFFECTS: shows the page panel associated with the page enum
    public void goTo(Page destination) {
        System.out.println("going to " + PAGE_TO_ID_MAP.get(destination));
        cardLayout.show(frame.getContentPane(), PAGE_TO_ID_MAP.get(destination));
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

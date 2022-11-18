package ui.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.corpora.*;
import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

public class CorporaCreationPanel extends JPanel {

    private MainWindow mainWindow;
    private CorporaMainPanel parent;

    private JPanel header;
    private JButton backButton;
    private JLabel title;
    private JPanel metadata;
    private JTextField corpusName;
    private JScrollPane corpusContentScroll;
    private JTextArea corpusContent;
    private JPanel interactionButtons;
    private JButton cancelButton;
    private JButton createButton;

    // EFFECTS: creates a CorporaCreationPanel with the given parent
    public CorporaCreationPanel(CorporaMainPanel parent, MainWindow mainWindow) {

    }
}

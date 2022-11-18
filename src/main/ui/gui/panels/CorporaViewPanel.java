package ui.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.corpora.Corpus;
import model.corpora.CorpusReader;
import ui.gui.App;

import static ui.gui.MainWindow.Page;

/*
 * A class representing the panel for viewing a corpus' contents
 */
public class CorporaViewPanel extends JPanel {

    public static final int CHARS_PER_READ_ITERATION = 500;

    private App app;

    private CorporaMainPanel parent;
    private Corpus corpus;
    private CorpusReader reader;

    private StringBuilder contentBuffer;
    private JButton backButton;
    private JLabel corpusName;
    private JTextArea corpusContent;
    private JButton showMore;

    // EFFECTS: creates a CorporaViewPanel that's parented to a CorporaMainPanel
    public CorporaViewPanel(App app, CorporaMainPanel parent) {
        super();

        this.app = app;
        this.parent = parent;

        contentBuffer = new StringBuilder();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setUpHeader();
        add(Box.createRigidArea(new Dimension(0, 5)));
        setUpContent();
        add(Box.createRigidArea(new Dimension(0, 5)));
        setUpShowMore();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the header that contains the back button and the corpus' name
    private void setUpHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        backButton = app.getMainWindow().createNavigationButton(Page.CorporaMain);
        backButton.setText("<");

        corpusName = new JLabel();

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(5, 0)));
        header.add(corpusName);
        header.add(Box.createHorizontalGlue());

        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(header);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for listing out the content of a corpus, may be truncated
    private void setUpContent() {
        corpusContent = new JTextArea();
        corpusContent.setLineWrap(true);
        corpusContent.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane contentScrollPane = new JScrollPane(corpusContent);
        contentScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentScrollPane.setVisible(true);

        add(contentScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: sets up the button that allows you to show more of the corpus content
    private void setUpShowMore() {
        showMore = new JButton("Show more");

        showMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performReadIteration();
            }
        });

        showMore.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(showMore);
    }

    // MODIFIES: this
    // EFFECTS: loads more of the corpus content and sets the content panel to display more text
    //          and disables the show more button if the corpus has been exhausted
    private void performReadIteration() {
        readAndAddToBuffer();
        corpusContent.setText(contentBuffer.toString());

        if (reader.isFinished()) {
            showMore.setEnabled(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: reads more fron the corpus and adds it to the buffer containing everything
    private void readAndAddToBuffer() {
        for (int index = 0; index < CHARS_PER_READ_ITERATION && !reader.isFinished(); index++) {
            contentBuffer.append(reader.consume());
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the corpus and sets up the panel to show this current corpus
    public void setCorpus(Corpus corpus) {
        this.corpus = corpus;
        reader = corpus.createCorpusReader();
        contentBuffer = new StringBuilder();

        corpusName.setText("📕  " + corpus.getName());

        showMore.setEnabled(true);
        performReadIteration();
    }
}

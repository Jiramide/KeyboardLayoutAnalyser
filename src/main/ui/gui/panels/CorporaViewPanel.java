package ui.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.corpora.Corpus;
import model.corpora.CorpusReader;
import ui.gui.MainWindow;

import static ui.gui.MainWindow.Page;

public class CorporaViewPanel extends JPanel {

    public static final int CHARS_PER_READ_ITERATION = 500;

    private MainWindow mainWindow;
    private CorporaMainPanel parent;
    private Corpus corpus;
    private CorpusReader reader;

    private StringBuilder contentBuffer;
    private JButton backButton;
    private JLabel corpusName;
    private JTextArea corpusContent;
    private JButton showMore;

    public CorporaViewPanel(MainWindow mainWindow, CorporaMainPanel parent) {
        super();

        this.mainWindow = mainWindow;
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

    private void setUpHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));

        backButton = mainWindow.createNavigationButton(Page.CorporaMain);
        backButton.setText("<");

        corpusName = new JLabel();

        header.add(backButton);
        header.add(Box.createRigidArea(new Dimension(5, 0)));
        header.add(corpusName);
        header.add(Box.createHorizontalGlue());

        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(header);
    }

    private void setUpContent() {
        corpusContent = new JTextArea();
        corpusContent.setLineWrap(true);
        corpusContent.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(corpusContent);
    }

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

    private void performReadIteration() {
        readAndAddToBuffer();
        corpusContent.setText(contentBuffer.toString());

        if (reader.isFinished()) {
            showMore.setEnabled(false);
        }
    }

    private void readAndAddToBuffer() {
        for (int index = 0; index < CHARS_PER_READ_ITERATION && !reader.isFinished(); index++) {
            contentBuffer.append(reader.consume());
        }
    }

    public void setCorpus(Corpus corpus) {
        this.corpus = corpus;
        reader = corpus.createCorpusReader();
        contentBuffer = new StringBuilder();

        corpusName.setText("📕  " + corpus.getName());

        showMore.setEnabled(true);
        performReadIteration();
    }

}

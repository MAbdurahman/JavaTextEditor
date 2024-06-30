package net.abdurrahman.app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

/**
 * The FindAndReplace Class
 * @author  MAbdurrahman
 * @date  26 June 2024
 * @version  1.0.0
 */
public class FindAndReplace extends JDialog implements ActionListener {
    /** Instance Variables */
    private JTextField findTextField, replaceTextField;
    private JCheckBox caseSensitiveCheckBox, matchExactWordCheckBox;
    private JRadioButton searchUpRadioButton, searchDownRadioButton;
    private final Font boldFont = new Font("Lucida Console", Font.BOLD, 13);
    private final Font boldFont2 = new Font("Lucida Console", Font.BOLD, 12);
    private final Font plainFont = new Font("Lucida Console", Font.PLAIN, 13);
    private final Font plainFont2 = new Font("Lucida Console", Font.PLAIN, 12);
    private final JPanel northPanel, centerPanel;
    private JLabel statusInfoLabel;
    private final JFrame owner;
    @SuppressWarnings("FieldMayBeFinal")
    private boolean isFind, isReplace;
    private int wordPosition;

    /**
     * FindAndReplace Constructor -
     * @param owner - the JFrame
     * @param modal - a Boolean to represent whether this modal is a 'Find and Replace'
     * or 'Find' modal
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FindAndReplace(JFrame owner, boolean modal) {
        super(owner, true);
        this.owner = owner;
        this.isReplace = modal;
        northPanel = new JPanel();
        centerPanel = new JPanel();

        if (isReplace) {
            setTitle("FindAndReplace Dialog");
            setupFindAndReplacePanel(northPanel);

        } else {
            setTitle("Find Dialog");
            setupFindPanel(northPanel);
        }
        addCenterComponents(centerPanel);
        //addSouthComponents(southPanel);

        addWindowListener(new WindowAdapter() {
            /**
             * windowClosing Method -
             * @param we - the WindowEvent
             */
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();

            }//end of the windowClosing Method
        });//end of the Anonymous WindowAdapter

        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        //getContentPane().add(southPanel, BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocation(500, 300);
        setVisible(true);

    }//end of the FindAndReplace Constructor

    /** Custom HighlightPainter for findNext and findAll */
    Highlighter.HighlightPainter wordHighlighter = new WordHighlighter(new Color(205, 230, 250, 128));

    /**
     * WordHighlighter Class
     */
    class WordHighlighter extends DefaultHighlighter.DefaultHighlightPainter {
        /**
         * WordHighlighter Constructor -
         * @param highlightColor - the Color of the highlight color
         */
        public WordHighlighter(Color highlightColor) {
            super(highlightColor);

        }//end of the WordHighlighter Constructor
    }//end of the WordHighlighter Class
    /**
     * setupFindPanel Method -
     * @param panel - the JPanel representing the panel
     */
    private void setupFindPanel(JPanel panel) {
        GridBagLayout gridBag = new GridBagLayout();
        panel.setLayout(gridBag);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 2, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 24;

        JLabel findWordLabel = new JLabel("Find What: ");
        /*findWordLabel.setFont(new Font("Verdana", Font.BOLD, 13));*/
        findWordLabel.setFont(boldFont);


        JButton findNextButton = new JButton("Find Next");
        /*findNextButton.setFont(new Font("Verdana", Font.BOLD, 13));*/
        findNextButton.setFont(boldFont);
        findNextButton.addActionListener(this);
        findNextButton.setEnabled(false);
        gbc.ipadx = 24;


        JButton findAllButton = new JButton("Find All");
        /*findAllButton.setFont(new Font("Verdana", Font.BOLD, 13));*/
        findAllButton.setFont(boldFont);
        findAllButton.addActionListener(this);
        findAllButton.setEnabled(false);
        gbc.ipadx = 24;

        JButton cancelButton = new JButton("Cancel");
        /*cancelButton.setFont(new Font("Verdana", Font.BOLD, 13));*/
        cancelButton.setFont(boldFont);
        cancelButton.addActionListener(this);
        cancelButton.setEnabled(true);

        findTextField = new JTextField(20);
        /*findTextField.setFont(new Font("Verdana", Font.PLAIN, 13));*/
        findTextField.setFont(plainFont);
        findTextField.addActionListener(this);
        findTextField.addKeyListener(new KeyAdapter() {
            /**
             * keyReleased Method -
             * @param ke - the KeyEvent representing whether a key release has occurred in
             * the input field
             */
            @Override
            public void keyReleased(KeyEvent ke) {
                boolean state = (findTextField.getDocument().getLength() > 0);
                findNextButton.setEnabled(state);
                findAllButton.setEnabled(state);
                isFind = false;

            }//end of the keyReleased Method
        });//end of the Anonymous KeyAdapter Class

        if (findTextField.getText().length() > 0) {
            findNextButton.setEnabled(true);
            findAllButton.setEnabled(true);

        } else {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gridBag.setConstraints(findWordLabel, gbc);
            panel.add(findWordLabel);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gridBag.setConstraints(findTextField, gbc);
            panel.add(findTextField);

            gbc.gridx = 2;
            gbc.gridy = 0;
            gridBag.setConstraints(findNextButton, gbc);
            panel.add(findNextButton);

            gbc.gridx = 2;
            gbc.gridy = 1;
            gridBag.setConstraints(findAllButton, gbc);
            panel.add(findAllButton);

            gbc.gridx = 2;
            gbc.gridy = 2;
            gridBag.setConstraints(cancelButton, gbc);
            panel.add(cancelButton);

        }

    }//end of the setFindPanel Method
    /**
     * setupFindAndReplacePanel Method -
     * @param panel - the JPanel representing the FindAndReplacePanel
     */
    private void setupFindAndReplacePanel(JPanel panel) {
        GridBagLayout gridBag = new GridBagLayout();
        panel.setLayout(gridBag);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 2, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel findWordLabel = new JLabel("Find What: ");
        gbc.ipadx = 24;
        JLabel replaceWordLabel = new JLabel("Replace With: ");
        gbc.ipadx = 24;

        /*findWordLabel.setFont(new Font("Verdana", Font.BOLD, 13));*/
        findWordLabel.setFont(boldFont);
        /*replaceWordLabel.setFont(new Font("Verdana", Font.BOLD, 13));*/
        replaceWordLabel.setFont(boldFont);

        JButton findNextButton = new JButton("Find Next");
        JButton replaceNextButton = new JButton("Replace Next");
        JButton replaceAllButton = new JButton("Replace All");
        JButton cancelButton = new JButton("Cancel");

        /*findNextButton.setFont(new Font("Verdana", Font.BOLD, 13));*/
        findNextButton.setFont(boldFont);
        gbc.ipadx = 24;
        /*replaceNextButton.setFont(new Font("Verdana", Font.BOLD, 13));*/
        replaceNextButton.setFont(boldFont);
        gbc.ipadx = 24;
        /*        replaceAllButton.setFont(new Font("Verdana", Font.BOLD, 13));*/
        replaceAllButton.setFont(boldFont);
        gbc.ipadx = 24;
        /*cancelButton.setFont(new Font("Verdana", Font.BOLD, 13));*/
        cancelButton.setFont(boldFont);


        findNextButton.addActionListener(this);
        replaceNextButton.addActionListener(this);
        replaceAllButton.addActionListener(this);
        cancelButton.addActionListener(this);

        findNextButton.setEnabled(false);
        replaceNextButton.setEnabled(false);
        replaceAllButton.setEnabled(false);

        findTextField = new JTextField(20);
        replaceTextField = new JTextField(20);

        /*findTextField.setFont(new Font("Verdana", Font.PLAIN, 13));*/
        findTextField.setFont(plainFont);
        /*replaceTextField.setFont(new Font("Verdana", Font.PLAIN, 13));*/
        replaceTextField.setFont(plainFont);

        findTextField.addKeyListener(new KeyAdapter() {
            /**
             * keyReleased Method
             * @param ke - the KeyEvent representing whether a key release event has occurred
             * in the find input text field
             */
            @Override
            public void keyReleased(KeyEvent ke) {
                boolean state = (findTextField.getDocument().getLength() > 0);
                findNextButton.setEnabled(state);
                isFind = false;
            }//end of the keyReleased Method for the findTextField
        });//end of the Anonymous KeyAdapter for the findTextField

        replaceTextField.addKeyListener(new KeyAdapter() {
            /**
             * keyReleased Method -
             * @param ke - the KeyEvent representing whether a key release event has occurred in the
             *           replace input text field
             */
            @Override
            public void keyReleased(KeyEvent ke) {
                boolean state = (replaceTextField.getDocument().getLength() > 0);
                replaceNextButton.setEnabled(state);
                replaceAllButton.setEnabled(state);
                isFind = false;

            }//end of the keyReleased Method for the replaceTextField
        });//end of the Anonymous KeyAdapter for the replaceTextField

        gbc.gridx = 0;
        gbc.gridy = 0;
        gridBag.setConstraints(findWordLabel, gbc);
        panel.add(findWordLabel);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gridBag.setConstraints(findTextField, gbc);
        panel.add(findTextField);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gridBag.setConstraints(findNextButton, gbc);
        panel.add(findNextButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gridBag.setConstraints(replaceWordLabel, gbc);
        panel.add(replaceWordLabel);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gridBag.setConstraints(replaceTextField, gbc);
        panel.add(replaceTextField);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gridBag.setConstraints(replaceNextButton, gbc);
        panel.add(replaceNextButton);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gridBag.setConstraints(replaceAllButton, gbc);
        panel.add(replaceAllButton);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gridBag.setConstraints(cancelButton, gbc);
        panel.add(cancelButton);

    }//end of the setupFindAndReplacePanel Method
    /**
     * addBottomComponents Method -
     * @param panel - the JPanel representing the center panel with its components
     */
    private void addCenterComponents(JPanel panel) {
        JPanel eastPanel = new JPanel();
        JPanel westPanel = new JPanel();
        /** Set the panel to a GridLayout with 1 row and 2 columns */
        panel.setLayout(new GridLayout(1, 2, 4, 4));
        /** Set the eastPanel to a GridLayout with 2 rows and 1 column */
        eastPanel.setLayout(new GridLayout(2, 1, 4,4));
        /** Set the westPanel to a GridLayout with 2 rows and 1 column */
        westPanel.setLayout(new GridLayout(2, 1, 4,4));

        caseSensitiveCheckBox = new JCheckBox("Case Sensitive", true);
        matchExactWordCheckBox = new JCheckBox("Match Exact Word", true);

        /*caseSensitiveCheckBox.setFont(new Font("Verdana", Font.PLAIN, 12));*/
        caseSensitiveCheckBox.setFont(plainFont2);
        /*matchExactWordCheckBox.setFont(new Font("Verdana", Font.PLAIN, 12));*/
        matchExactWordCheckBox.setFont(plainFont2);

        searchUpRadioButton = new JRadioButton("Search Up", false);
        searchDownRadioButton = new JRadioButton("Search Down", true);

        /*searchUpRadioButton.setFont(new Font("Verdana", Font.PLAIN, 12));*/
        searchUpRadioButton.setFont(plainFont2);
        /*searchDownRadioButton.setFont(new Font("Verdana", Font.PLAIN, 12));*/
        searchDownRadioButton.setFont(plainFont2);

        ButtonGroup radioButtonGroup = new ButtonGroup();
        /*radioButtonGroup.insets = new Insets(8, 8, 4, 8);*/
        radioButtonGroup.add(searchUpRadioButton);
        radioButtonGroup.add(searchDownRadioButton);

        eastPanel.add(caseSensitiveCheckBox);
        eastPanel.add(matchExactWordCheckBox);
        eastPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.
                        createMatteBorder(1, 1, 1, 1, Color.black), "Search Options",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Verdana",
                        Font.BOLD, 12), Color.black));

        westPanel.add(searchUpRadioButton);
        westPanel.add(searchDownRadioButton);
        westPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.
                        createMatteBorder(1, 1, 1, 1, Color.black), "Search Directions",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Verdana",
                        Font.BOLD, 12), Color.black));

        panel.add(eastPanel);
        panel.add(westPanel);


    }//end of the addBottomComponents Method
    /**
     * addSouthComponents Method -
     * @param panel - the JPanel representing the panel containing the south components
     */
    private void addSouthComponents(JPanel panel) {
        statusInfoLabel = new JLabel("Status Info: ");
        statusInfoLabel.setHorizontalAlignment(JLabel.LEFT);
        statusInfoLabel.setForeground(Color.blue);
        /*statusInfoLabel.setFont(new Font("Verdana", Font.PLAIN, 13));*/
        statusInfoLabel.setFont(plainFont);
        panel.add(statusInfoLabel);

    }//end of the addSouthComponents Method
    /**
     * findAllWord Method -
     * @param textComponent - the JTextComponent
     * @param wordToFind - a String representing the word to find
     */
    protected void findAllWord(JTextComponent textComponent, String wordToFind) {
        removeAllHighlights(textComponent);
        try {
            Highlighter thisHighlighter = textComponent.getHighlighter();
            Document document = textComponent.getDocument();
            String text = document.getText(0, document.getLength());

            while ((wordPosition = text.toUpperCase().indexOf(wordToFind.toUpperCase(),
                    wordPosition)) >= 0) {
                thisHighlighter.addHighlight(wordPosition, (wordPosition + wordToFind.length()),
                        wordHighlighter);
                wordPosition += wordToFind.length();
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            JOptionPane.showMessageDialog(rootPane, message);
        }
    }//end of the findAllWord Method
    /**
     * findNextWord Method -
     * @param textComponent - the JTextComponent
     * @param wordToFind - a String representing the word to find
     */
    protected void findNextWord(JTextComponent textComponent, String wordToFind) {
        removeAllHighlights(textComponent);
        try {
            Highlighter thisHighlighter = textComponent.getHighlighter();
            Document document = textComponent.getDocument();
            String text = document.getText(0, document.getLength());
            //int wordPosition = 0;

            if (text != null && wordToFind != null) {
                boolean isEndOfSearch = false;
                int start;
                while (!isEndOfSearch) {

                    start = text.indexOf(wordToFind, wordPosition);
                    System.out.println("start is " +start);

                    if (start != - 1) {
                        isEndOfSearch = true;

                        thisHighlighter.addHighlight(start, (start + wordToFind.length()), wordHighlighter);
                        wordPosition = (start + wordToFind.length());
                        System.out.println("wordPosition is "+wordPosition);

                    } else {
                        isEndOfSearch = true;
                        String message = "JavaTextEditor has completed the search for \"" + wordToFind + "\".";
                        JOptionPane.showMessageDialog(rootPane, message);
                    }
                }
            }
        } catch (BadLocationException | HeadlessException ex) {
            String msg = ex.getMessage();
            JOptionPane.showMessageDialog(rootPane, msg);
        }

    }//end of the findNextWord Method
    /**
     * removeHighlights Method - removes the highlights
     * @param textComponent - the JTextComponent
     */
    protected void removeAllHighlights(JTextComponent textComponent) {
        Highlighter thisHighlighter = textComponent.getHighlighter();
        Highlighter.Highlight[] highlights = thisHighlighter.getHighlights();

        for (Highlighter.Highlight theHighlighter : highlights) {
            if (theHighlighter.getPainter()instanceof WordHighlighter) {
                thisHighlighter.removeAllHighlights();

            }
        }

    }//end of the removeHighlights Method
    /**
     * replaceNextWord Method -
     * @param textComponent - the JTextComponent
     * @param wordToFind - a String representing the word to find
     * @param replaceWord - a String representing the word to replace
     */
    protected void replaceNextWord(JTextComponent textComponent, String wordToFind,
                                   String replaceWord) {
        removeAllHighlights(textComponent);
        boolean replacing = true;
        try {
            Highlighter thisHighlighter = textComponent.getHighlighter();
            Document document = textComponent.getDocument();
            String text = document.getText(0, document.getLength());

            if (text != null && wordToFind != null) {
                boolean isEndOfSearch = false;
                int start;
                while (!isEndOfSearch) {

                    start = text.indexOf(wordToFind, wordPosition);
                    System.out.println("start is " +start);

                    isEndOfSearch = true;
                    if (start != - 1) {

                        wordPosition = (start + wordToFind.length());
                        System.out.println("wordPosition is "+wordPosition);
                        if (replacing) {

                            StringBuffer stringBuffer = new StringBuffer(text);
                            System.out.println("at stringBuffer");
                            int difference = (replaceWord.length() - wordToFind.length());
                            int offset = 0;

                            System.out.println("getting to replace");
                            stringBuffer.replace(start, (start  + wordToFind.length()),
                                    replaceWord);

                            textComponent.setText(stringBuffer.toString());
                            thisHighlighter.addHighlight(start, (start + wordToFind.length()), wordHighlighter);
                        }

                    } else {
                        replacing = false;
                        String message = "JavaTextEditor has completed replacing text for \"" + wordToFind + "\".";
                        JOptionPane.showMessageDialog(rootPane, message);
                    }
                }
            }
        } catch (BadLocationException | HeadlessException ex) {
            String msg = ex.getMessage();
            JOptionPane.showMessageDialog(rootPane, msg);
        }

    }//end of the replaceNextWord Method
    /**
     * isSearchingDown Method -
     * @return Boolean - returns true if the checkbox to search down is checked; otherwise it returns
     * false.
     */
    private boolean isSearchingDown() {
        return (searchDownRadioButton.isSelected());

    }//end of the isSearchingDown Method
    /**
     * isMatchExactWordSelected Method -
     * @return Boolean - returns true if the checkbox to match exact word is selected; otherwise it returns
     * false.
     */
    private boolean isMatchExactWordSelected() {
        return (matchExactWordCheckBox.isSelected());

    }//end of isExactWordSelected Method
    /**
     * isCaseSensitiveSelected Method -
     * @return Boolean - returns true if the is case-sensitive check box is selected; otherwise, it returns
     * false.
     */
    private boolean isCaseSensitiveSelected() {
        return (caseSensitiveCheckBox.isSelected());

    }//end of isCaseSensitiveSelected Method
    /**
     * checkForExactWord Method -
     * @param wordLength - int representing the word length
     * @param text - a String representing the text
     * @param add - int representing add
     * @param caretPosition - int representing the caretPosition
     */
    private boolean checkForExactWord(int wordLength, String text, int add, int caretPosition) {
        int offsetLeft = (caretPosition + add) - 1;
        int offsetRight = (caretPosition + add + wordLength);

        if ((offsetLeft < 0) || (offsetRight > text.length())) {
            return true;

        }
        return ((!Character.isLetterOrDigit(text.charAt(offsetLeft))) &&
                (!Character.isLetterOrDigit(text.charAt(offsetRight))));

    }//end of the checkForExactWord Method
    /**
     * getWord Method -
     * @return String - returns the String representing the word to find
     */
    private String getWordToFind() {
        if (isCaseSensitiveSelected()) {
            return findTextField.getText();

        } else {
            return findTextField.getText().toLowerCase();
        }

    }//end of the getWordToFind Method
    /**
     * getWordToReplace Method
     * @return String - a String representing the word to replace
     */
    private String getWordToReplace() {
        if (isCaseSensitiveSelected()) {
            return replaceTextField.getText();

        } else {
            return replaceTextField.getText().toLowerCase();
        }

    }//end of the getWordToReplace Method
    /**
     * getAllText Method - gets all the text if case-sensitive check box is selected
     * @return String - a String representing all the text
     */
    private String getAllText() {
        if (isCaseSensitiveSelected()) {
            return JavaTextEditor.getTextPane().getText();

        } else {
            return JavaTextEditor.getTextPane().getText().toLowerCase();
        }

    }//end of the getAllText Method
    /**
     * replaceAll Method -
     */
    private void replaceAll() {
        String findWord = findTextField.getText();

        String text = getAllText();
        String replaceWord = replaceTextField.getText();
        @SuppressWarnings("StringBufferMayBeStringBuilder")
        StringBuffer stringBuffer = new StringBuffer(text);
        JavaTextEditor.getTextPane().setCaretPosition(0);
        int difference = (replaceWord.length() - findWord.length());
        int offset = 0;
        int foundWords = 0;

        for(int i = 0; i < (text.length() - findWord.length()); i++) {
            String temp = text.substring(i, (i + findWord.length()));
            if ((temp.equals(findWord)) &&
                    (checkForExactWord(findWord.length(), text, 0, i))) {
                foundWords++;
                stringBuffer.replace((i + offset),(i + offset + findWord.length()), replaceWord);
                offset += difference;
            }
        }
        JavaTextEditor.getTextPane().setText(stringBuffer.toString());
        displayEndResult(foundWords, true);
        JavaTextEditor.getTextPane().setCaretPosition(0);

    }//end of the replaceAll Method

    /**
     * updateProcess Method -
     */
    private void updateProcess() {
        if (isReplace) {
            statusInfoLabel.setText("Replacing the text \"" + findTextField.getText() + "\"");

        } else {

            statusInfoLabel.setText("Searching for the text \""+ findTextField.getText() +"\"");
        }
        int caretPosition = JavaTextEditor.getTextPane().getCaretPosition();
        String word = getWordToFind();
        String text = getAllText();
        //caretPosition = findWord(text, word, caretPosition);

        if (caretPosition <= 0) {
            //displayEndResult(0, false);

        }
    }//end of the updateProcess Method
    /**
     * displayEndResult Method -
     * @param foundWords - int representing the number of found words
     * @param isReplaceAll - a boolean representing if isReplaceAll has been clicked
     */
    private void displayEndResult(int foundWords, boolean isReplaceAll) {
        String message = "";
        if (isReplaceAll) {
            if (foundWords == 0) {
                message = "The text \"" + findTextField.getText() + "\" not found.";

            } else if (foundWords == 1) {
                message = "One change was made for the text \"" +
                        findTextField.getText() + "\"";

            } else {
                message = "" + foundWords + " changes were made for the text \"" +
                        findTextField.getText() + "\"";
            }

        } else {
            String string = "";
            if (isSearchingDown()) {
                string = "searching Down ";

            } else {
                string = "searching Up ";

            }
            if (isFind && !isReplace) {
                message = "End of " + string + " for " + findTextField.getText();

            } else if (isFind && isReplace) {
                message = "End of replacing " + findTextField.getText() + " with "
                        + replaceTextField.getText();
            }
        }
        //statusInfoLabel.setText(message);

    }//end of the displayEndResult Method
    /**
     * actionPerformed Method -
     * @param ae - the ActionEvent event of whether the findTextField or replaceTextField or find next
     * or find all or cancel or replace next of replace all has been clicked
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource().equals(findTextField) ||
                (ae.getSource().equals(replaceTextField))) {
            validate();
        }
        if (ae.getActionCommand().equals("Cancel")) {
            dispose();
        }
        if (ae.getActionCommand().equals("Find Next")) {
            findNextWord(JavaTextEditor.getTextPane(), getWordToFind());

        }
        if (ae.getActionCommand().equals("Find All")) {
            findAllWord(JavaTextEditor.getTextPane(), getWordToFind());
        }
        if (ae.getActionCommand().equals("Replace Next")) {
            replaceNextWord(JavaTextEditor.getTextPane(), getWordToFind(), getWordToReplace());
        }
        if (ae.getActionCommand().equals("Replace All")) {
            replaceAll();
        }

    }//end of the actionPerformed Method
}//end of the FindAndReplace Class

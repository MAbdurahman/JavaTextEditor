package net.abdurrahman.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

/**
 * JavaTextEditorFontChooser Class
 *
 * @author: MAbdurrahman
 * @date: 15 June 2024
 * @version: 1.0.0.
 */
public class JavaTextEditorFontChooser extends JComponent {
    /** Instance Variables */
    public static final int APPROVE_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    public static final int ERROR_OPTION = -1;

    private static Font DEFAULT_FONT = new Font("Verdana", Font.PLAIN, 12);
    private static int[] FONT_STYLES = {Font.PLAIN, Font.BOLD, Font.ITALIC,
            Font.BOLD | Font.ITALIC};
    protected int dialogResultValue = ERROR_OPTION;
    private static String[] FONT_SIZES = {"7", "8", "9", "10", "11", "12", "14", "15",
            "16", "17", "18", "19", "20", "22", "24",
            "26", "28", "30", "32", "34", "36", "38",
            "40", "42", "44", "46", "48", "50", "52",
            "54", "56", "58", "60", "64", "68", "70",
            "72"};
    private String[] fontStylesNames = null;
    private String[] fontFamilyNames = null;
    private String[] fontSizeStrings = null;

    private JTextField fontStyleTextField = null;
    private JTextField fontFamilyTextField = null;
    private JTextField fontSizeTextField = null;

    private JTextField previewTextField;

    private JList fontStyleList = null;
    private JList fontFamilyList = null;
    private JList fontSizeList = null;

    private JPanel fontNamePanel = null;
    private JPanel fontStylePanel = null;
    private JPanel fontSizePanel = null;
    private JPanel previewPanel = null;

    JavaTextEditor textEditor;

    /**
     * MemoPadFontChooser Constructor -
     */
    public JavaTextEditorFontChooser() {
        this(FONT_SIZES);

    }//end of the MemoPad Constructor

    /**
     * JavaTextEditorFontChooser Constructor - Creates an instance of MemoPadFontChooser
     * @param fontSizes - String[] of font sizes
     */
    public JavaTextEditorFontChooser(String[] fontSizes) {
        if (fontSizes == null) {
            fontSizeStrings = FONT_SIZES;
        }
        this.fontSizeStrings = fontSizes;

        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
        selectPanel.add(getFontFamilyPanel());
        selectPanel.add(getFontStylePanel());
        selectPanel.add(getFontSizePanel());

        JPanel contentsPanel = new JPanel();
        contentsPanel.setLayout(new GridLayout(2, 1));
        contentsPanel.add(selectPanel, BorderLayout.NORTH);
        contentsPanel.add(getPreviewPanel(), BorderLayout.CENTER);

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(contentsPanel);
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setSelectedFont(DEFAULT_FONT);
    }

    public JTextField getFontFamilyTextField() {
        if (fontFamilyTextField == null) {
            fontFamilyTextField = new JTextField();
            fontFamilyTextField.addFocusListener
                    (new TextFieldFocusHandlerForTextSelection(fontFamilyTextField));
            fontFamilyTextField.addKeyListener
                    (new TextFieldKeyHandlerForListSelectionUpDown(getFontFamilyList()));
            fontFamilyTextField.getDocument().addDocumentListener
                    (new ListSearchTextFieldDocumentHandler(getFontFamilyList()));
            fontFamilyTextField.setFont(DEFAULT_FONT);

        }
        return fontFamilyTextField;
    }

    public JTextField getFontStyleTextField() {
        if (fontStyleTextField == null) {
            fontStyleTextField = new JTextField();
            fontStyleTextField.addFocusListener
                    (new TextFieldFocusHandlerForTextSelection(fontStyleTextField));
            fontStyleTextField.addKeyListener
                    (new TextFieldKeyHandlerForListSelectionUpDown(getFontStyleList()));
            fontStyleTextField.getDocument().addDocumentListener
                    (new ListSearchTextFieldDocumentHandler(getFontStyleList()));
            fontStyleTextField.setFont(DEFAULT_FONT);
        }
        return fontStyleTextField;
    }

    public JTextField getFontSizeTextField() {
        if (fontSizeTextField == null) {
            fontSizeTextField = new JTextField();
            fontSizeTextField.addFocusListener
                    (new TextFieldFocusHandlerForTextSelection(fontSizeTextField));
            fontSizeTextField.addKeyListener
                    (new TextFieldKeyHandlerForListSelectionUpDown(getFontSizeList()));
            fontSizeTextField.getDocument().addDocumentListener
                    (new ListSearchTextFieldDocumentHandler(getFontSizeList()));
            fontSizeTextField.setFont(DEFAULT_FONT);
        }
        return fontSizeTextField;
    }

    public JList getFontFamilyList() {
        if (fontFamilyList == null) {
            fontFamilyList = new JList(getFontFamilies());
            fontFamilyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fontFamilyList.addListSelectionListener
                    (new ListSelectionHandler(getFontFamilyTextField()));
            fontFamilyList.setSelectedIndex(0);
            fontFamilyList.setFont(DEFAULT_FONT);
            fontFamilyList.setFocusable(false);
        }
        return fontFamilyList;
    }

    public JList getFontStyleList() {
        if (fontStyleList == null) {
            fontStyleList = new JList(getFontStyleNames());
            fontStyleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fontStyleList.addListSelectionListener
                    (new ListSelectionHandler(getFontStyleTextField()));
            fontStyleList.setSelectedIndex(0);
            fontStyleList.setFont(DEFAULT_FONT);
            fontStyleList.setFocusable(false);
        }
        return fontStyleList;
    }

    public JList getFontSizeList() {
        if (fontSizeList == null) {
            fontSizeList = new JList(this.fontSizeStrings);
            fontSizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            fontSizeList.addListSelectionListener(new ListSelectionHandler(getFontSizeTextField()));
            fontSizeList.setSelectedIndex(0);
            fontSizeList.setFont(DEFAULT_FONT);
            fontSizeList.setFocusable(false);
        }
        return fontSizeList;
    }

    public String getSelectedFontFamily() {
        String fontName = (String) getFontFamilyList().getSelectedValue();
        return fontName;
    }

    /**
     * Get the style of the selected font.
     * @return the style of the selected font. <code>Font.PLAIN</code>,
     * <code>Font.BOLD</code>, <code>Font.ITALIC</code>,
     * <code>Font.BOLD|Font.ITALIC</code>
     * @see java.awt.Font#PLAIN
     * @see java.awt.Font#BOLD
     * @see java.awt.Font#ITALIC
     * @see #setSelectedFontStyle
     */
    public int getSelectedFontStyle() {
        int index = getFontStyleList().getSelectedIndex();
        return FONT_STYLES[index];
    }

    /**
     * Get the size of the selected font.
     * @return the size of the selected font
     * @see #setSelectedFontSize
     */
    public int getSelectedFontSize() {
        int fontSize = 1;
        String fontSizeString = getFontSizeTextField().getText();
        while (true) {
            try {
                fontSize = Integer.parseInt(fontSizeString);
                break;
            } catch (NumberFormatException e) {
                fontSizeString = (String) getFontSizeList().getSelectedValue();
                getFontSizeTextField().setText(fontSizeString);
            }
        }

        return fontSize;
    }

    /**
     * Get the selected font.
     * @return the selected font
     * @see #setSelectedFont
     * @see java.awt.Font
     */
    public Font getSelectedFont() {
        Font font = new Font(getSelectedFontFamily(), getSelectedFontStyle(), getSelectedFontSize());
        return font;
    }

    /**
     * Set the family name of the selected font.
     * @param name the family name of the selected font.
     */
    public void setSelectedFontFamily(String name) {
        String[] names = getFontFamilies();
        for (int i = 0; i < names.length; i++) {
            if (names[i].toLowerCase().equals(name.toLowerCase())) {
                getFontFamilyList().setSelectedIndex(i);
                break;
            }
        }
        updatePreviewFont();
    }

    /**
     * Set the style of the selected font.
     * @param style the size of the selected font. <code>Font.PLAIN</code>,
     * <code>Font.BOLD</code>, <code>Font.ITALIC</code>, or
     * <code>Font.BOLD|Font.ITALIC</code>.
     * @see java.awt.Font#PLAIN
     * @see java.awt.Font#BOLD
     * @see java.awt.Font#ITALIC
     * @see #getSelectedFontStyle
     */
    public void setSelectedFontStyle(int style) {
        for (int i = 0; i < FONT_STYLES.length; i++) {
            if (FONT_STYLES[i] == style) {
                getFontStyleList().setSelectedIndex(i);
                break;
            }
        }
        updatePreviewFont();
    }

    /**
     * Set the size of the selected font.
     * @param size the size of the selected font
     * @see #getSelectedFontSize
     */
    public void setSelectedFontSize(int size) {
        String sizeString = String.valueOf(size);
        for (int i = 0; i < this.fontSizeStrings.length; i++) {
            if (this.fontSizeStrings[i].equals(sizeString)) {
                getFontSizeList().setSelectedIndex(i);
                break;
            }
        }
        getFontSizeTextField().setText(sizeString);
        updatePreviewFont();
    }

    /**
     * Set the selected font.
     * @param font the selected font
     * @see #getSelectedFont
     * @see java.awt.Font
     */
    public void setSelectedFont(Font font) {
        setSelectedFontFamily(font.getFamily());
        setSelectedFontStyle(font.getStyle());
        setSelectedFontSize(font.getSize());
    }

    public String getVersionString() {
        return ("Version");
    }

    /**
     * Show font selection dialog.
     * @param parent Dialog's Parent component.
     * @return OK_OPTION, CANCEL_OPTION or ERROR_OPTION
     * @see #CANCEL_OPTION
     * @see #ERROR_OPTION
     */
    @SuppressWarnings("UnusedAssignment")
    public int showDialog(Component parent) {
        dialogResultValue = ERROR_OPTION;
        JDialog dialog = createDialog(parent);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialogResultValue = CANCEL_OPTION;
            }
        });

        dialog.setVisible(true);
        dialog.dispose();
        dialog = null;

        return dialogResultValue;
    }

    protected class ListSelectionHandler implements ListSelectionListener {

        @SuppressWarnings("FieldMayBeFinal")
        private JTextComponent textComponent;

        ListSelectionHandler(JTextComponent textComponent) {
            this.textComponent = textComponent;
        }

        /**
         * valueChanged Method -
         * @param e - the ListSelectionEvent
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() == false) {
                JList list = (JList) e.getSource();
                String selectedValue = (String) list.getSelectedValue();

                String oldValue = textComponent.getText();
                textComponent.setText(selectedValue);
                if (!oldValue.equalsIgnoreCase(selectedValue)) {
                    textComponent.selectAll();
                    textComponent.requestFocus();
                }

                updatePreviewFont();
            }
        }
    }

    protected class TextFieldFocusHandlerForTextSelection extends FocusAdapter {

        @SuppressWarnings("FieldMayBeFinal")
        private JTextComponent textComponent;

        public TextFieldFocusHandlerForTextSelection(JTextComponent textComponent) {
            this.textComponent = textComponent;
        }
        /**
         * focusGained Method -
         * @param e - FocusEvent
         */
        @Override
        public void focusGained(FocusEvent e) {
            textComponent.selectAll();
        }
        /**
         * focusLost Method -
         * @param e - FocusEvent
         */
        @Override
        public void focusLost(FocusEvent e) {
            textComponent.select(0, 0);
            updatePreviewFont();
        }
    }

    protected class TextFieldKeyHandlerForListSelectionUpDown extends KeyAdapter {

        @SuppressWarnings("FieldMayBeFinal")
        private JList targetList;

        public TextFieldKeyHandlerForListSelectionUpDown(JList list) {
            this.targetList = list;
        }
        /**
         * keyPressed Method -
         * @param e - the KeyEvent of key being pressed
         */
        @Override
        public void keyPressed(KeyEvent e) {
            @SuppressWarnings("UnusedAssignment")
            int i = targetList.getSelectedIndex();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    i = targetList.getSelectedIndex() - 1;
                    if (i < 0) {
                        i = 0;
                    }
                    targetList.setSelectedIndex(i);
                    break;
                case KeyEvent.VK_DOWN:
                    int listSize = targetList.getModel().getSize();
                    i = targetList.getSelectedIndex() + 1;
                    if (i >= listSize) {
                        i = listSize - 1;
                    }
                    targetList.setSelectedIndex(i);
                    break;
                default:
                    break;
            }
        }
    }

    protected class ListSearchTextFieldDocumentHandler implements DocumentListener {
        /** Instance Variables */
        JList targetList;

        /**
         * ListSearchTextFieldDocumentHandler Constructor - Creates an instance of this Handler
         * @param targetList - the JList
         */
        public ListSearchTextFieldDocumentHandler(JList targetList) {
            this.targetList = targetList;
        }

        /**
         * insertUpdate Method -
         * @param de - the DocumentEvent
         */
        @Override
        public void insertUpdate(DocumentEvent de) {
            update(de);
        }

        /**
         * removeUpdate Method -
         * @param de - the DocumentEvent
         */
        @Override
        public void removeUpdate(DocumentEvent de) {
            update(de);
        }

        /**
         * changedUpdate Method -
         * @param de - the DocumentEvent
         */
        @Override
        public void changedUpdate(DocumentEvent de) {
            update(de);
        }

        /**
         * update Method -
         * @param de - the DocumentEvent
         */
        private void update(DocumentEvent de) {
            String newValue = "";
            try {
                Document doc = de.getDocument();
                newValue = doc.getText(0, doc.getLength());
            } catch (BadLocationException ble) {
                String message = ble.getMessage();
                JOptionPane.showMessageDialog(textEditor, message);
            }

            if (newValue.length() > 0) {
                int index = targetList.getNextMatch(newValue, 0, Position.Bias.Forward);
                if (index < 0) {
                    index = 0;
                }
                targetList.ensureIndexIsVisible(index);

                String matchedName = targetList.getModel().getElementAt(index).toString();
                if (newValue.equalsIgnoreCase(matchedName)) {
                    if (index != targetList.getSelectedIndex()) {
                        SwingUtilities.invokeLater(new ListSelector(index));
                    }
                }
            }
        }

        public class ListSelector implements Runnable {

            @SuppressWarnings("FieldMayBeFinal")
            private int index;

            public ListSelector(int index) {
                this.index = index;
            }
            /**
             * run Method -
             */
            @Override
            public void run() {
                targetList.setSelectedIndex(this.index);
            }
        }
    }

    protected class DialogApproveAction extends AbstractAction {

        protected static final String ACTION_NAME = "OK";
        @SuppressWarnings("FieldMayBeFinal")
        private JDialog dialog;

        protected DialogApproveAction(JDialog dialog) {
            this.dialog = dialog;
            putValue(Action.DEFAULT, ACTION_NAME);
            putValue(Action.ACTION_COMMAND_KEY, ACTION_NAME);
            putValue(Action.NAME, (ACTION_NAME));
        }
        /**
         * actionPerformed Method -
         * @param ae - the ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            dialogResultValue = APPROVE_OPTION;
            dialog.setVisible(false);
        }
    }
    /**
     * DialogCancelAction Class
     */
    protected class DialogCancelAction extends AbstractAction {

        protected static final String ACTION_NAME = "Cancel";
        @SuppressWarnings("FieldMayBeFinal")
        private JDialog dialog;

        protected DialogCancelAction(JDialog dialog) {
            this.dialog = dialog;
            putValue(Action.DEFAULT, ACTION_NAME);
            putValue(Action.ACTION_COMMAND_KEY, ACTION_NAME);
            putValue(Action.NAME, (ACTION_NAME));
        }
        /**
         * actionPerformed Method -
         * @param ae - the ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            dialogResultValue = CANCEL_OPTION;
            dialog.setVisible(false);
        }
    }

    protected JDialog createDialog(Component parent) {
        Frame frame = parent instanceof Frame ? (Frame) parent : (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
        JDialog dialog = new JDialog(frame, ("TextEditor FontChooser"), true);
        Image icon = Toolkit.getDefaultToolkit().getImage(JavaTextEditor.class.getResource("../img/java-texteditor.png"));
        dialog.setIconImage(icon);
        Action approveAction = new DialogApproveAction(dialog);
        Action cancelAction = new DialogCancelAction(dialog);

        JButton approveButton = new JButton(approveAction);
        approveButton.setFont(new Font("Verdana", Font.BOLD, 12));
        approveButton.setText("Approve");

        JButton cancelButton = new JButton(cancelAction);
        cancelButton.setFont(new Font("Verdana", Font.BOLD, 12));
        cancelButton.setText("Cancel");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 1, 0, 16));
        buttonsPanel.add(approveButton);
        buttonsPanel.add(cancelButton);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 10));

        ActionMap actionMap = buttonsPanel.getActionMap();
        actionMap.put(cancelAction.getValue(Action.DEFAULT), cancelAction);
        actionMap.put(approveAction.getValue(Action.DEFAULT), approveAction);
        InputMap inputMap = buttonsPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), cancelAction.getValue(Action.DEFAULT));
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), approveAction.getValue(Action.DEFAULT));

        JPanel dialogEastPanel = new JPanel();
        dialogEastPanel.setLayout(new BorderLayout());
        dialogEastPanel.add(buttonsPanel, BorderLayout.NORTH);

        dialog.getContentPane().add(this, BorderLayout.CENTER);
        dialog.getContentPane().add(dialogEastPanel, BorderLayout.EAST);
        /*dialog.pack();*/
        dialog.setSize(560, 315);
        dialog.setLocationRelativeTo(textEditor);

        return dialog;
    }

    protected void updatePreviewFont() {
        Font font = getSelectedFont();
        getPreviewTextField().setFont(font);
    }

    protected JPanel getFontFamilyPanel() {
        if (fontNamePanel == null) {
            fontNamePanel = new JPanel();
            fontNamePanel.setLayout(new BorderLayout());
            fontNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            fontNamePanel.setPreferredSize(new Dimension(180, 130));

            JScrollPane scrollPane = new JScrollPane(getFontFamilyList());
            scrollPane.getVerticalScrollBar().setFocusable(false);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            JPanel p = new JPanel();
            p.setLayout(new BorderLayout());
            p.add(getFontFamilyTextField(), BorderLayout.NORTH);
            p.add(scrollPane, BorderLayout.CENTER);

            JLabel label = new JLabel(("Font Name"));
            label.setFont(new Font("Verdana", Font.BOLD, 10));
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setHorizontalTextPosition(JLabel.LEFT);
            label.setLabelFor(getFontFamilyTextField());
            label.setDisplayedMnemonic('F');

            fontNamePanel.add(label, BorderLayout.NORTH);
            fontNamePanel.add(p, BorderLayout.CENTER);

        }
        return fontNamePanel;
    }

    protected JPanel getFontStylePanel() {
        if (fontStylePanel == null) {
            fontStylePanel = new JPanel();
            fontStylePanel.setLayout(new BorderLayout());
            fontStylePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            fontStylePanel.setPreferredSize(new Dimension(140, 130));

            JScrollPane scrollPane = new JScrollPane(getFontStyleList());
            scrollPane.getVerticalScrollBar().setFocusable(false);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            JPanel p = new JPanel();
            p.setLayout(new BorderLayout());
            p.add(getFontStyleTextField(), BorderLayout.NORTH);
            p.add(scrollPane, BorderLayout.CENTER);

            JLabel label = new JLabel(("Font Style"));
            label.setFont(new Font("Verdana", Font.BOLD, 10));
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setHorizontalTextPosition(JLabel.LEFT);
            label.setLabelFor(getFontStyleTextField());
            label.setDisplayedMnemonic('Y');

            fontStylePanel.add(label, BorderLayout.NORTH);
            fontStylePanel.add(p, BorderLayout.CENTER);
        }
        return fontStylePanel;
    }

    protected JPanel getFontSizePanel() {
        if (fontSizePanel == null) {
            fontSizePanel = new JPanel();
            fontSizePanel.setLayout(new BorderLayout());
            fontSizePanel.setPreferredSize(new Dimension(70, 130));
            fontSizePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JScrollPane scrollPane = new JScrollPane(getFontSizeList());
            scrollPane.getVerticalScrollBar().setFocusable(false);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            JPanel p = new JPanel();
            p.setLayout(new BorderLayout());
            p.add(getFontSizeTextField(), BorderLayout.NORTH);
            p.add(scrollPane, BorderLayout.CENTER);

            JLabel label = new JLabel(("Font Size"));
            label.setFont(new Font("Verdana", Font.BOLD, 10));
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setHorizontalTextPosition(JLabel.LEFT);
            label.setLabelFor(getFontSizeTextField());
            label.setDisplayedMnemonic('S');

            fontSizePanel.add(label, BorderLayout.NORTH);
            fontSizePanel.add(p, BorderLayout.CENTER);
        }
        return fontSizePanel;
    }

    protected JPanel getPreviewPanel() {
        if (previewPanel == null) {
            Border titledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ("Preview"), TitledBorder.LEFT, TitledBorder.TOP, new Font("Verdana", Font.BOLD, 10));
            Border empty = BorderFactory.createEmptyBorder(5, 10, 10, 10);
            Border border = BorderFactory.createCompoundBorder(titledBorder, empty);

            previewPanel = new JPanel();
            previewPanel.setLayout(new BorderLayout());
            previewPanel.setBorder(border);

            previewPanel.add(getPreviewTextField(), BorderLayout.CENTER);
        }
        return previewPanel;
    }//end of getPreviewPanel Method

    protected JTextField getPreviewTextField() {
        if (previewTextField == null) {
            Border lowered = BorderFactory.createLoweredBevelBorder();

            previewTextField = new JTextField(("AaBbCc"));
            previewTextField.setHorizontalAlignment(JTextField.CENTER);
            previewTextField.setBorder(lowered);
            previewTextField.setPreferredSize(new Dimension(300, 100));
        }
        return previewTextField;
    }//end of getPreviewTextField Method

    protected String[] getFontFamilies() {
        if (fontFamilyNames == null) {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            fontFamilyNames = env.getAvailableFontFamilyNames();
        }
        return fontFamilyNames;
    }//end of getFontFamilies Method

    protected String[] getFontStyleNames() {
        if (fontStylesNames == null) {
            int i = 0;
            fontStylesNames = new String[4];
            fontStylesNames[i++] = ("Plain");
            fontStylesNames[i++] = ("Bold");
            fontStylesNames[i++] = ("Italic");
            fontStylesNames[i++] = ("Bold Italic");
        }
        return fontStylesNames;
    }//end of getFontStyleNames Method

}

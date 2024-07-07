package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.File;

/**
 * JavaTextEditor -
 * @author  MAbdurrahman
 * @date  25 June 2024
 * @version  1.0.0
 */

public class JavaTextEditor extends JFrame {
    /** Instance Variables */
    private StyledDocument styledDocument;
    public static JTextPane TEXTPANE;
    private File currentFile;
    private PrinterJob printJob;
    private PageFormat pageFormat;
    private final Font menuFont = new Font("Lucida Console", Font.BOLD, 14);
    private final Font menuItemFont = new Font("Lucida Console", Font.PLAIN, 14);
    private final Font optionPaneFont = new Font("Lucida Console", Font.BOLD, 14);

    /** JMenuBar attributes for the textArea */
    private JMenuBar menuBar;

    /** Menus for the MenuBar */
    private JMenu fileMenu, editMenu, viewMenu;
    private JMenu formatMenu, helpMenu;

    /** MenuItems for the fileMenu */
    private static JMenuItem newFileItem, newWindowItem, openFileItem, saveFileItem, saveAsFileItem;
    private JMenuItem pageSetupItem, printPageItem, printPreviewItem;
    private JMenuItem exitItem;
    private JMenu printerMenu;

    protected static boolean HAS_CHANGED;
    protected static boolean HAS_LINE_NUMBERS;
    protected static boolean HAS_STATUS_BAR;

    /** MenuItems for the editMenu */
    protected JMenuItem undoItem, redoItem;
    static JMenuItem cutItem, copyItem, deleteItem, pasteItem, findItem, findReplaceItem;
    static JMenuItem selectAllItem, dateTimeItem;

    /** MenuItems for the viewMenu */
    protected JCheckBoxMenuItem lineNumberCheckboxItem;
    protected JCheckBoxMenuItem statusBarCheckboxItem;

    /** MenuItems for the formatMenu */
    private JMenu fontMenu, colorMenu, alignMenu;
    private JMenuItem fontItem, boldItem, italicItem, underlineItem;
    private JMenuItem subscriptItem, superscriptItem, strikeThroughItem;

    private JMenuItem colorBlackItem, colorBlueItem, colorRedItem;
    private JMenuItem moreColorsItem;

    private JMenuItem alignCenterItem, alignJustifyItem, alignLeftItem;
    private JMenuItem alignRightItem;

    /** MenuItem for the helpMenu */
    protected JMenuItem helpItem;
    protected JMenuItem aboutItem;

    /** UndoManager, UndoAction, RedoAction */
    protected UndoManager undoManager;
    protected UndoAction undoAction;
    protected RedoAction redoAction;

    /** CopyAction, CutAction, PasteAction */
    protected static CutAction cutAction;
    protected static CopyAction copyAction;
    protected PasteAction pasteAction;

    /** DateAndTimeAction, DeleteAction, SelectAllAction */
    protected static SelectAllAction selectAllAction;
    protected DateAndTimeAction dateAndTimeAction;
    protected static DeleteAction deleteAction;
    /** FindAction, FindAndReplaceAction */
    protected static FindAction findAction;
    protected static FindAndReplaceAction findAndReplaceAction;

    /** NewFileAction, NewWindowAction, OpenFileAction */
    protected NewFileAction newFileAction;
    protected NewWindowAction newWindowAction;
    protected OpenFileAction openFileAction;

    /** SaveFileAction and SaveAsFileAction */
    protected SaveFileAction saveFileAction;
    protected SaveAsFileAction saveAsFileAction;

    /** PageSetupAction, PrintPageAction, PrintPreviewAction */
    protected PageSetupAction pageSetupAction;
    protected PrintPageAction printPageAction;
    protected PrintPreviewAction printPreviewAction;
    protected ExitAction exitAction;

    /** ViewMenu and its menuItems Abstract Actions */
    LineNumbersAction lineNumbersAction;
    StatusBarAction statusBarAction;

    /**
     * JavaTextEditor Default Constructor
     */
    public JavaTextEditor() {
        super();

        initComponents();

        this.addWindowListener(new WindowAdapter() {
            /**
             * windowClosing Method - Closes the frame for the NotePad
             * @param we - the WindowEvent of closing the frame
             */
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);

            }//end of the windowClosing Method for the Anonymous WindowAdapter
        });//end of the Anonymous WindowAdapter Class
    }// end of default JavaTextEditor Constructor


    private void initComponents() {
        this.setVisible(true);
        this.setTitle("Untitled.txt - TextEditor");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setMinimumSize(new Dimension((int) (screenSize.width / 4), (int) (screenSize.height / 4)));

        int width = (int) (screenSize.width / 1.5);
        int height = (int) (screenSize.height / 1.5);
        this.setSize(width, height);
        this.setLocation((screenSize.width / 2) - (width / 2),
                (screenSize.height / 2) - (height / 2));


        /** The following two lines of code creates and sets a new icon for the frame */
        Image icon = Toolkit.getDefaultToolkit().getImage(JavaTextEditor.class.getResource("../img/java-texteditor.png"));
        setIconImage(icon);

        /** Get the Look and Feel of the system */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException |
                 InstantiationException |
                 IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            String message = ex.getMessage();
            JOptionPane.showConfirmDialog(rootPane, message);
        }
        javax.swing.UIManager.put("OptionPane.font", new Font("Lucida Console", Font.BOLD, 14));

        /** ImageIcon for the JMenus and JMenuItems */
        ImageIcon aboutIcon = new ImageIcon(getClass().getResource("../img/about.png"));
        ImageIcon alignIcon = new ImageIcon(getClass().getResource("../img/align.png"));
        ImageIcon alignCenterIcon = new ImageIcon(getClass().getResource("../img/alignCenter.png"));
        ImageIcon alignJustifyIcon = new ImageIcon(getClass().getResource("../img/alignJustify.png"));
        ImageIcon alignLeftIcon = new ImageIcon(getClass().getResource("../img/alignLeft.png"));
        ImageIcon alignRightIcon = new ImageIcon(getClass().getResource("../img/alignRight.png"));
        ImageIcon boldIcon = new ImageIcon(getClass().getResource("../img/boldIcon.png"));
        ImageIcon colorBlackIcon = new ImageIcon(getClass().getResource("../img/dot-black.png"));
        ImageIcon colorBlueIcon = new ImageIcon(getClass().getResource("../img/dot-blue.png"));
        ImageIcon colorRedIcon = new ImageIcon(getClass().getResource("../img/dot-red.png"));
        ImageIcon colorsIcon = new ImageIcon(getClass().getResource("../img/colors.png"));
        ImageIcon copyIcon = new ImageIcon(getClass().getResource("../img/copy.png"));
        ImageIcon cutIcon = new ImageIcon(getClass().getResource("../img/cut.png"));
        ImageIcon dateTimeIcon = new ImageIcon(getClass().getResource("../img/dateTime.png"));
        ImageIcon deleteIcon = new ImageIcon(getClass().getResource("../img/delete.png"));
        ImageIcon exitIcon = new ImageIcon(getClass().getResource("../img/exit.png"));
        ImageIcon findIcon = new ImageIcon(getClass().getResource("../img/find.png"));
        ImageIcon fontIcon = new ImageIcon(getClass().getResource("../img/font-icon.png"));
        ImageIcon fontsIcon = new ImageIcon(getClass().getResource("../img/font.png"));
        ImageIcon helpIcon = new ImageIcon(getClass().getResource("../img/help.png"));
        ImageIcon italicIcon = new ImageIcon(getClass().getResource("../img/italic.png"));
        ImageIcon lineNumberIcon = new ImageIcon(getClass().getResource("../img/lineNumber.png"));
        ImageIcon moreColorsIcon = new ImageIcon(getClass().getResource("../img/moreColors.png"));
        ImageIcon newFileIcon = new ImageIcon(getClass().getResource("../img/newFile.png"));
        ImageIcon newWindowIcon = new ImageIcon(getClass().getResource("../img/newWindow.png"));
        ImageIcon openFileIcon = new ImageIcon(getClass().getResource("../img/openFile.png"));
        ImageIcon pageSetupIcon = new ImageIcon(getClass().getResource("../img/pageSetup.png"));
        ImageIcon pasteIcon = new ImageIcon(getClass().getResource("../img/paste.png"));
        ImageIcon printIcon = new ImageIcon(getClass().getResource("../img/print.png"));
        ImageIcon printPreviewIcon = new ImageIcon(getClass().getResource("../img/printPreview.png"));
        ImageIcon printerIcon = new ImageIcon(getClass().getResource("../img/printer.png"));
        ImageIcon redoIcon = new ImageIcon(getClass().getResource("../img/redo.png"));
        ImageIcon findReplaceIcon = new ImageIcon(getClass().getResource("../img/findReplace.png"));
        ImageIcon saveFileIcon = new ImageIcon(getClass().getResource("../img/save.png"));
        ImageIcon saveAsFileIcon = new ImageIcon(getClass().getResource("../img/saveAs.png"));
        ImageIcon selectAllIcon = new ImageIcon(getClass().getResource("../img/selectAll.png"));
        ImageIcon statusBarIcon = new ImageIcon(getClass().getResource("../img/statusBar.png"));
        ImageIcon strikeThroughIcon = new ImageIcon(getClass().getResource("../img/strikethrough.png"));
        ImageIcon subscriptIcon = new ImageIcon(getClass().getResource("../img/subscript.png"));
        ImageIcon superscriptIcon = new ImageIcon(getClass().getResource("../img/superscript.png"));
        ImageIcon underlineIcon = new ImageIcon(getClass().getResource("../img/underline.png"));
        ImageIcon undoIcon = new ImageIcon(getClass().getResource("../img/undo.png"));


        /************************* JTextPane and attributes *************************/
        TEXTPANE = new JTextPane();
        TEXTPANE.setFont(new Font("Verdana", Font.PLAIN, 14));
        this.add(TEXTPANE);
        JScrollPane scrollPane = new JScrollPane(TEXTPANE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
        this.setVisible(true);
        HAS_CHANGED = false;
        HAS_LINE_NUMBERS = true;
        HAS_STATUS_BAR = true;

        /** Initialization of UndoManager, undoAction, redoAction, and other editMenuItems */
        undoManager = new UndoManager();
        undoAction = new UndoAction(undoIcon, this);
        redoAction = new RedoAction(redoIcon, this);
        cutAction = new CutAction(cutIcon);
        copyAction = new CopyAction(copyIcon);
        deleteAction = new DeleteAction(deleteIcon);
        try {
            pasteAction = new PasteAction(pasteIcon, this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        findAction = new FindAction(findIcon);
        findAndReplaceAction = new FindAndReplaceAction(findReplaceIcon);
        selectAllAction = new SelectAllAction(selectAllIcon, this);
        dateAndTimeAction = new DateAndTimeAction(dateTimeIcon);


        /************************* Anonymous UndoableEditListener *************************/
        TEXTPANE.getStyledDocument().addUndoableEditListener(new UndoableEditListener() {
            /**
             * undoableEditHappened Method -
             * @param ue - the UndoableEditEvent
             */
            @Override
            public void undoableEditHappened(UndoableEditEvent ue) {
                undoManager.addEdit(ue.getEdit());
                undoAction.updateUndoAction();
                redoAction.updateRedoAction();
                selectAllAction.updateSelectAllAction();
                copyAction.updateCopyAction();
                cutAction.updateCutAction();
                deleteAction.updateDeleteAction();
                findAction.updateFindAction();
                findAndReplaceAction.updateFindAndReplaceAction();


            }//end of the undoableEditHappened Method
        });//end of the Anonymous UndoableEditListener Class

        /************************* Anonymous DocumentListener *************************/
        TEXTPANE.getStyledDocument().addDocumentListener(new DocumentListener() {
            /**
             * changedUpdate Method -
             * @param de - the DocumentEvent
             */
            @Override
            public void changedUpdate(DocumentEvent de) {
                if (de.getDocument().getLength() == 0) {

                }
                TEXTPANE.getDocument().getLength();
                HAS_CHANGED = true;


            }//end of the changedUpdate Method
            /**
             * removeUpdate Method -
             * @param de - the DocumentEvent
             */
            @Override
            public void removeUpdate(DocumentEvent de) {
                de.getDocument().getLength();
                TEXTPANE.getDocument().getLength();
                HAS_CHANGED = true;


            }//end of the removeUpdate Method
            /**
             * insertUpdate Method -
             * @param de - the DocumentEvent
             */
            @Override
            public void insertUpdate(DocumentEvent de) {
                de.getDocument().getLength();
                TEXTPANE.getDocument().getLength();
                HAS_CHANGED = true;


            }//end of the insertUpdate Method
        });//end of the Anonymous DocumentListener

        /************************* Anonymous CaretListener *************************/
        TEXTPANE.addCaretListener(new CaretListener() {
            /**
             * caretUpdate Method -
             * @param ce - the CaretEvent
             */
            @Override
            public void caretUpdate(CaretEvent ce) {
                TEXTPANE.getCaretPosition();

            }//end of the caretUpdate Method
        });//end of the Anonymous Caret Listener

        /************************* create menubar and its attributes *************************/
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        /*********** create the menus, their attributes and add them to the menuBar ***********/
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        formatMenu = new JMenu("Format");
        viewMenu = new JMenu("View");
        helpMenu = new JMenu("Help");

        fileMenu.setFont(menuFont);
        editMenu.setFont(menuFont);
        formatMenu.setFont(menuFont);
        viewMenu.setFont(menuFont);
        helpMenu.setFont(menuFont);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        /** Create the Menu and MenuItem for the fileMenu */
        newFileAction = new NewFileAction(newFileIcon, this);
        newFileItem = new JMenuItem(newFileAction);

        newWindowAction = new NewWindowAction(newWindowIcon, this);
        newWindowItem = new JMenuItem(newWindowAction);

        openFileAction = new OpenFileAction(openFileIcon, this);
        openFileItem = new JMenuItem(openFileAction);

        saveFileAction = new SaveFileAction(saveFileIcon, this);
        saveFileItem = new JMenuItem(saveFileAction);

        saveAsFileAction = new SaveAsFileAction(saveAsFileIcon, this);
        saveAsFileItem = new JMenuItem(saveAsFileAction);

        pageSetupAction  = new PageSetupAction(pageSetupIcon, this);
        pageSetupItem = new JMenuItem(pageSetupAction);

        printerMenu = new JMenu("Printer...");
        printerMenu.setIcon(printerIcon);

        printPageAction  = new PrintPageAction(printIcon, this);
        printPageItem = new JMenuItem(printPageAction);

        printPreviewAction = new PrintPreviewAction(printPreviewIcon, this);
        printPreviewItem = new JMenuItem(printPreviewAction);

        exitAction  = new ExitAction(exitIcon, this);
        exitItem = new JMenuItem(exitAction);

        printerMenu.setFont(menuItemFont);
        newFileItem.setFont(menuItemFont);
        newWindowItem.setFont(menuItemFont);
        openFileItem.setFont(menuItemFont);
        saveFileItem.setFont(menuItemFont);
        saveAsFileItem.setFont(menuItemFont);
        pageSetupItem.setFont(menuItemFont);
        printPageItem.setFont(menuItemFont);
        printPreviewItem.setFont(menuItemFont);
        exitItem.setFont(menuItemFont);

        /** Shortcut KeyStrokes for JMenuItems for the fileMenu */
        newFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        openFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        saveFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveAsFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        printPageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_MASK | InputEvent.ALT_DOWN_MASK));

        /************************* fileMenu and add its menuItems *************************/
        fileMenu.add(newFileItem);
        fileMenu.add(newWindowItem);
        fileMenu.add(openFileItem);
        fileMenu.addSeparator();
        fileMenu.add(saveFileItem);
        fileMenu.add(saveAsFileItem);
        fileMenu.addSeparator();
        fileMenu.add(pageSetupItem);
        fileMenu.add(printerMenu);
        fileMenu.addSeparator();
        printerMenu.add(printPageItem);
        printerMenu.addSeparator();
        printerMenu.add(printPreviewItem);
        fileMenu.add(exitItem);

        /************************* editMenu and add its menuItems *************************/
        undoItem = new JMenuItem(undoAction);
        redoItem = new JMenuItem(redoAction);
        cutItem = new JMenuItem(cutAction);
        copyItem = new JMenuItem(copyAction);
        pasteItem = new JMenuItem(pasteAction);
        deleteItem = new JMenuItem(deleteAction);
        findItem = new JMenuItem(findAction);
        findReplaceItem = new JMenuItem(findAndReplaceAction);
        selectAllItem = new JMenuItem(selectAllAction);
        dateTimeItem = new JMenuItem(dateAndTimeAction);

        undoItem.setFont(menuItemFont);
        redoItem.setFont(menuItemFont);
        cutItem.setFont(menuItemFont);
        copyItem.setFont(menuItemFont);
        pasteItem.setFont(menuItemFont);
        deleteItem.setFont(menuItemFont);
        findItem.setFont(menuItemFont);
        findReplaceItem.setFont(menuItemFont);
        selectAllItem.setFont(menuItemFont);
        dateTimeItem.setFont(menuItemFont);


        /** Shortcut keystrokes for the editMenu JMenuItems */
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.ALT_MASK));
        selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.addSeparator();
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(deleteItem);
        editMenu.addSeparator();
        editMenu.add(findItem);
        editMenu.add(findReplaceItem);
        editMenu.addSeparator();
        editMenu.add(selectAllItem);
        editMenu.addSeparator();
        editMenu.add(dateTimeItem);

        /************* Create the Menus and MenuItems for the formatMenu *************/
        fontMenu = new JMenu("Fonts...");
        alignMenu = new JMenu("Alignments...");
        colorMenu = new JMenu("Colors...");

        fontMenu.setIcon(fontIcon);
        alignMenu.setIcon(alignIcon);
        colorMenu.setIcon(colorsIcon);

        fontMenu.setFont(menuItemFont);
        alignMenu.setFont(menuItemFont);
        colorMenu.setFont(menuItemFont);

        formatMenu.add(fontMenu);
        formatMenu.addSeparator();
        formatMenu.add(alignMenu);
        formatMenu.addSeparator();
        formatMenu.add(colorMenu);

        fontItem = new JMenuItem(new JavaTextEditorFontChooserAction());
        boldItem = new JMenuItem(new StyledEditorKit.BoldAction());
        italicItem = new JMenuItem(new StyledEditorKit.ItalicAction());
        underlineItem = new JMenuItem(new StyledEditorKit.UnderlineAction());
        subscriptItem = new JMenuItem(new SubscriptAction());
        superscriptItem = new JMenuItem(new SuperscriptAction());
        strikeThroughItem = new JMenuItem(new StrikeThroughAction());

        fontItem.setFont(menuItemFont);
        boldItem.setFont(menuItemFont);
        italicItem.setFont(menuItemFont);
        underlineItem.setFont(menuItemFont);
        subscriptItem.setFont(menuItemFont);
        superscriptItem.setFont(menuItemFont);
        strikeThroughItem.setFont(menuItemFont);

        fontItem.setText("Fonts");
        boldItem.setText("Bold");
        italicItem.setText("Italic");
        underlineItem.setText("Underline");
        subscriptItem.setText("Subscript");
        superscriptItem.setText("Superscript");
        strikeThroughItem.setText("Strikethrough");

        fontItem.setIcon(fontsIcon);
        boldItem.setIcon(boldIcon);
        italicItem.setIcon(italicIcon);
        underlineItem.setIcon(underlineIcon);
        subscriptItem.setIcon(subscriptIcon);
        superscriptItem.setIcon(superscriptIcon);
        strikeThroughItem.setIcon(strikeThroughIcon);

        /** Shortcut KeyStroke for the JMenuItems in formatMenu */
        fontItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.CTRL_MASK));
        boldItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        italicItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        underlineItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        subscriptItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, InputEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        superscriptItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, InputEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        strikeThroughItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9, InputEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));

        fontMenu.add(fontItem);
        fontMenu.addSeparator();
        fontMenu.add(boldItem);
        fontMenu.addSeparator();
        fontMenu.add(italicItem);
        fontMenu.addSeparator();
        fontMenu.add(underlineItem);
        fontMenu.addSeparator();
        fontMenu.add(subscriptItem);
        fontMenu.addSeparator();
        fontMenu.add(superscriptItem);
        fontMenu.addSeparator();
        fontMenu.add(strikeThroughItem);

        /** AlignMenu and its JMenuItems and their attributes */
        alignLeftItem = new JMenuItem(new StyledEditorKit.AlignmentAction("Left Alignment", StyleConstants.ALIGN_LEFT));
        alignCenterItem = new JMenuItem(new StyledEditorKit.AlignmentAction("Center Alignment", StyleConstants.ALIGN_CENTER));
        alignRightItem = new JMenuItem(new StyledEditorKit.AlignmentAction("Right Alignment", StyleConstants.ALIGN_RIGHT));
        alignJustifyItem = new JMenuItem(new StyledEditorKit.AlignmentAction("Justify Alignment", StyleConstants.ALIGN_JUSTIFIED));

        alignLeftItem.setIcon(alignLeftIcon);
        alignCenterItem.setIcon(alignCenterIcon);
        alignRightItem.setIcon(alignRightIcon);
        alignJustifyItem.setIcon(alignJustifyIcon);

        alignLeftItem.setFont(menuItemFont);
        alignCenterItem.setFont(menuItemFont);
        alignRightItem.setFont(menuItemFont);
        alignJustifyItem.setFont(menuItemFont);

        alignMenu.add(alignLeftItem);
        alignMenu.addSeparator();
        alignMenu.add(alignCenterItem);
        alignMenu.addSeparator();
        alignMenu.add(alignRightItem);
        alignMenu.addSeparator();
        alignMenu.add(alignJustifyItem);

        /** ColorMenu and its JMenuItems and their attributes */
        colorBlackItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Black", Color.decode("#2e2e2e")));
        colorBlueItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Blue", Color.decode("#377099")));
        colorRedItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Red", Color.decode("#a50303")));
        moreColorsItem = new JMenuItem("More Colors...", moreColorsIcon);

        colorBlackItem.setIcon(colorBlackIcon);
        colorBlueItem.setIcon(colorBlueIcon);
        colorRedItem.setIcon(colorRedIcon);

        colorBlackItem.setForeground(Color.decode("#2e2e2e"));
        colorBlackItem.setFont(menuItemFont);
        colorBlueItem.setForeground(Color.decode("#377099"));
        colorBlueItem.setFont(menuItemFont);
        colorRedItem.setForeground(Color.decode("#a50303"));
        colorRedItem.setFont(menuItemFont);
        moreColorsItem.setFont(menuItemFont);

        moreColorsItem.addActionListener(new JavaTextEditorColorChooser());

        colorMenu.add(colorBlackItem);
        colorMenu.addSeparator();
        colorMenu.add(colorBlueItem);
        colorMenu.addSeparator();
        colorMenu.add(colorRedItem);
        colorMenu.addSeparator();
        colorMenu.add(moreColorsItem);


        /************* Create the CheckboxMenuItems for the viewMenu **************/
        lineNumbersAction = new LineNumbersAction(lineNumberIcon, this);
        lineNumberCheckboxItem = new JCheckBoxMenuItem(lineNumbersAction);

        statusBarAction = new StatusBarAction(statusBarIcon, this);
        statusBarCheckboxItem = new JCheckBoxMenuItem(statusBarAction);

        lineNumberCheckboxItem.setFont(menuItemFont);
        statusBarCheckboxItem.setFont(menuItemFont);

        lineNumberCheckboxItem.setSelected(HAS_LINE_NUMBERS);
        statusBarCheckboxItem.setSelected(HAS_STATUS_BAR);

        viewMenu.add(lineNumberCheckboxItem);
        viewMenu.addSeparator();
        viewMenu.add(statusBarCheckboxItem);

        /************************* helpMenu and its menuItems *************************/
        /**Create the MenuItem for the helpMenu*/
        helpItem = new JMenuItem("View Help", helpIcon);
        aboutItem = new JMenuItem("About Editor", aboutIcon);

        helpItem.setFont(menuItemFont);
        aboutItem.setFont(menuItemFont);

        /** Shortcut KeyStroke for the JMenuItem in the helpMenu */
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK));
        helpItem.addActionListener(new HelpDialog(this, "View Help", true));
        aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK));
        aboutItem.addActionListener(new AboutDialog(this, "About Editor", true));

        helpMenu.add(helpItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);

    }//end of initComponents Method

    /**
     * getTextPane Method -
     * @return JTextPane
     */
    public static JTextPane getTextPane() {
        return  TEXTPANE;

    }//end of the getTextPane Method

    /**
     * removeExtraCharacters Method - removes the last thirteen characters from a String
     * @param text - the specified String
     * @return String - a String with the last thirteen characters removed
     */
    public static String removeExtraCharacters(String text) {
        return text.substring(0, text.length() - 13);

    }//end of removeExtraCharacters Method

    /************************* UndoAction and RedoAction Classes *************************/
    /**
     * UndoAction Class
     */
    class UndoAction extends AbstractAction {
        JavaTextEditor javaTextEditor;
        /**
         * UndoAction Constructor -
         * @param icon - ImageIcon
         */
        @SuppressWarnings("OverridableMethodCallInConstructor")
        public UndoAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
            super("Undo", icon);
            setEnabled(false);
            this.javaTextEditor = javaTextEditor;

        }//end of the UndoAction Constructor
        /**
         * actionPerformed Method -
         * @param ae - the ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                undoManager.undo();


            } catch (CannotUndoException ex) {
                String message = ex.getMessage();
                JOptionPane.showMessageDialog(TEXTPANE, message);

            }
            updateUndoAction();
            redoAction.updateRedoAction();

        }//end of the actionPerformed Method
        /**
         * updateUndoAction Method -
         */
        protected void updateUndoAction() {
            if (undoManager.canUndo()) {
                setEnabled(true);
                putValue(Action.NAME, "Undo");

            } else {
                setEnabled(false);
                putValue(Action.NAME, "Undo");

            }

        }//end of the updateUndoAction Method
    }//end of the UndoAction Class
    /**
     * RedoAction Class -
     */
    class RedoAction extends AbstractAction {
        //Instance variables
        JavaTextEditor javaTextEditor;
        /**
         * RedoAction Constructor -
         * @param icon - the ImageIcon
         */
        @SuppressWarnings("OverridableMethodCallInConstructor")
        public RedoAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
            super("Redo", icon);
            setEnabled(false);
            this.javaTextEditor = javaTextEditor;

        }//end of the RedoAction Constructor
        /**
         * actionPerformed Method -
         * @param ae - the ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                undoManager.redo();

            } catch (CannotRedoException ex) {
                String message = ex.getMessage();
                JOptionPane.showMessageDialog(TEXTPANE, message);

            }
            updateRedoAction();
            undoAction.updateUndoAction();

        }//end of the actionPerformed Method
        /**
         * updateRedoAction Method -
         */
        protected void updateRedoAction() {
            if (undoManager.canRedo()) {
                setEnabled(true);
                putValue(Action.NAME, "Redo");

            } else {
                setEnabled(false);
                putValue(Action.NAME, "Redo");
            }
        }//end of the updateRedoAction Method
    }//end of the RedoAction Class

    /**
     * AboutDialog Class -
     */
    public class AboutDialog extends JDialog implements ActionListener {
        //Instance Variables
        private final JButton okayButton;
        private final JTextArea aboutTextArea;
        private final JPanel textPanel, buttonPanel;

        /**
         * AboutDialog Constructor -
         * @param frame - the JFrame representing the parent frame
         * @param title - the String representing the title
         * @param modal - a Boolean representing the modal
         */
        @SuppressWarnings({"OverridableMethodCallInConstructor", "LeakingThisInConstructor"})
        public AboutDialog(JFrame frame, String title, Boolean modal) {
            super(frame, title, modal);

            /** The following 2 lines of code creates a null icon for the JDialog */
            Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
            setIconImage(icon);
            setBounds(500, 75, 544, 216);
            StringBuilder text = new StringBuilder();
            setFont(new Font("Lucida Console", Font.PLAIN, 12));
            text.append("TextEditor is a stand-alone application that allows users to create and\n");
            text.append("manipulate computer files with the extensions, .cpp, .css, .html, .java\n");
            text.append(".js, .rtf, and .txt. This application not only allows for the creation\n");
            text.append("and storage of these text files, but users can manipulate text fonts,\n");
            text.append("colors, and alignments. In addition to allowing basic functions like\n");
            text.append("cut, copy, and paste, users are allowed to find and replace desired\n");
            text.append("specified text. \n\n");
            text.append("@author:  Mahdi Abdurrahman\n");
            text.append("@date:  25 June 2024\n");
            text.append("@version:  1.0.0");

            aboutTextArea = new JTextArea(30, 1);
            aboutTextArea.setFont(new Font("Lucida Console", Font.PLAIN, 11));
            aboutTextArea.setText(text.toString());
            aboutTextArea.setEditable(false);

            okayButton = new JButton(" OK ");
            okayButton.setFont(new Font("Lucida Console", Font.BOLD, 13));
            okayButton.addActionListener(this);

            addWindowListener(new WindowAdapter() {
                /**
                 * windowClosing Method -
                 * @param we - the WindowEvent
                 */
                @Override
                public void windowClosing(WindowEvent we) {
                    Window window = we.getWindow();
                    window.dispose();

                }//end of the windowClosing Method for the Anonymous WindowAdapter
            });//end of the Anonymous WindowAdapter

            textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            textPanel.add(aboutTextArea);

            buttonPanel.add(okayButton);

            getContentPane().add(textPanel, BorderLayout.CENTER);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            setLocationRelativeTo(JavaTextEditor.getTextPane());

        }//end of the AboutDialog Constructor
        /**
         * actionPerformed Method -
         * @param ae - the ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == aboutItem) {
                this.setVisible(true);
            }
            if (ae.getSource() == okayButton) {
                this.dispose();
            }
        }//end of the actionPerformed Method
    }//end of the AboutDialog Class
    /**
     * HelpDialog Class -
     */
    public class HelpDialog extends JDialog implements ActionListener {
        //Instance variables
        private final JButton okayButton;
        private final JTextArea helpTextArea;
        private final JPanel textPanel, buttonPanel;

        /**
         * HelpDialog Constructor -
         * @param frame - the JFrame representing the parent frame
         * @param title - the String representing the title
         * @param modal - a Boolean representing the modal
         */
        @SuppressWarnings({"OverridableMethodCallInConstructor", "LeakingThisInConstructor"})
        public HelpDialog(JFrame frame, String title, Boolean modal) {
            super(frame, title, modal);

            /** The following 2 lines of code creates a null icon for the JDialog */
            Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
            setIconImage(icon);

            setBounds(500, 75, 500, 180);
            setFont(new Font("Lucida Console", Font.PLAIN, 12));
            StringBuilder text = new StringBuilder();
            text.append("This program is free software.  You can distribute  it and/or \n");
            text.append("modify it under the terms of the GNU General Public License \n");
            text.append("as published by the Free Software Foundation; either version \n");
            text.append("1.0.0 of the License, or (at your option) any later rendition.\n\n");
            text.append("This program is distributed in hope that it will be useful, but \n");
            text.append("WITHOUT ANY WARRANTY; without even the implied warranty of \n");
            text.append("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.\n");

            helpTextArea = new JTextArea(20, 1);
            helpTextArea.setFont(new Font("Lucida Console", Font.PLAIN, 11));
            helpTextArea.setText(text.toString());
            helpTextArea.setEditable(false);

            okayButton = new JButton(" OK ");
            okayButton.setFont(new Font("Lucida Console", Font.BOLD, 13));
            okayButton.addActionListener(this);

            addWindowListener(new WindowAdapter() {
                /**
                 * windowClosing Method -
                 * @param we - the WindowEvent
                 */
                @Override
                public void windowClosing(WindowEvent we) {
                    Window window = we.getWindow();
                    window.dispose();

                }//end of the windowClosing Method for the Anonymous WindowAdapter
            });//end of the Anonymous WindowAdapter

            textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            textPanel.add(helpTextArea);
            buttonPanel.add(okayButton);

            getContentPane().add(textPanel, BorderLayout.CENTER);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);
            setLocationRelativeTo(JavaTextEditor.getTextPane());

        }//end of the HelpDialog Constructor
        /**
         * actionPerformed Method -
         * @param ae - the ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == helpItem) {
                this.setVisible(true);

            }
            if (ae.getSource() == okayButton) {
                this.dispose();

            }
        }//end of the actionPerformed Method
    }//end of the HelpDialog Class

    /**
     * main Method - contains the command line arguments
     * @param args - String[] representing the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            /**
             * run Method - implements the one method, run of Interface Runnable
             */
            @Override
            public void run() {
                new JavaTextEditor();

            }// end of the run Method for the Anonymous Runnable
        });// end of the Anonymous Runnable
    }// end of the main Method
}//end of JavaTextEditor Class

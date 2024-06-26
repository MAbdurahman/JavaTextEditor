package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.*;
import javax.swing.text.DateFormatter;
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
import java.util.ArrayList;

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
    private static JMenuItem newItem, openItem, saveItem, saveAsItem;
    private JMenuItem pageSetupItem, printItem, previewPrintItem;
    private JMenuItem exitItem;
    private JMenu printerMenu;

    protected boolean hasChanged;
    protected boolean hasLineNumbers;
    protected boolean hasStatusBar;
    protected boolean hasWordWrap;

    /** MenuItems for the editMenu */
    protected JMenuItem undoItem, redoItem;
    static JMenuItem cutItem, copyItem, deleteItem, pasteItem, findItem, findReplaceItem;
    protected JMenuItem selectAllItem, dateTimeItem;

    /** MenuItems for the viewMenu */
    protected JCheckBoxMenuItem lineNumberCheckboxItem;
    protected JCheckBoxMenuItem statusBarCheckboxItem;
    protected JCheckBoxMenuItem wordWrapCheckboxItem;

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
        this.setTitle("Untitled - TextEditor");
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
        ImageIcon openFileIcon = new ImageIcon(getClass().getResource("../img/openFile.png"));
        ImageIcon pageSetupIcon = new ImageIcon(getClass().getResource("../img/pageSetup.png"));
        ImageIcon pasteIcon = new ImageIcon(getClass().getResource("../img/paste.png"));
        ImageIcon printIcon = new ImageIcon(getClass().getResource("../img/print.png"));
        ImageIcon printPreviewIcon = new ImageIcon(getClass().getResource("../img/printPreview.png"));
        ImageIcon printerIcon = new ImageIcon(getClass().getResource("../img/printer.png"));
        ImageIcon redoIcon = new ImageIcon(getClass().getResource("../img/redo.png"));
        ImageIcon findReplaceIcon = new ImageIcon(getClass().getResource("../img/findReplace.png"));
        ImageIcon rulerIcon = new ImageIcon(getClass().getResource("../img/ruler.png"));
        ImageIcon saveFileIcon = new ImageIcon(getClass().getResource("../img/save.png"));
        ImageIcon saveAsFileIcon = new ImageIcon(getClass().getResource("../img/saveAs.png"));
        ImageIcon selectAllIcon = new ImageIcon(getClass().getResource("../img/selectAll.png"));
        ImageIcon statusBarIcon = new ImageIcon(getClass().getResource("../img/statusBar.png"));
        ImageIcon strikeThroughIcon = new ImageIcon(getClass().getResource("../img/strikethrough.png"));
        ImageIcon subscriptIcon = new ImageIcon(getClass().getResource("../img/subscript.png"));
        ImageIcon superscriptIcon = new ImageIcon(getClass().getResource("../img/superscript.png"));
        ImageIcon underlineIcon = new ImageIcon(getClass().getResource("../img/underline.png"));
        ImageIcon undoIcon = new ImageIcon(getClass().getResource("../img/undo.png"));
        ImageIcon wordWrapIcon = new ImageIcon(getClass().getResource("../img/wordWrap.png"));

        /************************* JTextpane and attributes *************************/
        TEXTPANE = new JTextPane();
        TEXTPANE.setFont(new Font("Verdana", Font.PLAIN, 14));
        this.add(TEXTPANE);
        JScrollPane scrollPane = new JScrollPane(TEXTPANE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
        this.setVisible(true);
        hasChanged = false;
        hasLineNumbers = true;
        hasStatusBar = true;
        hasWordWrap = true;






    }//end of initComponents Method


    /**
     * getTextPane Method -
     * @return JTextPane
     */
    public static JTextPane getTextPane() {
        return  TEXTPANE;

    }//end of the getTextPane Method

    /************************* UndoAction and RedoAction Classes *************************/
    /**
     * UndoAction Class
     */
    class UndoAction extends AbstractAction {
        /**
         * UndoAction Constructor -
         * @param icon - ImageIcon
         */
        @SuppressWarnings("OverridableMethodCallInConstructor")
        public UndoAction(ImageIcon icon) {
            super("Undo", icon);
            setEnabled(false);

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
        /**
         * RedoAction Constructor -
         * @param icon - the ImageIcon
         */
        @SuppressWarnings("OverridableMethodCallInConstructor")
        public RedoAction(ImageIcon icon) {
            super("Redo", icon);
            setEnabled(false);

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
            text.append("@date:  15 June 2024\n");
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

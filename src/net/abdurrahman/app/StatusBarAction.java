package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * StatusBarAction Class
 * @author MAbdurrahman
 * @version 1.0.0
 * @date 26 June 2024
 */
public class StatusBarAction extends AbstractAction implements CaretListener {
    //Instance variables
    JavaTextEditor javaTextEditor;
    JPanel statusBarPanel;
    static JLabel statusBarLabel;
    static JLabel CRLFLabel;
    static JLabel UTF8Label;
    static String spaces_13 = "             ";
    Border leftBorder = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY);
    JPanel ut8Panel, crlfPanel;
    JPanel statusPanel;
    protected static int LINE_NUMBER;
    protected static int COLUMN_NUMBER;
    Container container;

    /**
     * StatusBarAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public StatusBarAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Status Bar", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;
        this.statusBarPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.ut8Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.crlfPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ut8Panel.setBorder(leftBorder);
        crlfPanel.setBorder(leftBorder);
        statusPanel.setBorder(leftBorder);

        statusBarLabel = new JLabel();
        statusBarLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        CRLFLabel = new JLabel("Windows (CRLF)" + spaces_13);
        CRLFLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        UTF8Label = new JLabel("UTF-8" + spaces_13);
        UTF8Label.setAlignmentX(Component.LEFT_ALIGNMENT);

        ut8Panel.add(UTF8Label);
        crlfPanel.add(CRLFLabel);
        statusPanel.add(statusBarLabel);

        statusBarPanel.setBorder(new CompoundBorder(new LineBorder(Color.LIGHT_GRAY, 0), new EmptyBorder(0, 0, 0, 0)));

        statusBarPanel.add(statusPanel);
        statusBarPanel.add(crlfPanel);
        statusBarPanel.add(ut8Panel);

        this.container = JavaTextEditor.getContainer();
        container.add(statusBarPanel, BorderLayout.SOUTH);
        LINE_NUMBER = getLineNumber();
        COLUMN_NUMBER = getColumnNumber();
        updateStatusBar(LINE_NUMBER, COLUMN_NUMBER);

    }//end of the StatusBarAction Constructor

    /**
     * actionPerformed Method -
     * @param ae - the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        JavaTextEditor.HAS_STATUS_BAR = !JavaTextEditor.HAS_STATUS_BAR;
        statusBarPanel.setVisible(JavaTextEditor.HAS_STATUS_BAR);
        updateStatusBar(LINE_NUMBER, COLUMN_NUMBER);

    }//end of actionPerformed Method

    /**
     * caretUpdate Method -
     * @param ce - the caret event
     */
    @Override
    public void caretUpdate(CaretEvent ce) {
        try {

            LINE_NUMBER = getLineNumber();
            COLUMN_NUMBER = getColumnNumber();

        } catch (Exception ex) {
        }

        updateStatusBar(LINE_NUMBER, COLUMN_NUMBER);
    }

    protected static void updateStatusBar(int lineNumber, int columnNumber) {
        statusBarLabel.setText("Line: " + lineNumber + ", Column: " + columnNumber + spaces_13);

    }//end of updateStatusBar Method

    /**
     * getLineNumber Method -
     * @return int - the current line number
     */
    protected static int getLineNumber() {
        int caretPosition = JavaTextEditor.TEXTPANE.getCaretPosition();
        int rowNumber = (caretPosition == 0) ? 1 : 0;

        for (int offset = caretPosition; offset > 0; ) {
            try {
                offset = Utilities.getRowStart(JavaTextEditor.TEXTPANE, offset) - 1;
                rowNumber++;
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        }

        return rowNumber;

    }//end of getLineNumber Method

    /**
     * getColumnNumber Method -
     * @return int - the current column number
     */
    protected static int getColumnNumber() {
        int caretPosition = JavaTextEditor.TEXTPANE.getCaretPosition();
        int columnNumber;
        try {
            int offset = Utilities.getRowStart(JavaTextEditor.TEXTPANE, caretPosition);
            columnNumber = caretPosition - offset + 1;
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

        return columnNumber;

    }//end of getColumnNumber Method
}//end of StatusBarAction Class

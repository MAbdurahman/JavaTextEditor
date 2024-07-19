package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
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
 * @date 26 June 2024
 * @version 1.0.0
 */
public class StatusBarAction extends AbstractAction implements CaretListener {
    //Instance variables
    JavaTextEditor javaTextEditor;
    JTextArea editArea;
    JPanel statusBarPanel;
    static JLabel statusBarLabel;
    static JLabel CRLFLabel;
    static JLabel UTF8Label;

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
        this.statusBarLabel = new JLabel();
        statusBarLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.CRLFLabel = new JLabel("Windows (CRLF)    ");
        CRLFLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.UTF8Label = new JLabel("UTF-8     ");
        UTF8Label.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusBarPanel.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY), new EtchedBorder()));
        statusBarPanel.add(statusBarLabel);
        statusBarPanel.add(CRLFLabel);
        statusBarPanel.add(UTF8Label);

        this.container = javaTextEditor.getContainer();
        container.add(statusBarPanel, BorderLayout.SOUTH);
        LINE_NUMBER = 1;
        COLUMN_NUMBER = 1;
        updateStatusBar(LINE_NUMBER, COLUMN_NUMBER);

    }//end of the StatusBarAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        JavaTextEditor.HAS_STATUS_BAR = !JavaTextEditor.HAS_STATUS_BAR;
        statusBarPanel.setVisible(JavaTextEditor.HAS_STATUS_BAR);
        updateStatusBar(LINE_NUMBER, COLUMN_NUMBER);

    }//end of actionPerformed Method


    @Override
    public void caretUpdate(CaretEvent ce) {
        editArea = (JTextArea) ce.getSource();


        try {

            int caretPosition = editArea.getCaretPosition();
            LINE_NUMBER = editArea.getLineOfOffset(caretPosition);
            System.out.println(LINE_NUMBER);
            COLUMN_NUMBER = caretPosition - editArea.getLineStartOffset(LINE_NUMBER) + 1;


        } catch (Exception ex) {}

        updateStatusBar(LINE_NUMBER, COLUMN_NUMBER);
    }

    protected static void updateStatusBar(int lineNumber, int columnNumber) {
        statusBarLabel.setText("Line: " + lineNumber + ", Column: " + columnNumber + "     ");

    }//end of updateStatusBar Method

    protected static int getLineNumber() {
        int caretPosition = JavaTextEditor.TEXTPANE.getCaretPosition();
        int rowNumber = (caretPosition == 0) ? 1 : 0;

        for (int offset = caretPosition; offset > 0;) {
            try {
                offset = Utilities.getRowStart(JavaTextEditor.TEXTPANE, offset) - 1;
                rowNumber++;
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        }

        return rowNumber;
    }//end of getLineNumber Method

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
    }
}//end of StatusBarAction Class

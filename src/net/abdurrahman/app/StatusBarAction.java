package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
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
        statusBarPanel.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY), new EtchedBorder()));
        statusBarPanel.add(statusBarLabel);

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
        statusBarLabel.setText("Line: " + lineNumber + ", Column: " + columnNumber);

    }//end of updateStatusBar Method
}//end of StatusBarAction Class

package net.abdurrahman.app;

import java.awt.event.ActionEvent;
import javax.swing.*;
import static net.abdurrahman.app.JavaTextEditor.TEXTPANE;

/**
 * SelectAllAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class SelectAllAction extends AbstractAction {

    JavaTextEditor textEditor;
    /**
     * SelectionAllAction Constructor -
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SelectAllAction(ImageIcon icon) {
        super("SelectAll", icon);
        setEnabled(!TEXTPANE.getText().equals(""));

    }//end of the SelectionAllAction Constructor
    /**
     * actionPerformed Method -
     * @param ae - the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            TEXTPANE.selectAll();
            if (!TEXTPANE.getSelectedText().isEmpty()) {
                JavaTextEditor.cutItem.setEnabled(true);
                JavaTextEditor.copyItem.setEnabled(true);
                JavaTextEditor.deleteItem.setEnabled(true);
                JavaTextEditor.selectAllItem.setEnabled(false);
                putValue(Action.NAME, "Select All");

            } else {
                JavaTextEditor.cutItem.setEnabled(false);
                JavaTextEditor.copyItem.setEnabled(false);
                JavaTextEditor.deleteItem.setEnabled(false);
                JavaTextEditor.selectAllItem.setEnabled(true);
                putValue(Action.NAME, "Select All");
            }

        } catch (Exception ex) {
            String message = "";
            if (ex instanceof NullPointerException) {
                message = "Error: NullPointerException";
            } else {
                message = "Error: IllegalArgumentException";

            }
            JOptionPane.showMessageDialog(textEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateSelectAllAction();

    }//end actionPerformed Method
    /**
     * updateSelectAllAction Method -
     */
    protected void updateSelectAllAction() {
        if (TEXTPANE.getText().isEmpty() || !TEXTPANE.getSelectedText().isEmpty()) {
            setEnabled(false);
            putValue(Action.NAME, "SelectAll");

        } else {
            setEnabled(true);
            putValue(Action.NAME, "SelectAll");
        }

    }//end of the updateSelectAllAction Method
}//end of the SelectAllAction Class

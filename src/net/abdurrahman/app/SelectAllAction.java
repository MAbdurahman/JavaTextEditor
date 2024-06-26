package net.abdurrahman.app;

import java.awt.event.ActionEvent;
import javax.swing.*;
import static net.abdurrahman.app.JavaTextEditor.TEXTPANE;
import static net.abdurrahman.app.JavaTextEditor.selectAllItem;

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
        setEnabled(!TEXTPANE.getText().isEmpty());

    }//end of the SelectionAllAction Constructor
    /**
     * actionPerformed Method -
     * @param ae - the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            TEXTPANE.selectAll();
            if (!TEXTPANE.getText().isEmpty()) {
                JavaTextEditor.selectAllItem.setEnabled(true);
                putValue(Action.NAME, "Select All");

            } else {
                JavaTextEditor.selectAllItem.setEnabled(false);
                putValue(Action.NAME, "Select All");
            }
            if (!TEXTPANE.getSelectedText().isEmpty()) {
                JavaTextEditor.cutItem.setEnabled(true);
                JavaTextEditor.copyItem.setEnabled(true);
                JavaTextEditor.deleteItem.setEnabled(true);
                JavaTextEditor.selectAllItem.setEnabled(true);
            }

        } catch (Exception ex) {
            String message = "";
            if (ex instanceof NullPointerException) {
                message = "Error: NullPointerException";
            } else if (ex instanceof IllegalArgumentException) {
                message = "Error: IllegalArgumentException";

            } else {
                message = "Error: " + ex.getMessage();
            }
            JOptionPane.showMessageDialog(textEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateSelectAllAction();

    }//end actionPerformed Method
    /**
     * updateSelectAllAction Method -
     */
    protected void updateSelectAllAction() {
        /*if (TEXTPANE.getText() == null || TEXTPANE.getSelectedText() ==  null) {
            setEnabled(false);
            putValue(Action.NAME, "SelectAll");

        } else {
            setEnabled(true);
            putValue(Action.NAME, "SelectAll");
        }*/

        if (!TEXTPANE.getText().isEmpty()) {
            setEnabled(!TEXTPANE.getText().isEmpty());
            putValue(Action.NAME, "Select All");
        }
        if ((TEXTPANE.getSelectionEnd() - TEXTPANE.getSelectionStart()) > 0) {
            selectAllItem.setEnabled(false);
            putValue(Action.NAME, "Select All");
        }
        /*setEnabled((!TEXTPANE.getText().isEmpty()) && ((TEXTPANE.getCaretPosition() != 0))
                && ((TEXTPANE.getSelectionEnd() - TEXTPANE.getSelectionStart()) <= 0));
        putValue(Action.NAME, "Select All");*/

    }//end of the updateSelectAllAction Method
}//end of the SelectAllAction Class

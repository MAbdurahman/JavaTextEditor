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

    JavaTextEditor javaTextEditor;
    /**
     * SelectionAllAction Constructor -
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SelectAllAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("SelectAll", icon);
        setEnabled(!TEXTPANE.getText().isEmpty());
        this.javaTextEditor = javaTextEditor;

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
            JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateSelectAllAction();

    }//end actionPerformed Method
    /**
     * updateSelectAllAction Method -
     */
    protected void updateSelectAllAction() {
        if ((TEXTPANE.getSelectionEnd() - TEXTPANE.getSelectionStart()) > 0) {
            selectAllItem.setEnabled(false);
            putValue(Action.NAME, "Select All");
        }
        if ((TEXTPANE.getSelectedText() == null) && (!TEXTPANE.getText().isEmpty())
                || (TEXTPANE.getSelectedText().isEmpty()) && (!TEXTPANE.getText().isEmpty()))  {
            selectAllItem.setEnabled(true);
            putValue(Action.NAME, "Select All");
        }

    }//end of the updateSelectAllAction Method
}//end of the SelectAllAction Class

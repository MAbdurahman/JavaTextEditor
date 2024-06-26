package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * DeleteAction Class -
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class DeleteAction extends AbstractAction {

    /**
     * DeleteAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public DeleteAction(ImageIcon icon) {
        super("Delete", icon);
        setEnabled(false);

    }//end of DeleteAction Constructor

    /**
     * actionPerformed Method -
     *
     * @param ae - the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (JavaTextEditor.TEXTPANE.getSelectionEnd() - JavaTextEditor.TEXTPANE.getSelectionStart() > 0) {
            setEnabled(true);
            JavaTextEditor.TEXTPANE.setText("");

        } else {
            setEnabled(false);
        }
        updateDeleteAction();

    }//end of actionPerformed Method

    /**
     * updateDeleteAction Method -
     */
    protected void updateDeleteAction() {
        setEnabled(JavaTextEditor.TEXTPANE.getSelectionEnd() - JavaTextEditor.TEXTPANE.getSelectionStart() > 0);
        putValue(Action.NAME, "Delete");

    }//end of updateDeleteAction Method
}//end of DeleteAction Class

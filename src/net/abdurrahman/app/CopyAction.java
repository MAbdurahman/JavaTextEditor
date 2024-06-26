package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * CopyAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class CopyAction extends AbstractAction {
    JavaTextEditor textEditor;
    /**
     * CopyAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public CopyAction(ImageIcon icon) {
        super("Copy", icon);
        setEnabled(false);

    }//end of the CopyAction Constructor
    /**
     * actionPerformed Method -
     * @param ae - the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            JavaTextEditor.TEXTPANE.copy();

        } catch (Exception ex) {
            String message = ex.getMessage();
            JOptionPane.showMessageDialog(textEditor, message);

        }
        updateCopyAction();

    }//end of actionPerformed Method
    /**
     * updateCopyAction Method -
     */
    public void updateCopyAction() {
        setEnabled(JavaTextEditor.TEXTPANE.getSelectedText() != null);
        putValue(Action.NAME, "Copy");

    }//end of updateCopyAction Method
}//end of CopyAction Class

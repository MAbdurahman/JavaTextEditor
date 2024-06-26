package net.abdurrahman.app;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static net.abdurrahman.app.JavaTextEditor.*;

/**
 * FindAction Class -
 */
public class FindAction extends AbstractAction {
    JavaTextEditor textEditor;

    /**
     * FindAction Constructor -
     * @param icon - an ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FindAction(ImageIcon icon) {
        super("Find", icon);
        setEnabled(false);

    }//end of the FindAction Constructor
    /**
     * actionPerformed Method - overrides the actionPerformed Method for AbstractAction Class
     * @param ae - the ActionEvent listener
     */
    @Override
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void actionPerformed(ActionEvent ae) {
        try {
            new FindAndReplace(null, false);

        } catch (Exception ex) {
            String message = ex.getMessage();
            JOptionPane.showMessageDialog(textEditor, message);

        }
        updateFindAction();

    }//end of actionPerformed Method
    /**
     * updateFindAction Method
     */
    protected void updateFindAction() {
        setEnabled(!TEXTPANE.getText().isEmpty() ? true : false);
        putValue(Action.NAME, "Find");

    }//end of the updateFindAction Method
}//end of the FindAction Class

package net.abdurrahman.app;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

/**
 * The CutAction Class
 */
public class CutAction extends AbstractAction {
    JavaTextEditor textEditor;
    /**
     * CutAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public CutAction(ImageIcon icon) {
        super("Cut", icon);
        setEnabled(false);

    }//end of CutAction Constructor
    /**
     * actionPerformed Method -
     * @param ae - the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        setEnabled(true);
        try {

            JavaTextEditor.TEXTPANE.cut();

        } catch (Exception ex) {
            String message = ex.getMessage();
            JOptionPane.showMessageDialog(textEditor, message);

        }
        updateCutAction();

    }//end of actionPerformed Method
    /**
     * updateCutAction Method -
     */
    public void updateCutAction() {
        /*setEnabled(JavaTextEditor.TEXTPANE.getSelectedText() != null);*/
        setEnabled(JavaTextEditor.TEXTPANE.getSelectionEnd() - JavaTextEditor.TEXTPANE.getSelectionStart() > 0);
        putValue(Action.NAME, "Cut");

    }//end of updateCutAction Method
}//end of CutAction Class

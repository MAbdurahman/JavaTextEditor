package net.abdurrahman.app;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import static net.abdurrahman.app.JavaTextEditor.TEXTPANE;

public class FindAndReplaceAction extends AbstractAction {
    //Instance variables
    JavaTextEditor textEditor;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FindAndReplaceAction(ImageIcon icon) {
        super("Find/Replace", icon);
        setEnabled(false);

    }//end of FindAndReplaceAction Constructor


    @Override
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void actionPerformed(ActionEvent ae) {
        try {
            new FindAndReplace(null, true);

        } catch (Exception ex) {
            String message = ex.getMessage();
            JOptionPane.showMessageDialog(textEditor, message);

        }

        updateFindAndReplaceAction();

    }//end of actionPerformed Method

    protected void updateFindAndReplaceAction () {
        setEnabled(!TEXTPANE.getText().isEmpty());
        putValue(Action.NAME, "Find/Replace");

    }//end of updateFindAndReplaceAction Method
}//end of FindAndReplaceAction Class

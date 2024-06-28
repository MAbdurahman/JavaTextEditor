package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewWindowAction extends AbstractAction {
    /**
     * NewWindowAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public NewWindowAction(ImageIcon icon) {
        super("New Window", icon);
        setEnabled(true);

    }//end of the NewWindowAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

    }//end of actionPerformed Method
}//end of NewWindowAction Class

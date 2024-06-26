package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitAction extends AbstractAction {
    /**
     * ExitAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ExitAction(ImageIcon icon) {
        super("Exit", icon);
        setEnabled(true);

    }//end of the ExitAction Constructor

    public void actionPerformed(ActionEvent ae) {}//end of actionPerformed Method

}//end of ExitAction Class

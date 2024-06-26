package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SaveAsFileAction extends AbstractAction {

    /**
     * SaveAsFileAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SaveAsFileAction(ImageIcon icon) {
        super("New", icon);
        setEnabled(true);

    }//end of the SaveAsFileAction Constructor


    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {}//end of actionPerformed Method
}//end of SaveAsFileAction Class

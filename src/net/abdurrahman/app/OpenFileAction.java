package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * OpenFileAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class OpenFileAction extends AbstractAction {
    /**
     * OpenFileAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public OpenFileAction(ImageIcon icon) {
        super("Open", icon);
        setEnabled(true);

    }//end of the OpenFileAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {}//end of actionPerformed Method
}//end of OpenFileAction Class

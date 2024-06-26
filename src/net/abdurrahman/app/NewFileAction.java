package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * NewFileAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class NewFileAction extends AbstractAction {
    /**
     * NewFileAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public NewFileAction(ImageIcon icon) {
        super("New", icon);
        setEnabled(true);

    }//end of the NewFileAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {}//end of actionPerformed Method
}//end of NewFileAction Class

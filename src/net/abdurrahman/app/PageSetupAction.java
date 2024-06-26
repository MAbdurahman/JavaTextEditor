package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * PageSetupAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class PageSetupAction extends AbstractAction {
    /**
     * PageSetupAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PageSetupAction(ImageIcon icon) {
        super("Page Setup", icon);
        setEnabled(true);

    }//end of the PageSetupAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {}//end of actionPerformed Method
}//end of PageSetupAction Class

package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * PrintPreviewAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class PrintPreviewAction extends AbstractAction {
    /**
     * PrintPreviewAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PrintPreviewAction(ImageIcon icon) {
        super("Print Preview", icon);
        setEnabled(true);

    }//end of the PrintPreviewAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {}//end of actionPerformed Method
}//end of PrintPreviewAction Class

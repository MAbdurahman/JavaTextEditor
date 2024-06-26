package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * LineNumbersAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class LineNumbersAction extends AbstractAction {

    /**
     * LineNumbersAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LineNumbersAction(ImageIcon icon) {
        super("Line Numbers", icon);
        setEnabled(true);

    }//end of the LineNumbersAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {}//end of actionPerformed Method
}//end of LineNumbersAction Class

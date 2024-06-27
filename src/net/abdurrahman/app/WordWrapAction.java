package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * WordWrapAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class WordWrapAction extends AbstractAction {

    /**
     * NewFileAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public WordWrapAction(ImageIcon icon) {
        super("Word Wrap", icon);
        setEnabled(true);

    }//end of the WordWrapAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {}//end of actionPerformed Method
}//end of WordWrapAction Class

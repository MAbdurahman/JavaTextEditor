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
    //Instance variables
    JavaTextEditor javaTextEditor;
    /**
     * NewFileAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public NewFileAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("New", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;

    }//end of the NewFileAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {}//end of actionPerformed Method
}//end of NewFileAction Class

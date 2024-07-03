package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;
    /**
     * ExitAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ExitAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Exit", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;

    }//end of the ExitAction Constructor

    public void actionPerformed(ActionEvent ae) {
        System.exit(0);
    }//end of actionPerformed Method

}//end of ExitAction Class

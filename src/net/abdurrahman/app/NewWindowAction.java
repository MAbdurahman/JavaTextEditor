package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewWindowAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;
    JFileChooser fileChooser;

    /**
     * NewWindowAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public NewWindowAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("New Window", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;
        this.fileChooser = JavaTextEditor.FILE_CHOOSER;

    }//end of the NewWindowAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        new JavaTextEditor();

    }//end of actionPerformed Method
}//end of NewWindowAction Class

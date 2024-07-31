package net.abdurrahman.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ZoomDefaultAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;
    Font originalFont;
    int originalFontSize;

    public ZoomDefaultAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Restore Default Zoom", icon);
        this.javaTextEditor = javaTextEditor;
        this.originalFont = javaTextEditor.getFont();
        this.originalFontSize = javaTextEditor.getFont().getSize();
    }

    /**
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println("Zoom Default");

    }//end of actionPerformed Method
}//end of ZoomDefaultAction Class

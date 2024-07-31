package net.abdurrahman.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ZoomOutAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;
    Font originalFont;
    int originalFontSize;

    public ZoomOutAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Zoom Out", icon);
        this.javaTextEditor = javaTextEditor;
        this.originalFont = javaTextEditor.getFont();
        this.originalFontSize = javaTextEditor.getFont().getSize();

    }//end of ZoomOutAction Class

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Zoom Out");
    }//end of actionPerformed Method
}//end of ZoomOutAction Class

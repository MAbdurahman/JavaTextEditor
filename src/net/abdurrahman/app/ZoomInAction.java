package net.abdurrahman.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ZoomInAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;
    Font originalFont;
    int originalFontSize;

    public ZoomInAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Zoom In", icon);
        this.javaTextEditor = javaTextEditor;
        this.originalFont = javaTextEditor.getFont();
        this.originalFontSize = javaTextEditor.getFont().getSize();


    }//end of ZoomInAction Constructor


    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Zoom In");
    }//end of actionPerformed Method
}//end of ZoomInAction Class

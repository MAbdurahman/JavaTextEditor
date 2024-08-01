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
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        updateZoomOutAction();

    }//end of actionPerformed Method

    /**
     * updateZoomOutAction Method -
     */
    public void updateZoomOutAction() {
        Font currentFont = JavaTextEditor.getTextPane().getFont();
        Font newFont = new Font(currentFont.getFamily(), currentFont.getStyle(), currentFont.getSize());
        int newFontSize = currentFont.getSize();
        newFontSize /= 1.25;
        JavaTextEditor.TEXTPANE.setFont(new Font(newFont.getFamily(), newFont.getStyle(), newFontSize));

    }//end of updateZoomOutAction Method
}//end of ZoomOutAction Class

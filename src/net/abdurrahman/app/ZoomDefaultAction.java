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
        this.originalFont = JavaTextEditor.getTextPane().getFont();
        this.originalFontSize = JavaTextEditor.getTextPane().getFont().getSize();
    }

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        updateZoomDefaultAction();

    }//end of actionPerformed Method

    /**
     * updateZoomDefaultAction Method -
     */
    public void updateZoomDefaultAction() {
        JavaTextEditor.getTextPane().setFont(new Font(originalFont.getName(), originalFontSize, originalFont.getSize()));

    }//end of updateZoomDefaultAction Method
}//end of ZoomDefaultAction Class

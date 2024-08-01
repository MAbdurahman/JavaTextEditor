package net.abdurrahman.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

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
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        updateZoomInAction();

        /*Font currentFont = JavaTextEditor.getTextPane().getFont();

        System.out.println("Zoom In");
        System.out.println("Current Font Size: " + currentFont.getSize());
        Font newFont = new Font(currentFont.getFamily(), currentFont.getStyle(), currentFont.getSize());
        int newFontSize = currentFont.getSize();
        newFontSize *= 1.25;

        JavaTextEditor.TEXTPANE.setFont(new Font(newFont.getFamily(), newFont.getStyle(), newFontSize));
        System.out.println("New Font Size: " + newFontSize);*/

    }//end of actionPerformed Method

    public void updateZoomInAction() {
        Font currentFont = JavaTextEditor.getTextPane().getFont();
        Font newFont = new Font(currentFont.getFamily(), currentFont.getStyle(), currentFont.getSize());
        int newFontSize = currentFont.getSize();
        newFontSize *= 1.25;
        JavaTextEditor.TEXTPANE.setFont(new Font(newFont.getFamily(), newFont.getStyle(), newFontSize));

    }//end of updateZoomInAction Method


}//end of ZoomInAction Class

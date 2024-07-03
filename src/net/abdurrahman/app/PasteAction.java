package net.abdurrahman.app;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class PasteAction extends AbstractAction {

    JavaTextEditor javaTextEditor;
    Clipboard clipboard;


    public PasteAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Paste", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        /*try {
            String stringData = (String) clipboard.getData(DataFlavor.stringFlavor);
            if (stringData != null) {
                setEnabled(true);

            } else {
                setEnabled(false);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message != null) {
                JOptionPane.showMessageDialog(textEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }*/

    }//end of PasteAction Constructor

    public void actionPerformed(ActionEvent ae) {
        try {
            if (clipboard.getContents(null) == null) {
                setEnabled(false);
                putValue(Action.NAME, "Paste");

            } else {
                setEnabled(true);
                JavaTextEditor.TEXTPANE.paste();

            }

        } catch (Exception ex) {
            String message = ex.getMessage();
            JOptionPane.showMessageDialog(javaTextEditor, message, "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        updatePasteAction();

    }//end of actionPerformed Method

    public String updatePasteAction() {
        try {
              return (String) clipboard.getData(DataFlavor.stringFlavor);

        } catch (HeadlessException | IOException ex) {
            String message = ex.getMessage();
            JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();

        } catch (UnsupportedFlavorException e) {
            /*String message = e.getMessage();*/
            String message = "Supported String to paste is null!";
            JOptionPane.showMessageDialog(javaTextEditor, message, "Info", JOptionPane.INFORMATION_MESSAGE);
            /*e.printStackTrace();*/

        }
        return null;

    }//end of updatePasteAction Method

}//end of PasteAction Class

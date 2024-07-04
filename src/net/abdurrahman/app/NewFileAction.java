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
    public void actionPerformed(ActionEvent ae) {
        String fileName = javaTextEditor.getTitle();
        if (JavaTextEditor.HAS_CHANGED && JavaTextEditor.TEXTPANE.getText() != "") {
            showDialog();

        } else if (fileName.equalsIgnoreCase("Untitled - TextEditor")) {
            showSaveAsDialog();

        } else {

        }
    }//end of actionPerformed Method

    private void showDialog() {
        String file = javaTextEditor.getTitle();
        Object[] options = {"Save", "Don't Save", "Cancel"};
        int result = JOptionPane.showOptionDialog(javaTextEditor, "Do you want to save changes to "+removeExtraCharacters(file)+"?",
                "TextEditor", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

    }//end of showDialog Method

    private void showSaveAsDialog() {
        System.out.println("showSaveAsDialog");
    }

    /**
     * removeExtraCharacters Method - removes the last thirteen characters from a String
     * @param text - the specified String
     * @return String - a String with the last thirteen characters removed
     */
    private String removeExtraCharacters(String text) {
        return text.substring(0, text.length() - 13);

    }//end of removeExtraCharacters Method
}//end of NewFileAction Class

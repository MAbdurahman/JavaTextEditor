package net.abdurrahman.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveAsFileAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;
    String fileName;
    String filePath;

    /**
     * SaveAsFileAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SaveAsFileAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("SaveAs...", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;

    }//end of the SaveAsFileAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        FileDialog fileDialog = new FileDialog(javaTextEditor, "Save As...", FileDialog.SAVE);
        fileDialog.setVisible(true);
        javaTextEditor.setLocationRelativeTo(javaTextEditor);
        fileDialog.setLocation(javaTextEditor.getLocation().x, javaTextEditor.getLocation().y);
        String file = javaTextEditor.getTitle();
        if (fileDialog.getFile() != null) {
            fileName = fileDialog.getFile();
            filePath = fileDialog.getDirectory() + fileDialog.getFile();
            fileDialog.setDirectory(filePath);
            javaTextEditor.setTitle(fileName);

        }
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(JavaTextEditor.TEXTPANE.getText());
            bufferedWriter.close();

        } catch (NullPointerException ex) {
            String message = "Error saving file: " + removeExtraCharacters(file)+ "\nError: NullPointerException.";
            JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (IOException ex) {
            String message = "Error saving file: " + removeExtraCharacters(file) + "\nError: IOException.";
            JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            String message = "Error saving file: " + removeExtraCharacters(file) + "\nError: "+ex.getMessage();
            JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//end of actionPerformed Method


    /**
     * removeExtraCharacters Method - removes the last thirteen characters from a String
     * @param text - the specified String
     * @return String - a String with the last thirteen characters removed
     */
    private String removeExtraCharacters(String text) {
        return text.substring(0, text.length() - 13);

    }//end of removeExtraCharacters Method
}//end of SaveAsFileAction Class

package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * NewFileAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class NewFileAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;
    String fileName;
    String filePath;
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
        /* Assign ImageIcon to parent and later assign to JFileChooser */
        JFrame parent = new JFrame();
        parent.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/java-texteditor.png")));

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Save As...");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        String filterFiles = "Text files (*.cpp, *.css, *.html, *.htm, *.java, *.js, *rtf, *.txt)";
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(filterFiles,
                "cpp", "css", "html", "htm", "java", "js", "rtf", "txt");
        jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);

        String file = javaTextEditor.getTitle();
        int returnedValue = jFileChooser.showSaveDialog(parent);

        if (returnedValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            fileName = selectedFile.getName();
            filePath = selectedFile.getAbsolutePath();
            javaTextEditor.setTitle(fileName + " - TextEditor");

            try {
                FileWriter fileWriter = new FileWriter(filePath);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(JavaTextEditor.TEXTPANE.getText());
                bufferedWriter.close();

            } catch (NullPointerException ex) {
                String message = "Error saving file: " + removeExtraCharacters(file) + "\nError: NullPointerException.";
                JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (IOException ex) {
                String message = "Error saving file: " + removeExtraCharacters(file) + "\nError: IOException.";
                JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception ex) {
                String message = "Error saving file: " + removeExtraCharacters(file) + "\nError: " + ex.getMessage();
                JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
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

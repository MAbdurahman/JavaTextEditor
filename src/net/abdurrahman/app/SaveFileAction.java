package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * SaveFileAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class SaveFileAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;
    String fileName;
    String filePath;

    /**
     * SaveFileAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SaveFileAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Save", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;

    }//end of the SaveFileAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        /* Assign ImageIcon to parent and later assign to JFileChooser */
        /*JFrame parent = new JFrame();*/
        javaTextEditor.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/java-texteditor.png")));

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Save");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        String filterFiles = "Text files (*.cpp, *.css, *.html, *.htm, *.java, *.js, *.rtf, *.txt)";
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(filterFiles,
                "cpp", "css", "html", "htm", "java", "js", "rtf", "txt");
        jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);


        String file = javaTextEditor.getTitle();
        jFileChooser.setSelectedFile(new File(removeExtraCharacters(file)));
        int userSelection = jFileChooser.showSaveDialog(javaTextEditor);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
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
    }//end of actionPerformed Method
    /**
     * removeExtraCharacters Method - removes the last thirteen characters from a String
     * @param text - the specified String
     * @return String - a String with the last thirteen characters removed
     */
    private String removeExtraCharacters(String text) {
        return text.substring(0, text.length() - 13);

    }//end of removeExtraCharacters Method

    public static void saveFileActionDialog () {

    }//end of saveFileActionDialog Method

    public static void saveHTMLFileActionDialog () {

    }//end of saveHTMLFileActionDialog Method

    public static void saveRTFFileActionDialog () {

    }//end of saveRTFFileActionDialogMethod

    public static void showConfirmSaveDialog () {

    }//end of showConfirmSaveDialog Method

    public static void showConfirmSaveMessageDialog () {

    }//end of showConfirmSaveMessageDialog Method
}//end of SaveFileAction Class

package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
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
        /* Assign ImageIcon to parent and later assign to JFileChooser */
        JFrame parent = new JFrame();
        parent.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/java-texteditor.png")));

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Save As...");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        String filterFiles = "Text files (*.cpp, *.css, *.html, *.htm, *.java, *.js, *.php, *scss, *.txt)";
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(filterFiles,
                "cpp", "css", "html", "htm", "java", "js", "php", "scss", "txt");
        jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);

        String file = javaTextEditor.getTitle();
        jFileChooser.setSelectedFile(new File(removeExtraCharacters(file)));
        int userSelection = jFileChooser.showSaveDialog(parent);

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

    public static void saveAsFileActionDialog () {

    }//end of saveAsFileActionDialog Method

    public static void showConfirmSaveAsDialog(JavaTextEditor javaTextEditor) {

    }//end of showConfirmSaveAsDialog Method

    public static void showConfirmSaveAsMessageDialog(JavaTextEditor javaTextEditor) {

    }//end of showConfirmSaveAsMessageDialog Method
}//end of SaveAsFileAction Class

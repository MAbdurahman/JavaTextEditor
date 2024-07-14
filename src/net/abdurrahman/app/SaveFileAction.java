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
 * SaveFileAction Class
 *
 * @author MAbdurrahman
 * @version 1.0.0
 * @date 26 June 2024
 */
public class SaveFileAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;
    JFileChooser jFileChooser;
    String fileName;
    static String filePath;

    /**
     * SaveFileAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SaveFileAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Save", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;
        this.jFileChooser = JavaTextEditor.FILE_CHOOSER;

    }//end of the SaveFileAction Constructor

    public static void saveFileActionDialog(JavaTextEditor javaTextEditor) {
        System.out.println("Saving file...with saveFileActionDialog");
        JFileChooser jFileChooser = JavaTextEditor.FILE_CHOOSER;
        String file = javaTextEditor.getTitle();
        File selectedFile = jFileChooser.getSelectedFile();
        if (selectedFile != null) {
            filePath = selectedFile.getAbsolutePath();
        }

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(JavaTextEditor.TEXTPANE.getText());
            bufferedWriter.close();

        } catch (NullPointerException ex) {
            String message = "Error saving file: " + JavaTextEditor.removeExtraCharacters(file) + "\nError: NullPointerException.";
            JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (IOException ex) {
            String message = "Error saving file: " + JavaTextEditor.removeExtraCharacters(file) + "\nError: IOException.";
            JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            String message = "Error saving file: " + JavaTextEditor.removeExtraCharacters(file) + "\nError: " + ex.getMessage();
            JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }//end of saveFileActionDialog Method

    public static void showConfirmSaveDialog(JavaTextEditor javaTextEditor, JFileChooser jFileChooser) {
        String file = javaTextEditor.getTitle();
        String fileName = JavaTextEditor.removeExtraCharacters(file);
        jFileChooser = JavaTextEditor.FILE_CHOOSER;
        String message = "Do you want to save the changes to " + fileName + "?";
        int userResponse = JOptionPane.showConfirmDialog(javaTextEditor, message, "Save File", JOptionPane.YES_NO_CANCEL_OPTION);
        switch (userResponse) {
            case JOptionPane.YES_OPTION:
                saveFileActionDialog(javaTextEditor);
                showConfirmSaveMessageYesDialog(javaTextEditor);
                break;
            case JOptionPane.NO_OPTION:
                showConfirmSaveMessageNoDialog(javaTextEditor);
                break;
            case JOptionPane.CANCEL_OPTION:
                showConfirmSaveMessageCancelDialog(javaTextEditor);
                break;
        }
    }//end of showConfirmSaveDialog Method

    public static void showConfirmSaveMessageYesDialog(JavaTextEditor javaTextEditor) {
        String file = javaTextEditor.getTitle();
        String fileName = JavaTextEditor.removeExtraCharacters(file);
        String message = "The changes to " + fileName + " were saved.";
        JOptionPane.showMessageDialog(javaTextEditor, message, "Save File", JOptionPane.INFORMATION_MESSAGE);
    }//end of showConfirmSaveMessageYesDialog Method

    public static void showConfirmSaveMessageNoDialog(JavaTextEditor javaTextEditor) {
        String file = javaTextEditor.getTitle();
        String fileName = JavaTextEditor.removeExtraCharacters(file);
        String message = "The changes to " + fileName + " were not saved.";
        JOptionPane.showMessageDialog(javaTextEditor, message, "Save File", JOptionPane.INFORMATION_MESSAGE);
    }//end of showConfirmSaveMessageNoDialog Method

    public static void showConfirmSaveMessageCancelDialog(JavaTextEditor javaTextEditor) {
        String file = javaTextEditor.getTitle();
        String fileName = JavaTextEditor.removeExtraCharacters(file);
        String message = "The changes to " + fileName + " were cancelled.";
        JOptionPane.showMessageDialog(javaTextEditor, message, "Save File", JOptionPane.INFORMATION_MESSAGE);
    }//end of showConfirmSaveMessageCancelDialog Method

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        /* Assign ImageIcon to parent and later assign to JFileChooser */
        javaTextEditor.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/java-texteditor.png")));

        /*jFileChooser = new JFileChooser();*/
        jFileChooser.setDialogTitle("Save");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        String filterFiles = "Text files (*.cpp, *.css, *.html, *.htm, *.java, *.js, *.php, *.scss, *.txt)";
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(filterFiles,
                "cpp", "css", "html", "htm", "java", "js", "php", "scss", "txt");
        jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);


        String file = javaTextEditor.getTitle();
        jFileChooser.setSelectedFile(new File(removeExtraCharacters(file)));
        int userSelection = jFileChooser.showSaveDialog(javaTextEditor);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            fileName = selectedFile.getName();
            filePath = selectedFile.getAbsolutePath();
            javaTextEditor.setTitle(fileName + " - TextEditor");

        }

    }//end of actionPerformed Method

    /**
     * removeExtraCharacters Method - removes the last thirteen characters from a String
     *
     * @param text - the specified String
     * @return String - a String with the last thirteen characters removed
     */
    private String removeExtraCharacters(String text) {
        return text.substring(0, text.length() - 13);

    }//end of removeExtraCharacters Method

}//end of SaveFileAction Class

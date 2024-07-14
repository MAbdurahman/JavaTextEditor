package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExitAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;
    JFileChooser jFileChooser;
    String fileName;
    static String filePath;
    /**
     * ExitAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ExitAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Exit", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;
        this.jFileChooser = JavaTextEditor.FILE_CHOOSER;

    }//end of the ExitAction Constructor

    public void actionPerformed(ActionEvent ae) {
        if (JavaTextEditor.TEXTPANE.getText() == "") {
            disposeJavaTextEditor();

        } else if (JavaTextEditor.HAS_CHANGED) {
            System.out.println("show dialog");
            showDialog();
        }

    }//end of actionPerformed Method

    private void disposeJavaTextEditor() {
        javaTextEditor.dispose();

    }//end of disposeJavaTextEditor Method

    /**
     * showDialog Method -
     */
    private void showDialog() {
        String file = javaTextEditor.getTitle();
        Object[] options = {"Save", "Don't Save", "Cancel"};
        int result = JOptionPane.showOptionDialog(javaTextEditor, "Do you want to save changes to "+JavaTextEditor.removeExtraCharacters(file)+"?",
                "TextEditor", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

        if (result == JOptionPane.YES_OPTION) {
            showSaveAsDialog();
        }
        if (result == JOptionPane.NO_OPTION) {
            disposeJavaTextEditor();
        }
    }//end of showDialog Method

    /**
     * showSaveAsDialog Method -
     */
    private void showSaveAsDialog() {
        System.out.println("showSaveAsDialog");
        /* Assign ImageIcon to parent and later assign to JFileChooser */
        javaTextEditor.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/java-texteditor.png")));

        jFileChooser.setDialogTitle("Save As...");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        String filterFiles = "Text files (*.cpp, *.css, *.html, *.htm, *.java, *.js, *.php, *.scss, *.txt)";
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(filterFiles,
                "cpp", "css", "html", "htm", "java", "js", "php", "scss", "txt");
        jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);

        String file = "*.txt";
        jFileChooser.setSelectedFile(new File(file));
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
        }
    }//end of showSaveAsDialog Method
}//end of ExitAction Class

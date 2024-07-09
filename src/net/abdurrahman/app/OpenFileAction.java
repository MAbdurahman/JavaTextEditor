package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * OpenFileAction Class
 *
 * @author MAbdurrahman
 * @version 1.0.0
 * @date 26 June 2024
 */
public class OpenFileAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;

    /**
     * OpenFileAction Constructor -
     *
     * @param icon - the ImageIcon
     * @param javaTextEditor - the JavaTextEditor
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public OpenFileAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Open", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;

    }//end of the OpenFileAction Constructor

    /**
     * openFileActionDialog Method -
     *
     * @param openedFile - the selected File
     * @param javaTextEditor - the instance of JavaTextEditor
     */
    public static void openFileActionDialog(File openedFile, JavaTextEditor javaTextEditor) {

        String fileName = openedFile.getName();
        if (fileName.endsWith(".cpp")) {
            JavaTextEditor.TEXTPANE.setContentType("text/cpp");
        }
        if (fileName.endsWith(".css")) {
            JavaTextEditor.TEXTPANE.setContentType("text/css");
        }
        if (fileName.endsWith(".htm")) {
            JavaTextEditor.TEXTPANE.setContentType("text/html");
        }
        if (fileName.endsWith(".html")) {
            JavaTextEditor.TEXTPANE.setContentType("text/html");
        }
        if (fileName.endsWith(".java")) {
            JavaTextEditor.TEXTPANE.setContentType("text/java");
        }
        if (fileName.endsWith(".js")) {
            JavaTextEditor.TEXTPANE.setContentType("text/js");
        }
        if (fileName.endsWith(".php")) {
            JavaTextEditor.TEXTPANE.setContentType("text/php");
        }
        if (fileName.endsWith(".scss")) {
            JavaTextEditor.TEXTPANE.setContentType("text/scss");
        }
        if (fileName.endsWith(".txt")) {
            JavaTextEditor.TEXTPANE.setContentType("text/plain");
        }

        BufferedReader bufferedReader = null;
        try {

            FileReader fileReader = new FileReader(openedFile);
            bufferedReader = new BufferedReader(fileReader);

            String string1 = "";
            StringBuilder string2 = new StringBuilder();

            while ((string1 = bufferedReader.readLine()) != null) {
                string2.append(string1).append("\n");
            }

            JavaTextEditor.TEXTPANE.setText(string2.toString());

            bufferedReader.close();

        } catch (IOException ex) {
            String message = ex.getMessage();
            JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    String message = "Error closing file " + openedFile.getAbsolutePath();
                    JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        /*if (!JavaTextEditor.TEXTPANE.getText().isEmpty()) {
            JavaTextEditor.undoItem.setEnabled(true);
            JavaTextEditor.undoAction.updateUndoAction();
            JavaTextEditor.selectAllAction.updateSelectAllAction();
            JavaTextEditor.findAction.updateFindAction();
            JavaTextEditor.findAndReplaceAction.updateFindAndReplaceAction();

        }*/
    }//end of openFileActionDialog Method

    /**
     * actionPerformed Method -
     *
     * @param ae the ActionEvent to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        /* Assign ImageIcon to parent and later assign to JFileChooser */
        javaTextEditor.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/java-texteditor.png")));
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Open File");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        String filterFiles = "Text files (*.cpp, *.css, *.html, *.htm, *.java, *.js, *php, *scss, *.txt)";
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(filterFiles,
                "cpp", "css", "html", "htm", "java", "js", "php", "scss", "txt");
        jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);

        int userSelection = jFileChooser.showOpenDialog(javaTextEditor);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File openedFile = jFileChooser.getSelectedFile();
            String openedFileName = openedFile.getName();
            javaTextEditor.setTitle(openedFileName + " - TextEditor");
            System.out.println(openedFileName);

            openFileActionDialog(openedFile, javaTextEditor);
        }
    }//end of actionPerformed Method
}//end of OpenFileAction Class

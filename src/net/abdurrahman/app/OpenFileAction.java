package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

        try {

            FileReader fileReader = new FileReader(openedFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

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
        }

    }//end of openFileActionDialog Method

    /**
     * openHTMLFileActionDialog Method
     *
     * @param openedFile - the selected File
     * @param javaTextEditor - the instance of JavaTextEditor
     */
    public static void openHTMLFileActionDialog(File openedFile, JavaTextEditor javaTextEditor) {
        System.out.println(openedFile.getAbsolutePath());
        System.out.println(openedFile.getName() + " ends with .html or .htm");

    }//end of openHTMLFileActionDialog Method

    /**
     * openRTFFileActionDialog Method -
     *
     * @param openedFile - the selected File
     * @param javaTextEditor - the instance of JavaTextEditor
     */
    public static void openRTFFileActionDialog(File openedFile, JavaTextEditor javaTextEditor) {
        System.out.println(openedFile.getAbsolutePath());
        System.out.println(openedFile.getName() + " ends with .rtf");
    }//end of openRTFFileActionDialog Method

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
        String filterFiles = "Text files (*.cpp, *.css, *.html, *.htm, *.java, *.js, *.rtf, *.txt)";
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(filterFiles,
                "cpp", "css", "html", "htm", "java", "js", "rtf", "txt");
        jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);

        int userSelection = jFileChooser.showOpenDialog(javaTextEditor);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File openedFile = jFileChooser.getSelectedFile();
            String openedFileName = openedFile.getName();
            javaTextEditor.setTitle(openedFileName + " - TextEditor");
            System.out.println(openedFileName);

            if ((openedFileName.endsWith(".html") || (openedFileName.endsWith(".htm")))) {
                openHTMLFileActionDialog(openedFile, javaTextEditor);

            } else if (openedFileName.endsWith(".rtf")) {
                openRTFFileActionDialog(openedFile, javaTextEditor);

            } else {
                openFileActionDialog(openedFile, javaTextEditor);
            }
        }
    }//end of actionPerformed Method


}//end of OpenFileAction Class

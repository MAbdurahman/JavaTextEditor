package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public OpenFileAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Open", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;

    }//end of the OpenFileAction Constructor

    /**
     * actionPerformed Method -
     *
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        /* Assign ImageIcon to parent and later assign to JFileChooser */
        JFrame parent = new JFrame();
        parent.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/java-texteditor.png")));

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Open File");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        String filterFiles = "Text files (*.cpp, *.css, *.html, *.htm, *.java, *.js, *rtf, *.txt)";
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(filterFiles,
                ".cpp", ".css", ".html", ".htm", ".java", ".js", ".rtf", ".txt");
        jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);

        int checkInput = jFileChooser.showOpenDialog(parent);

        if (checkInput == JFileChooser.APPROVE_OPTION) {
            File openedFile = jFileChooser.getSelectedFile();
            String openedFileName = openedFile.getName();
            javaTextEditor.setTitle(openedFileName + " - TextEditor");
            /*parent.setTitle(openedFileName);*/
            System.out.println(openedFileName);
            try {
                FileReader fileReader = new FileReader(openedFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String string1 = "";
                StringBuilder string2 = new StringBuilder();

                while ((string1 = bufferedReader.readLine()) != null) {
                    string2.append(string1).append("\n");
                }

                //TEXTPANE.setText(string2.toString());
                JavaTextEditor.TEXTPANE.setText(string2.toString());

                bufferedReader.close();


            } catch (IOException ex) {
                String message = ex.getMessage();
                JOptionPane.showMessageDialog(javaTextEditor, message, "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }//end of actionPerformed Method
}//end of OpenFileAction Class

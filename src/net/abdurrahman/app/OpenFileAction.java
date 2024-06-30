package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    /**
     * OpenFileAction Constructor -
     *
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public OpenFileAction(ImageIcon icon) {
        super("Open", icon);
        setEnabled(true);

    }//end of the OpenFileAction Constructor

    /**
     * actionPerformed Method -
     *
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        JFileChooser jFileChooser = new JFileChooser();
        Image icon = Toolkit.getDefaultToolkit().getImage(JavaTextEditor.class.getResource("../img/java-texteditor.png"));
        jFileChooser.setDialogTitle("Open File");


        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Text Files",
                "cpp", "css", "html", "htm", "java", "js", "rtf", "txt");
        jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);

        int checkInput = jFileChooser.showOpenDialog(null);

        if (checkInput == JFileChooser.APPROVE_OPTION) {
            File openedFile = jFileChooser.getSelectedFile();

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

            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }//end of actionPerformed Method
}//end of OpenFileAction Class

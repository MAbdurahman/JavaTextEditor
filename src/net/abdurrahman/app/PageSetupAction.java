package net.abdurrahman.app;

import javax.print.attribute.standard.PrinterLocation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;

/**
 * PageSetupAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class PageSetupAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;
    PrinterJob printerJob;
    PageFormat pageFormat;

    /**
     * PageSetupAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PageSetupAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Page Setup", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;
        /*javaTextEditor.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../img/java-texteditor.png")));
        JDialog dialog = new JDialog(javaTextEditor, "Page Setup", true);
        dialog.setLocationRelativeTo(javaTextEditor);
        dialog.setLocation(300, 200);*/



    }//end of the PageSetupAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        printerJob = PrinterJob.getPrinterJob();

        pageFormat = new PageFormat();

        pageFormat = printerJob.pageDialog(printerJob.defaultPage());
    }//end of actionPerformed Method


}//end of PageSetupAction Class

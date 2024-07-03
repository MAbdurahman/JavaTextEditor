package net.abdurrahman.app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * PrintPageAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class PrintPageAction extends AbstractAction {
    //Instance variables
    JavaTextEditor javaTextEditor;
    PrinterJob printerJob = PrinterJob.getPrinterJob();

    /**
     * PrintPageAction Constructor -
     * @param icon - the ImageIcon
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PrintPageAction(ImageIcon icon, JavaTextEditor javaTextEditor) {
        super("Print", icon);
        setEnabled(true);
        this.javaTextEditor = javaTextEditor;

    }//end of the PrintPageAction Constructor

    /**
     * actionPerformed Method -
     * @param ae the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        printerJob = PrinterJob.getPrinterJob();
        if (printerJob.printDialog()) {
            try {
                printerJob.print();

            } catch (PrinterException ex) {
                String message = ex.getMessage();
                JOptionPane.showMessageDialog(JavaTextEditor.TEXTPANE, message);
            }
        }
    }//end of actionPerformed Method
}//end of PrintPageAction Class

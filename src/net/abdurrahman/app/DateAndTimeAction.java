package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.text.Caret;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateAndTimeAction extends AbstractAction {
    //Instances variables
    static LocalDateTime dateTime;
    static DateTimeFormatter dateTimeFormatter;
    static String formattedDate;

    public DateAndTimeAction(ImageIcon icon) {
        super("Date/Time", icon);
        setEnabled(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String dateAndTime = getFormattedDate();
            int caretPosition = JavaTextEditor.TEXTPANE.getCaretPosition();
            JavaTextEditor.TEXTPANE.getDocument().insertString(caretPosition, dateAndTime, null);


        } catch (Exception ex) {
            String message = ex.getMessage();
            JOptionPane.showMessageDialog(JavaTextEditor.TEXTPANE, message);
        }
    }//end of actionPerformed Method

    public static String getFormattedDate() {
        try {
            dateTime = LocalDateTime.now();
            dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm:ss a");
            formattedDate = dateTime.format(dateTimeFormatter);

        } catch (Exception ex) {
            String message = ex.getMessage();
            JOptionPane.showMessageDialog(JavaTextEditor.TEXTPANE, message);
        }
        return formattedDate;
    }
}//end of getFormattedDate Method

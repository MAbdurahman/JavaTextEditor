package net.abdurrahman.app;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;

public class LineNumbers implements EditorLineNumber {
    //Instance variables
    JavaTextEditor javaTextEditor;
    int lineCount;
    JTextArea textArea;
    public LineNumbers(JavaTextEditor javaTextEditor) {
        this.javaTextEditor = javaTextEditor;
        this.lineCount = 0;
    }

    @Override
    public int getNumberLines() {
        int totalCharacters = JavaTextEditor.TEXTPANE.getText().length();
        int lineCount = (totalCharacters == 0) ? 1 : 0;

        try {
            int offset = totalCharacters;

            while (offset > 0) {
                offset = Utilities.getRowStart(JavaTextEditor.TEXTPANE, offset) - 1;
                lineCount++;
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        return lineCount;
    }//end of getNumberLines Method

    @Override
    public Rectangle getLineRectangle(int line) {
        try{
            int offset = Utilities.getRowStart(JavaTextEditor.TEXTPANE, line) - 1;

            return JavaTextEditor.TEXTPANE.modelToView(offset);

        }catch(BadLocationException e){
            e.printStackTrace();

            return new Rectangle();

        }
    }//end of getLineRectangle Method
}//end of LineNumbers Class

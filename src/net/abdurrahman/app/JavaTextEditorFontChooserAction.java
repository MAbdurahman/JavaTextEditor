package net.abdurrahman.app;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
public class JavaTextEditorFontChooserAction extends StyledEditorKit.StyledTextAction {
    /** Instance Variables */
    JavaTextEditorFontChooser fontChooser;
    JavaTextEditor textEditor;

    /**
     * JavaTextEditorFontChooserAction Constructor -
     */
    public JavaTextEditorFontChooserAction() {
        super(StyleConstants.FontFamily.toString());

    }//end of the JavaTextEditorFontChooserAction Constructor
    /**
     * actionPerformed Method -
     * @param ae - representing the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        JTextPane textPaneEditor = (JTextPane) getEditor(ae);
        if (textPaneEditor == null) {
            String message = "Select the TextPad before changing the font.";
            JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        fontChooser = new JavaTextEditorFontChooser();
        int option = fontChooser.showDialog(textEditor);
        if (option == JavaTextEditorFontChooser.APPROVE_OPTION) {

            Font font = fontChooser.getSelectedFont();
            StyledEditorKit editorKit = getStyledEditorKit(textPaneEditor);
            MutableAttributeSet mutableAttributes = editorKit.getInputAttributes();

            StyleConstants.setFontFamily(mutableAttributes, font.getFamily());
            StyleConstants.setFontSize(mutableAttributes, font.getSize());
            boolean isSubscripted = !StyleConstants.isSubscript(mutableAttributes);
            boolean isSuperscripted = !StyleConstants.isSuperscript(mutableAttributes);
            boolean isStrikeThroughed = !StyleConstants.isStrikeThrough(mutableAttributes);

            SimpleAttributeSet simpleAttributes = new SimpleAttributeSet();
            StyleConstants.setSubscript(simpleAttributes, isSubscripted);
            StyleConstants.setSuperscript(simpleAttributes, isSuperscripted);
            StyleConstants.setStrikeThrough(simpleAttributes, isStrikeThroughed);

            int startingPoint = textPaneEditor.getSelectionStart();
            int endingPoint = textPaneEditor.getSelectionEnd();

            StyledDocument document = getStyledDocument(textPaneEditor);
            document.setCharacterAttributes(startingPoint, endingPoint, mutableAttributes, false);
            //Element characterElement = document.getCharacterElement(startingPoint);
            //AttributeSet attributes = characterElement.getAttributes

        }
    }//end of the actionPerformed Method
}//end of JavaTextEditorFontChooserAction Class

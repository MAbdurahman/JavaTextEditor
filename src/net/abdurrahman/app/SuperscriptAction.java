package net.abdurrahman.app;

import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

/**
 * SuperscriptAction Class
 */
public class SuperscriptAction extends StyledEditorKit.StyledTextAction {
    /**
     * SuperscriptAction Constructor -
     */
    public SuperscriptAction() {
        super(StyleConstants.Superscript.toString());

    }//end of the SuperscriptAction Constructor
    /**
     * actionPerformed Method -
     * @param ae - representing the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        JTextPane textPaneEditor = (JTextPane) getEditor(ae);
        if (textPaneEditor != null) {
            StyledEditorKit editorKit = getStyledEditorKit(textPaneEditor);
            MutableAttributeSet mutableAttributes = editorKit.getInputAttributes();
            boolean isSuperscripted = (StyleConstants.isSuperscript(mutableAttributes)) ? false : true;
            SimpleAttributeSet simpleAttributes = new SimpleAttributeSet();
            StyleConstants.setSuperscript(simpleAttributes, isSuperscripted);
            setCharacterAttributes(textPaneEditor, simpleAttributes, false);

        }
    }//end of actionPerformed Method
}//end of SuperscriptAction Class

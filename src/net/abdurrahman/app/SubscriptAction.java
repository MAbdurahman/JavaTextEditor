package net.abdurrahman.app;

import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

/**
 * The SubscriptAction Class
 * @author MAbdurrahman
 * @date 26 June 2024
 * @version 1.0.0
 */
public class SubscriptAction extends StyledEditorKit.StyledTextAction {
    /**
     * SubscriptAction Constructor -
     */
    public SubscriptAction() {
        super(StyleConstants.Subscript.toString());

    }//end of the SubscriptAction Constructor
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
            boolean isSubscripted = (StyleConstants.isSubscript(mutableAttributes)) ? false : true;
            SimpleAttributeSet simpleAttributes = new SimpleAttributeSet();
            StyleConstants.setSubscript(simpleAttributes, isSubscripted);
            setCharacterAttributes(textPaneEditor, simpleAttributes, false);

        }
    }//end of actionPerformed Method
}//end of SubscriptAction Class

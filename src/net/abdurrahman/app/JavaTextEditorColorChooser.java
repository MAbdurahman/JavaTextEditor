package net.abdurrahman.app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import static net.abdurrahman.app.JavaTextEditor.TEXTPANE;

public class JavaTextEditorColorChooser extends StyledEditorKit.StyledTextAction {
    /** Instance Variables */
    protected JColorChooser colorChooser;
    protected JDialog colorDialog;
    protected Color foregroundColor;
    protected boolean hasChanged;
    protected boolean hasCancelled;

    /**
     * JavaTextEditorColorChooser Constructor -
     */
    public JavaTextEditorColorChooser() {
        super(StyleConstants.Foreground.toString());

        colorChooser = new JColorChooser();
        colorDialog = new JDialog();
        colorDialog.setTitle("TextEditor ColorChooser");
        Image icon = Toolkit.getDefaultToolkit().getImage(JavaTextEditor.class.getResource("../img/java-texteditor.png"));
        colorDialog.setIconImage(icon);
        foregroundColor = TEXTPANE.getForeground();
        hasChanged = false;
        hasCancelled = false;

    }//end of JavaTextEditorColorChooser Constructor
    /**
     * actionPerformed Method -
     * @param ae - representing the ActionEvent event
     */
    @Override
    @SuppressWarnings({"UnnecessaryReturnStatement", "null", "Convert2Lambda"})
    public void actionPerformed(ActionEvent ae) {
        JTextPane textPaneEditor = (JTextPane) getEditor(ae);
        if (textPaneEditor == null) {
            String message = "Select the TextPad before changing the text color.";
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR);
            return;
        }
        int startingPoint = textPaneEditor.getSelectionStart();
        StyledDocument document = getStyledDocument(textPaneEditor);
        Element characterElement = document.getCharacterElement(startingPoint);
        AttributeSet attributes = characterElement.getAttributes();
        foregroundColor = StyleConstants.getForeground(attributes);
        if (foregroundColor == null) {
            foregroundColor = textPaneEditor.getForeground();

        }
        colorChooser.setColor(foregroundColor);

        JButton approveButton = new JButton("Approve");
        approveButton.setFont(new Font("Verdana", Font.BOLD, 12));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Verdana", Font.BOLD, 12));

        approveButton.addActionListener(new ActionListener() {
            /**
             * actionPerformed Method -
             * @param approveEvent - the ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent approveEvent) {
                foregroundColor = colorChooser.getColor();
                colorDialog.dispose();

            }//end of the actionPerformed Method for the Anonymous ActionListener
        });//end of the Anonymous ActionListener for the approveButton
        cancelButton.addActionListener(new ActionListener() {
            /**
             * actionPerformed Method -
             * @param cancelEvent - the ActionEvent
             */
            @Override
            public void actionPerformed(ActionEvent cancelEvent) {
                hasCancelled = true;
                colorDialog.dispose();

            }//end of the actionPerformed Method for the Anonymous ActionListener
        });//end of the Anonymous ActionListener for the cancelButton
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(approveButton);
        buttonPanel.add(cancelButton);

        colorDialog.getContentPane().setLayout(new BorderLayout());
        colorDialog.getContentPane().add(colorChooser, BorderLayout.CENTER);
        colorDialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        colorDialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
        /*colorDialog.pack();*/
        colorDialog.setSize(620, 400);
        colorDialog.setLocationRelativeTo(textPaneEditor);
        colorDialog.setVisible(true);

        if (!hasCancelled) {
            @SuppressWarnings("UnusedAssignment")
            MutableAttributeSet mutableAttributes = null;

            if (textPaneEditor != null) {
                if ((foregroundColor != null) && (!hasChanged)) {
                    mutableAttributes = new SimpleAttributeSet();
                    StyleConstants.setForeground(mutableAttributes, foregroundColor);
                    setCharacterAttributes(textPaneEditor, mutableAttributes, false);
                }
            }
        }
        hasChanged = false;
        hasCancelled = false;

    }//end of the actionPerformed Method
}//end of JavaTextEditorColorChooser Class

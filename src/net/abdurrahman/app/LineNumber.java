package net.abdurrahman.app;

import java.awt.Component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * LineNumber Class -
 * @author MAbdurrahman
 * @date 17 July 2024
 * @version 1.0.0
 */
public class LineNumber extends JComponent {
    //Instance variables
    public static final int LEFT_ALIGNMENT = 0;
    public static final int RIGHT_ALIGNMENT = 1;
    public static final int CENTER_ALIGNMENT = 2;
    private static final int HORIZONTAL_PADDING = 1;
    private static final int VERTICAL_PADDING = 3;
    private int alignment = LEFT_ALIGNMENT;
    private EditorLineNumber editorLineNumber;


    /**
     * Default LineNumber Constructor -
     */
    public LineNumber() {
        super();
    }//end of Default LineNumber Constructor

    public LineNumber(EditorLineNumber lineNumber) {
        this();
        setLineNumber(lineNumber);

    }//end of LineNumber Constructor with 1 parameter


    /**
     * adjustWidth Method -
     */
    public void adjustWidth() {
        int max = editorLineNumber.getNumberLines();

        if (getGraphics() == null) {
            return;
        }
        int width = getGraphics().getFontMetrics().stringWidth(String.valueOf(max)) + 2 * HORIZONTAL_PADDING;

        JComponent component = (JComponent) getParent();
        if (component == null) {
            return;
        }

        Dimension dimension = component.getPreferredSize();

        if (component instanceof JViewport) {
            JViewport viewport = (JViewport) component;
            Component parent = viewport.getParent();
            if (parent != null && parent instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) viewport.getParent();
                dimension = scrollPane.getViewport().getView().getPreferredSize();

            }
        }
        if ( width > getPreferredSize().width || width < getPreferredSize().width){
            setPreferredSize(new Dimension(width + 2 * HORIZONTAL_PADDING, dimension.height));

            revalidate();
            repaint();

        }
    }//end of adjustWidth Method

    /**
     * getAlignment Method -
     * @return int - returns an int representing the alignment
     */
    public int getAlignment() {
        return alignment;

    }//end of getAlignment Method

    /**
     * paintComponent Method -
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if ( editorLineNumber == null ){
            return;

        }

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(getBackground());

        g2d.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(getForeground());

        //iterate over all lines to draw the line numbers.

        for ( int i = 0; i < editorLineNumber.getNumberLines(); i++ ){
            Rectangle rect = editorLineNumber.getLineRectangle(i);

            String text = String.valueOf(i + 1);

            int yPosition = rect.y + rect.height - VERTICAL_PADDING;
            int xPosition = HORIZONTAL_PADDING;//default to left alignment

            switch (alignment){
                case RIGHT_ALIGNMENT:
                    xPosition = getPreferredSize().width - g.getFontMetrics().stringWidth(text) - HORIZONTAL_PADDING;
                    break;
                case CENTER_ALIGNMENT:
                    xPosition = getPreferredSize().width / 2  - g.getFontMetrics().stringWidth(text) / 2;
                    break;
                default://left alignment, do nothing
                    break;
            }
            g2d.drawString(String.valueOf(i+1), xPosition, yPosition);

        }
    }//end of paintComponent Method

    /**
     * setAlignment Method -
     * @param alignment - an int representing the alignment
     * @throws IllegalArgumentException
     */
    public void setAlignment(int alignment) throws IllegalArgumentException {
        if ( alignment < 0 || alignment > 2 ){
            throw new IllegalArgumentException("Invalid Alignment Option!");

        }

        this.alignment = alignment;

    }//end of setAlignment Method

    /**
     * setLineNumber Method -
     * @param lineNumber
     */
    public void setLineNumber(EditorLineNumber lineNumber) {
        editorLineNumber = lineNumber;
        if (editorLineNumber != null) {
            adjustWidth();

        }
        repaint();

    }//end of setLineNumber Method



}//end of LineNumber Class

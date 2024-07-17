package net.abdurrahman.app;


import java.awt.Rectangle;

/**
 * EditorLineNumber Interface -
 * @author MAbdurrahman
 * @date 17 July 2024
 * @version 1.0.0
 */
public interface EditorLineNumber {
    /**
     * getNumberLines -
     * @return int
     */
    public int getNumberLines();

    /**
     * getLineRectangle -
     * @param line - int representing the line
     * @return Rectangle
     */
    public Rectangle getLineRectangle(int line);

}//end of EditorLineNumber Interface

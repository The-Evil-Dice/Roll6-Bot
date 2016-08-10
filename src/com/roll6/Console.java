package com.roll6;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 *
 * @author The_Evil_Dice
 */
public class Console extends OutputStream {
    
    private JTextArea textArea;
    
    public Console(JTextArea textArea){
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char)b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
        //textArea.setForeground(Color.red);
    }
    
}


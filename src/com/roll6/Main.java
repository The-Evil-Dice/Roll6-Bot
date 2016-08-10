package com.roll6;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author The_Evil_Dice
 */
public class Main {
    
    public static Bot bot;
    public static GUI gui;
    public static Config config;
    public static Console console;

    public static void main(String[] args) {
        
        try {
            bot = new Bot();
            gui = new GUI();
            config = new Config();
            
            gui.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


}

package com.roll6;

import com.roll6.gui.Console;
import com.roll6.gui.GUIhandler;
import com.roll6.config.Config;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author The_Evil_Dice
 */
public class Main {
    
    public static Bot bot;
    public static GUIhandler gui;
    public static Config config;
    public static Console console;

    public static void main(String[] args) {
        
        try {
            bot = new Bot();
            config = new Config();
            gui = new GUIhandler();
            
            
            gui.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


}

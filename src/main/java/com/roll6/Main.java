/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roll6;

import com.roll6.Core.Config;
import com.roll6.Core.PircbotX;
import com.roll6.GUI.GUIHandler;

/**
 *
 * @author The_Evil_Dice
 */
public class Main {
    
    public static GUIHandler guiHandler;
    public static Config configHandler;
    public static PircbotX bot;
    
    public static void main(String[] args) {
        bot = new PircbotX();
        configHandler = new Config();
        //configureBot();
        guiHandler = new GUIHandler();
        guiHandler.startGUI(args);
    }
}

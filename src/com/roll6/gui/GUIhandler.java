/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roll6.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author The_Evil_Dice
 */
public class GUIhandler extends JFrame {
    
    private Settings settings;
    private Console console;

    public JPanel DEFpanel;

    public GUIhandler() throws IOException {
        settings = new Settings();
        console = new Console(this);
        
        initialiseJFrame();
        addDEFpanel();

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Console", console.getConsole());
        tabs.add("Commands", new JPanel());
        tabs.add("Users", new JPanel());
        tabs.add("Music", new JPanel());
        tabs.add("Settings", settings.getSettingsPanel());

        DEFpanel.add(tabs, BorderLayout.CENTER);
    }
    
    public void printToConsole(String string) {
        console.printStream.println(string+"\0");
    }

    private void initialiseJFrame() {
        try {
            setTitle("The Evil Bot");
            setSize(900, 600);
            setResizable(false);
            BufferedImage bg = ImageIO.read(getClass().getResource("../favicon.png"));
            setIconImage(bg);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (IOException ex) {
            Logger.getLogger(GUIhandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createCommandsPanel(){
        
    }

    private void addDEFpanel() {
        DEFpanel = new JPanel();
        add(DEFpanel, BorderLayout.CENTER);
        DEFpanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        DEFpanel.setLayout(new BorderLayout());
        DEFpanel.setBackground(console.hex2Rgb("#555555"));
    }
}

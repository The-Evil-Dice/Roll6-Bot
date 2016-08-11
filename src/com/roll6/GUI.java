/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roll6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author The_Evil_Dice
 */
public class GUI extends JFrame {

    private JPanel DEFpanel;

    private JPanel CONSOLEpanel;
    private JTextArea console;

    private JPanel BTNpanel;

    private JButton button;
    private JButton enter;

    private JTextField inputArea;

    private Listener listener;

    PrintStream printStream;

    public GUI() throws IOException {
        listener = new Listener();
        initialiseJFrame();
        addDEFpanel();
        createBTNpanel();

        CONSOLEpanel = new JPanel();
        CONSOLEpanel.setLayout(new BorderLayout());
        console = new JTextArea(50, 10);
        console.setForeground(Color.BLUE);
        console.setLineWrap(true);
        console.setEditable(false);

        JScrollPane scroll = new JScrollPane(console);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        CONSOLEpanel.add(scroll);

        printStream = new PrintStream(new Console(console));
        //System.setOut(printStream);
        //System.setErr(printStream);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Console", CONSOLEpanel);
        tabs.add("Commands", new JPanel());
        tabs.add("Users", new JPanel());
        tabs.add("Music", new JPanel());
        tabs.add("Settings", new JPanel());

        DEFpanel.add(tabs, BorderLayout.CENTER);
    }
    
    private void initialiseJFrame(){
        try {
            setTitle("Roll6 Bot");
            setSize(900, 600);
            setResizable(false);
            BufferedImage bg = ImageIO.read(getClass().getResource("favicon.png"));
            setIconImage(bg);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addDEFpanel(){
        DEFpanel = new JPanel();
        add(DEFpanel, BorderLayout.CENTER);
        DEFpanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        DEFpanel.setLayout(new BorderLayout());
    }
    
    private void createBTNpanel(){
        BTNpanel = new JPanel();
        BTNpanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        BTNpanel.setLayout(new BorderLayout());

        add(BTNpanel, BorderLayout.SOUTH);

        button = new JButton("Start");
        button.addActionListener(listener);
        button.setActionCommand("START");
        button.setPreferredSize(new Dimension(100, 40));

        enter = new JButton("Enter");
        enter.addActionListener(listener);
        enter.setActionCommand("ENTER");
        enter.setPreferredSize(new Dimension(100, 40));

        inputArea = new JTextField(20);
        inputArea.setBackground(Color.WHITE);
        inputArea.setForeground(Color.DARK_GRAY);
        inputArea.setActionCommand("ENTER");
        inputArea.addActionListener(listener);

        BTNpanel.add(button, BorderLayout.WEST);
        BTNpanel.add(enter, BorderLayout.EAST);
        BTNpanel.add(inputArea, BorderLayout.CENTER);
    }

    public class Listener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            String cmd = e.getActionCommand();
            switch (cmd) {
                case "START":
                    if(Main.config.getOAuth() == "" ||
                            Main.config.getChannel() == "" ||
                            Main.config.getUsername() == ""){
                        Main.gui.printStream.println("[Roll6 Bot] > Could not connect! Check your settings!");
                        break;
                    }
                    Main.bot.configureBot();
                    button.setActionCommand("STOP");
                    button.setText("Stop");
                    break;
                case "STOP":
                    Main.bot.stopBot();
                    button.setText("Start");
                    button.setActionCommand("START");
                    break;
                case "ENTER":
                    if (inputArea.getText().toLowerCase().startsWith("?reload")) {
                        try {
                            Main.config.reloadCommands();
                            Main.config.reloadBanned();
                        } catch (IOException ex) {
                            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Main.gui.printStream.println("[Roll6 Bot] > Bot Reloaded!");
                        inputArea.setText("");
                        break;
                    }
                    Main.bot.bot.send().message("#" + Main.config.getChannel(), inputArea.getText());
                    Main.gui.printStream.println(Main.config.getUsername() + " > " + inputArea.getText());
                    inputArea.setText("");
                    break;
            }
        }

    }
}

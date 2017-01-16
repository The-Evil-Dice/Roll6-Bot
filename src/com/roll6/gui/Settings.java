/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roll6.gui;

import com.roll6.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author The_Evil_Dice
 */
public class Settings extends JFrame {

    private JPanel SETTINGSpanel = null;
    private Listener listener;

    Settings() {
        listener = new Listener();
        
        SETTINGSpanel = new JPanel();
        SETTINGSpanel.setLayout(new GridLayout(4, 1, 5, 5));

        JPanel LOGINsubpanel = new JPanel();
        LOGINsubpanel.setLayout(new GridLayout(1,2));
        JPanel UsernameFill = new JPanel();
        JPanel UsernameBorder = new JPanel();
        UsernameBorder.setLayout(new BorderLayout());
        UsernameBorder.setBorder(BorderFactory.createTitledBorder("Username"));
        JTextField UsernameField = new JTextField(32);
        UsernameBorder.add(UsernameField, BorderLayout.CENTER);
        UsernameBorder.add(new JButton("Enter"), BorderLayout.EAST);
        UsernameFill.add(UsernameBorder);
        LOGINsubpanel.setBorder(BorderFactory.createTitledBorder("Login"));
        LOGINsubpanel.add(UsernameFill);
        
        UsernameField.setText(Main.config.getUsername());
        
        JPanel OAUTHFill = new JPanel();
        JPanel OAUTHBorder = new JPanel();
        OAUTHBorder.setLayout(new BorderLayout());
        OAUTHBorder.setBorder(BorderFactory.createTitledBorder("OAUTH"));
        JTextField OAUTHField = new JTextField(32);
        OAUTHBorder.add(OAUTHField, BorderLayout.CENTER);
        OAUTHBorder.add(new JLabel("Eventually the bot will generate your OAUTH for you.", SwingConstants.CENTER), BorderLayout.SOUTH);
        JButton enterUsername = new JButton("Enter");
        
        OAUTHBorder.add(enterUsername, BorderLayout.EAST);
        OAUTHFill.add(OAUTHBorder);
        LOGINsubpanel.add(OAUTHFill);
        
        OAUTHField.setText(Main.config.getOAuth());

        JPanel CHANNELsubpanel = new JPanel();
        CHANNELsubpanel.setBorder(BorderFactory.createTitledBorder("Channel"));

        SETTINGSpanel.add(LOGINsubpanel);
        SETTINGSpanel.add(CHANNELsubpanel);
        
        UsernameField.setActionCommand("SETUSERNAME");
        UsernameField.addActionListener(listener);
        enterUsername.setActionCommand("SETUSERNAME");
        enterUsername.addActionListener(listener);
    }
    
    public JPanel getSettingsPanel() {
        if(SETTINGSpanel == null) return null;
        
        return SETTINGSpanel;
    }
    
    public class Listener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            String cmd = e.getActionCommand();
            switch (cmd) {
                case "SETUSERNAME" :
                    if(((JTextField)e.getSource()).getText().equalsIgnoreCase(Main.config.getUsername())){
                        break;
                    } else {
                        if(Main.config.setUsername(((JTextField)e.getSource()).getText())) {
                            ((JTextField)e.getSource()).setForeground(Color.GREEN);
                        } else {
                            ((JTextField)e.getSource()).setForeground(Color.RED);
                        }
                    }
            }
        }
        
    }
}

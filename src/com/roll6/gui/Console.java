package com.roll6.gui;

import com.roll6.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author The_Evil_Dice
 */
public class Console extends JFrame {

    private GUIhandler handler;

    private JPanel CONSOLEpanel;
    private JTextPane console;
    private JPanel BTNpanel;
    private JTextField inputArea;
    private JButton button;
    private JButton enter;
    public PrintStream printStream;

    public Listener listener;

    Console(GUIhandler handler) {
        this.handler = handler;
        listener = new Listener();

        CONSOLEpanel = new JPanel();
        CONSOLEpanel.setLayout(new BorderLayout());
        console = new JTextPane();
        
        JScrollPane scroll = new JScrollPane(console);
        StyledDocument doc = console.getStyledDocument();
        console.setBackground(hex2Rgb("#333333"));
        console.setEditable(false);

        CONSOLEpanel.add(scroll, BorderLayout.CENTER);
        createBTNpanel();

        printStream = new PrintStream(new ConsoleMethod(console));
    }

    public JPanel getConsole() {
        return CONSOLEpanel;
    }
    
    public Color hex2Rgb(String colorStr) {
            return new Color(
                    Integer.valueOf(colorStr.substring(1, 3), 16),
                    Integer.valueOf(colorStr.substring(3, 5), 16),
                    Integer.valueOf(colorStr.substring(5, 7), 16));
        }

    private void createBTNpanel() {
        BTNpanel = new JPanel();
        BTNpanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        BTNpanel.setLayout(new BorderLayout());

        CONSOLEpanel.add(BTNpanel, BorderLayout.SOUTH);

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

    public class ConsoleMethod extends OutputStream {

        private JTextPane textArea;
        private StringBuilder out;

        public ConsoleMethod(JTextPane textArea) {
            this.textArea = textArea;
            out = new StringBuilder();
        }

        @Override
        public void write(int b) throws IOException {
            out.append((char) b);
            if (out.toString().endsWith("\0")) {
                writeToConsole(new String(out));
                textArea.setCaretPosition(textArea.getDocument().getLength());
                out.setLength(0);
            }
        }

        public void writeToConsole(String text) {
            String colour = "#DDDDDD";
            String textOut = text.replaceAll("[\u0000-\u001f]", "");
            if (textOut.startsWith("#")) {
                colour = textOut.substring(0, 7);
                textOut = textOut.substring(8);
            }
            String[] textArray = textOut.split(" > ");
            appendToConsole(textArray[0], colour, true);
            if (textArray.length > 1) {
                appendToConsole(": " + textArray[1], "#DDDDDD", false);
            }
        }

        public void appendToConsole(String text, String colour, boolean newLine) {
            StyleContext sc = new StyleContext();
            Style style = sc.addStyle("test", null);

            StyleConstants.setForeground(style, hex2Rgb(colour));
            StyleConstants.setFontSize(style, 15);
            StyleConstants.setFontFamily(style, "Monospaced");
            StyleConstants.setBold(style, true);
            //StyleConstants.setBackground(style, hex2Rgb("#333333"));
            int len = textArea.getDocument().getLength();
            try {
                if (len == 0) {
                    textArea.getStyledDocument().insertString(len, text, style);
                } else {
                    textArea.getStyledDocument().insertString(len,
                            newLine == true ? "\n"+text : text, style);
                }
            } catch (BadLocationException ex) {
                Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public class Listener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            String cmd = e.getActionCommand();
            switch (cmd) {
                case "START":
                    if (Main.config.getOAuth() == ""
                            || Main.config.getChannel() == ""
                            || Main.config.getUsername() == "") {
                        Main.gui.printToConsole("The Evil Bot could not connect, check your settings!");
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
                            Logger.getLogger(GUIhandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Main.gui.printToConsole("The Evil Bot Reloaded!");
                        inputArea.setText("");
                        break;
                    }
                    if (button.getActionCommand().equals("STOP")) {
                        Main.bot.bot.send().message("#" + Main.config.getChannel(), inputArea.getText());
                        Main.gui.printToConsole(Main.config.getUsername() + " > " + inputArea.getText());
                        inputArea.setText("");
                        break;
                    }
            }
        }

    }

}

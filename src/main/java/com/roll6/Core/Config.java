package com.roll6.Core;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.roll6.Main;
import com.roll6.GUI.Pages.Console;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author The_Evil_Dice
 */
public class Config {

    private Settings settings;
    private BannedWords bannedWords;
    private Commands commands;
    private Users users;

    public Config() {
        try {
            importSettings();
            importUsers();
            importBanned();
            importCommands();
        } catch (Exception ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean setUsername(String string) {
        settings.replace("Username", string);
        try {
            YamlWriter writer = new YamlWriter(new FileWriter("Settings.yml"));
            writer.getConfig().setClassTag("Settings", Settings.class);
            writer.write(settings);
            writer.close();
        } catch (YamlException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        Main.bot.changeUsername();
        return true;
    }
    
    public boolean setOAUTH(String string) {
        settings.replace("OAUTH", string);
        try {
            YamlWriter writer = new YamlWriter(new FileWriter("Settings.yml"));
            writer.getConfig().setClassTag("Settings", Settings.class);
            writer.write(settings);
            writer.close();
        } catch (YamlException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        Main.bot.changePassword();
        return true;
    }

    public String getUsername() {
        return (String) settings.get("Username");
    }

    public String getOAuth() {
        return (String) settings.get("OAuth");
    }

    public String getChannel() {
        return (String) settings.get("Channel");
    }

    public int getMaxCaps() {
        return Integer.parseInt((String) settings.get("Max Caps"));
    }

    public List returnBanned() {
        return bannedWords;
    }

    public void setBanned(List bannedWords) {
        this.bannedWords = (BannedWords) bannedWords;
    }

    public HashMap returnCommands() {
        return commands;
    }

    public String returnCommandsFile() throws FileNotFoundException {
        String content = new Scanner(new File("Commands.yml")).useDelimiter("\\Z").next();
        return content;
    }

    public void setCommands(String commands) throws FileNotFoundException, IOException {
        try (PrintWriter out = new PrintWriter("Commands.yml")) {
            out.println(commands);
        }
        reloadCommands();
    }

    public void reload() throws IOException {
        reloadBanned();
        reloadCommands();
    }

    public void reloadBanned() throws IOException {
        YamlReader reader = new YamlReader(new FileReader("BannedWords.yml"));
        reader.getConfig().setClassTag("BannedWords", BannedWords.class);
        bannedWords = reader.read(BannedWords.class);
    }

    public void reloadSettings() throws IOException {
        YamlReader reader = new YamlReader(new FileReader("Settings.yml"));
        reader.getConfig().setClassTag("Settings", Settings.class);
        settings = reader.read(Settings.class);
    }

    public void reloadCommands() throws IOException {
        YamlReader reader = new YamlReader(new FileReader("Commands.yml"));
        reader.getConfig().setClassTag("Commands", Commands.class);
        commands = reader.read(Commands.class);
    }

    private void importBanned() throws IOException {
        File file = new File("BannedWords.yml");
        if (!file.exists() || !file.isFile()) {
            BannedWords temp = new BannedWords();
            temp.add("Hitler");
            temp.add("Nazi");

            YamlWriter writer = new YamlWriter(new FileWriter("BannedWords.yml"));
            writer.getConfig().setClassTag("BannedWords", BannedWords.class);
            writer.write(temp);
            writer.close();
        }

        YamlReader reader = new YamlReader(new FileReader("BannedWords.yml"));
        reader.getConfig().setClassTag("BannedWords", BannedWords.class);
        bannedWords = reader.read(BannedWords.class);
    }

    private void importSettings() throws IOException {
        File file = new File("Settings.yml");
        if (!file.exists() || !file.isFile()) {
            Settings temp = new Settings();
            temp.put("Username", "");
            temp.put("OAuth", "");
            temp.put("Channel", "");
            temp.put("Max Caps", "15");

            YamlWriter writer = new YamlWriter(new FileWriter("Settings.yml"));
            writer.getConfig().setClassTag("Settings", Settings.class);
            writer.write(temp);
            writer.close();
        }

        YamlReader reader = new YamlReader(new FileReader("Settings.yml"));
        reader.getConfig().setClassTag("Settings", Settings.class);
        settings = reader.read(Settings.class);
    }

    private void importCommands() throws IOException {
        File file = new File("Commands.yml");
        if (!file.exists() || !file.isFile()) {
            Commands temp = new Commands();
            temp.put("!hello", "Hello &user&!");
            temp.put("!hug", "&user& hugs the fuck out of &arg1&!");

            YamlWriter writer = new YamlWriter(new FileWriter("Commands.yml"));
            writer.getConfig().setClassTag("Commands", Commands.class);
            writer.write(temp);
            writer.close();
        }

        YamlReader reader = new YamlReader(new FileReader("Commands.yml"));
        reader.getConfig().setClassTag("Commands", Commands.class);
        commands = reader.read(Commands.class);
    }

    private void importUsers() throws IOException {
        File file = new File("Users.yml");
        if (!file.exists() || !file.isFile()) {
            Users temp = new Users();
            Map temp2 = new HashMap<>();
            temp2.put("Colour", "#CC6D00");
            temp2.put("Rank", 0);
            temp2.put("Moderator", false);
            temp2.put("Currency", 0);
            temp.put("ExampleUser", temp2);

            YamlWriter writer = new YamlWriter(new FileWriter("Users.yml"));
            writer.getConfig().setClassTag("Users", Users.class);
            writer.write(temp);
            writer.close();
        }

        YamlReader reader = new YamlReader(new FileReader("Users.yml"));
        reader.getConfig().setClassTag("Users", Users.class);
        users = reader.read(Users.class);
    }

    private static class Commands extends HashMap {
    }

    private static class Settings extends HashMap {
    }

    private static class BannedWords extends ArrayList {
    }

    private static class Users extends HashMap {
    }
}

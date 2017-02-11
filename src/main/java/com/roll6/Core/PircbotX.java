package com.roll6.Core;

import com.roll6.Main;
import com.roll6.GUI.Pages.Console;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 *
 * @author The_Evil_Dice
 */
 
public class PircbotX extends ListenerAdapter {
    
    public Console console;
    public static PircBotX bot;
    public static Thread thread;
    
    PircbotX(Console console) {
        this.console = console;
    }

    public PircbotX() {
        
    }
    
    public void changeUsername() {
        stopBot();
        console.print(true, "#00FF00", "Username Changed!");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                configureBot(console);
            }
        }, 2000);
    }
    
    public void changePassword() {
        stopBot();
        console.print(true, "#00FF00", "Password Changed!");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                configureBot(console);
            }
        }, 2000);
    }
    
    public PircBotX getBot() {
        return bot;
    }

    @Override
    public void onConnect(ConnectEvent event){
        console.print("Roll6-Bot Connected!");
    }
    
    @Override
    public void onDisconnect(DisconnectEvent event){
        console.print("Roll6-Bot Disconnected!");
    }
    
    @Override
    public void onGenericMessage(GenericMessageEvent event) {
        String user = ((MessageEvent)event).getTags().get("display-name");
        String colour = ((MessageEvent)event).getTags().get("color");
        String[] args = event.getMessage().split(" ");
        Boolean mod = Boolean.parseBoolean(((MessageEvent)event).getTags().get("moderator"));
        Boolean broadcaster = ((MessageEvent)event).getTags().get("badges").contains("broadcaster");
        console.print(true, colour, user, "#NNNNNN", ": "+event.getMessage());
        if (!mod && !broadcaster) {
            if (event.getMessage().matches("[A-Z]{" + Main.configHandler.getMaxCaps() + ",}")) {
                event.respondWith("/timeout " + user + " 1");
                event.respondWith(user + " chat purged for exceeding max caps!");
                console.print(Main.configHandler.getUsername() + " > " + user + " chat purged for exceeding max caps!");
                return;
            }
            for (Object bannedWord : Main.configHandler.returnBanned()) {
                String bannedWordString = (String) bannedWord;
                if (event.getMessage().toLowerCase().contains(bannedWordString.toLowerCase())) {
                    event.respondWith("/timeout " + user + " 1");
                    event.respondWith(user + " chat purged for using banned word!");
                    console.print(Main.configHandler.getUsername() + " > " + user + " chat purged for using banned word!");
                    return;
                }
            }
        }
        for (Object command : Main.configHandler.returnCommands().keySet()) {
            String commandString = (String) command;
            if(event.getMessage().length() > commandString.length()) commandString += " ";
            if (event.getMessage().toLowerCase().startsWith(commandString.toLowerCase())) {
                String response = Main.configHandler.returnCommands().get(commandString).toString();
                response = response.replace("&user&", user);
                switch (args.length) {
                    case 1:
                        break;
                    case 2:
                        response = response.replace("&arg1&", args[1]);
                        break;
                    default:
                        response = response.replace("&arg1&", args[1]);
                        response = response.replace("&arg2&", args[2]);
                        break;
                }
                event.respondWith(response);
                console.print(Main.configHandler.getUsername() + " > " + response);
                return;
            }
        }
    }

    public void configureBot(Console gui) {
        this.console = gui;
        thread = new Thread(() -> {
            Configuration configuration = new Configuration.Builder()
                    .setAutoNickChange(false)
                    .setOnJoinWhoEnabled(false)
                    .setCapEnabled(true)
                    .addCapHandler(new EnableCapHandler("twitch.tv/membership"))
                    .addCapHandler(new EnableCapHandler("twitch.tv/tags"))
                    .addServer("irc.twitch.tv")
                    .setName(Main.configHandler.getUsername())
                    .setServerPassword("oauth:" + Main.configHandler.getOAuth())
                    .addAutoJoinChannel("#" + Main.configHandler.getChannel())
                    .addListener(new PircbotX(gui))
                    .buildConfiguration();

            bot = new PircBotX(configuration);
            try {
                console.print("Roll6-Bot Connecting...");
                bot.startBot();
            } catch (IOException | IrcException ex) {
                console.print(ex.getLocalizedMessage());
            }
        });
        thread.start();
    }

    public void stopBot() {
        bot.close();
        thread.interrupt();
    }
}
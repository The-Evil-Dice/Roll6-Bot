package com.roll6;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Bot extends ListenerAdapter {

    public static PircBotX bot; //Test
    public static Thread thread;

    @Override
    public void onConnect(ConnectEvent event){
        Main.gui.printStream.println("Roll6 Bot Connected!");
    }
    
    @Override
    public void onDisconnect(DisconnectEvent event){
        Main.gui.printStream.println("Bot Disconnected!");
    }
    
    @Override
    public void onGenericMessage(GenericMessageEvent event) {
        String user = ((MessageEvent)event).getTags().get("display-name");
        String[] args = event.getMessage().split(" ");
        Boolean mod = Boolean.parseBoolean(((MessageEvent)event).getTags().get("moderator"));
        Boolean broadcaster = ((MessageEvent)event).getTags().get("badges").contains("broadcaster");
        Main.gui.printStream.println(user+" > " + event.getMessage());
        if (!mod && !broadcaster) {
            if (event.getMessage().matches("[A-Z]{" + Main.config.getMaxCaps() + ",}")) {
                event.respondWith("/timeout " + user + " 1");
                event.respondWith(user + " chat purged for exceeding max caps!");
                Main.gui.printStream.println(Main.config.getUsername() + " > " + user + " chat purged for exceeding max caps!");
                return;
            }
            for (Object bannedWord : Main.config.returnBanned()) {
                String bannedWordString = (String) bannedWord;
                if (event.getMessage().toLowerCase().contains(bannedWordString.toLowerCase())) {
                    event.respondWith("/timeout " + user + " 1");
                    event.respondWith(user + " chat purged for using banned word!");
                    Main.gui.printStream.println(Main.config.getUsername() + " > " + user + " chat purged for using banned word!");
                    return;
                }
            }
        }
        for (Object command : Main.config.returnCommands().keySet()) {
            String commandString = (String) command;
            if (event.getMessage().toLowerCase().startsWith(commandString.toLowerCase())) {
                String response = Main.config.returnCommands().get(commandString).toString();
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
                Main.gui.printStream.println(Main.config.getUsername() + " > " + response);
                return;
            }
        }
    }

    public static void configureBot() {
        thread = new Thread(() -> {
            Configuration configuration = new Configuration.Builder()
                    .setAutoNickChange(false)
                    .setOnJoinWhoEnabled(false)
                    .setCapEnabled(true)
                    .addCapHandler(new EnableCapHandler("twitch.tv/membership"))
                    .addCapHandler(new EnableCapHandler("twitch.tv/tags"))
                    .addServer("irc.twitch.tv")
                    .setName(Main.config.getUsername())
                    .setServerPassword("oauth:" + Main.config.getOAuth())
                    .addAutoJoinChannel("#" + Main.config.getChannel())
                    .addListener(new Bot())
                    .buildConfiguration();

            bot = new PircBotX(configuration);
            try {
                Main.gui.printStream.println("Roll6 Bot Connecting...");
                bot.startBot();
            } catch (IOException | IrcException ex) {
                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread.start();
    }

    public static void stopBot() {
        bot.close();
        thread.interrupt();
    }

}

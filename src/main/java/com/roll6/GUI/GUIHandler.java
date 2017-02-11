/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roll6.GUI;

import com.roll6.GUI.Pages.Console;
import com.roll6.GUI.Pages.Settings;
import com.roll6.Main;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author The_Evil_Dice
 */
public class GUIHandler extends Application {

    public Stage window;
    public Console console;
    public Settings settings;

    public GUIHandler() {
        
    }

    public void startGUI(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        console = new Console();
        settings = new Settings(this);
        Main.bot.configureBot(console);

        window.setOnCloseRequest(e -> {Main.bot.stopBot(); window.close();});
        window.setTitle("Roll6 Bot");
        window.setWidth(900);
        window.setHeight(600);
        window.setMinHeight(600);
        window.setMinWidth(900);
        window.setResizable(false);

        Image icon = new Image(Main.class.getResourceAsStream("/images/favicon.png"));
        window.getIcons().add(icon);

        TabPane tabs = new TabPane();

        Tab consoleTab = new Tab("Console");
        consoleTab.setClosable(false);
        consoleTab.setContent(console.getLayout());

        Tab commandsTab = new Tab("Commands");
        commandsTab.setClosable(false);

        Tab usersTab = new Tab("Users");
        usersTab.setClosable(false);

        Tab musicTab = new Tab("Music");
        musicTab.setClosable(false);

        Tab settingsTab = new Tab("Settings");
        settingsTab.setClosable(false);
        settingsTab.setContent(settings.getLayout());

        tabs.getTabs().addAll(consoleTab, commandsTab, usersTab, musicTab, settingsTab);

        Scene scene = new Scene(tabs);
        scene.getStylesheets().add(Main.class.getResource("/css/style.css").toExternalForm());

        window.setScene(scene);

        this.window = window;
        this.window.show();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        for (Object key : Main.configHandler.returnCommands().keySet()) {
                            console.print((String) key + " : " + Main.configHandler.returnCommands().get(key));
                        }
                        //Main.bot.configureBot();
                    }
                });
            }
        }, 2000);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roll6.GUI.Pages;

import com.roll6.GUI.GUIHandler;
import com.roll6.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author The_Evil_Dice
 */
public class Settings {

    private GridPane layout;
    private GUIHandler guiHandler;

    public Settings(GUIHandler guiHandler) {
        this.guiHandler = guiHandler;
        layout = new GridPane();
        initialiseGrid(layout, 12, 8);

        GridPane login = new GridPane();
        login.getStyleClass().add("content");
        initialiseGrid(login, 6, 2);

        Label loginLabel = new Label("Login");
        loginLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        loginLabel.getStyleClass().add("title");
        loginLabel.setAlignment(Pos.TOP_LEFT);

        TextField loginInput = new TextField(Main.configHandler.getUsername());
        loginInput.getStyleClass().add("input-area");
        loginInput.setOnAction(e -> {
            if (!loginInput.getText().equals(Main.configHandler.getUsername())) {
                Main.configHandler.setUsername(loginInput.getText());
            }
        });

        Button loginEnter = new Button("Enter");
        loginEnter.setMaxWidth(Double.MAX_VALUE);
        loginEnter.getStyleClass().add("button-sm");
        loginEnter.setOnAction(e -> {
            if (!loginInput.getText().equals(Main.configHandler.getUsername())) {
                Main.configHandler.setUsername(loginInput.getText());
            }
        });

        login.add(loginLabel, 0, 0, 1, 2);
        login.add(loginInput, 1, 0, 5, 1);
        login.add(loginEnter, 4, 1, 2, 1);

        GridPane OAUTH = new GridPane();
        OAUTH.getStyleClass().add("content");
        initialiseGrid(OAUTH, 6, 2);

        Label OAUTHLabel = new Label("OAUTH");
        OAUTHLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        OAUTHLabel.getStyleClass().add("title");
        OAUTHLabel.setAlignment(Pos.TOP_LEFT);

        TextField OAUTHInput = new TextField(Main.configHandler.getOAuth());
        OAUTHInput.getStyleClass().add("input-area");
        OAUTHInput.setOnAction(e -> {
            if (!OAUTHInput.getText().equals(Main.configHandler.getOAuth())) {
                Main.configHandler.setOAUTH(OAUTHInput.getText());
            }
        });
        
        Label NotificationLabel = new Label("Will eventually get OAUTH from password");
        NotificationLabel.setWrapText(true);
        NotificationLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Button OAUTHEnter = new Button("Enter");
        OAUTHEnter.setMaxWidth(Double.MAX_VALUE);
        OAUTHEnter.getStyleClass().add("button-sm");
        OAUTHEnter.setOnAction(e -> {
            if (!OAUTHInput.getText().equals(Main.configHandler.getOAuth())) {
                Main.configHandler.setOAUTH(OAUTHInput.getText());
            }
        });

        OAUTH.add(OAUTHLabel, 0, 0, 1, 2);
        OAUTH.add(OAUTHInput, 1, 0, 5, 1);
        OAUTH.add(NotificationLabel, 1, 1, 3, 1);
        OAUTH.add(OAUTHEnter, 4, 1, 2, 1);

        layout.add(login, 0, 0, 6, 2);
        layout.add(OAUTH, 6, 0, 6, 2);

        GridPane.setMargin(login, new Insets(10));
        GridPane.setMargin(loginInput, new Insets(0, 10, 0, 10));
        GridPane.setMargin(loginEnter, new Insets(0, 10, 10, 10));
        GridPane.setFillWidth(loginLabel, true);
        GridPane.setFillHeight(loginLabel, true);

        GridPane.setMargin(OAUTH, new Insets(10));
        GridPane.setMargin(OAUTHInput, new Insets(0, 10, 0, 10));
        GridPane.setMargin(OAUTHEnter, new Insets(0, 10, 10, 10));
        GridPane.setMargin(NotificationLabel, new Insets(0, 10, 10, 10));
        GridPane.setFillWidth(OAUTHLabel, true);
        GridPane.setFillHeight(OAUTHLabel, true);
        GridPane.setFillWidth(NotificationLabel, true);
        GridPane.setFillHeight(NotificationLabel, true);
    }

    private void initialiseGrid(GridPane grid, int numCols, int numRows) {
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            grid.getRowConstraints().add(rowConst);
        }
        //grid.setGridLinesVisible(true);
        //grid.setPadding(new Insets(10));
    }

    public GridPane getLayout() {
        return layout;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roll6.GUI.Pages;

import com.roll6.GUI.Extensions.ColouredText;
import com.roll6.Main;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author The_Evil_Dice
 */
public class Console {

    private GridPane layout;
    private TextArea console;
    private TextFlow console2;
    private boolean consoleStyling = false;

    public Console() {
        initialiseGrid();

        TextField inputBox = new TextField();
        inputBox.setOnAction(e -> {
            if (inputBox.getText().length() > 0) {
                print(Main.configHandler.getUsername() + ": " + inputBox.getText());
                Main.bot.getBot().send().message("#" + Main.configHandler.getChannel(), inputBox.getText());
                inputBox.clear();
            }
        });
        inputBox.getStyleClass().add("input-area-lg");

        Button inputButton = new Button("Enter");
        //inputButton.setFont(new Font(20));
        inputButton.setMaxWidth(Double.MAX_VALUE);
        inputButton.setOnAction(e -> {
            if (inputBox.getText().length() > 0) {
                print(Main.configHandler.getUsername() + ": " + inputBox.getText());
                Main.bot.getBot().send().message("#" + Main.configHandler.getChannel(), inputBox.getText());
                inputBox.clear();
            }
        });

        console = new TextArea();
        console.setEditable(false);
        console.getStyleClass().add("console");

        console2 = new TextFlow();
        console2.getStyleClass().add("console");

        layout.add(console2, 0, 0, 12, 7);
        layout.add(inputBox, 0, 7, 10, 1);
        layout.add(inputButton, 10, 7, 2, 1);

        GridPane.setMargin(inputBox, new Insets(10, 0, 0, 0));
        GridPane.setMargin(inputButton, new Insets(10, 0, 0, 10));
    }

    private void initialiseGrid() {
        layout = new GridPane();
        final int numCols = 12;
        final double numRows = 600 / 75;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            layout.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            layout.getRowConstraints().add(rowConst);
        }
        //layout.setGridLinesVisible(true);
        layout.setPadding(new Insets(10));
    }

    private void resizeGrid() {
        layout = new GridPane();
        final int numCols = 12;
        final double numRows = Main.guiHandler.window.getHeight() / 75;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            layout.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            layout.getRowConstraints().add(rowConst);
        }
        //layout.setGridLinesVisible(true);
        layout.setPadding(new Insets(10));
    }

    public GridPane getLayout() {
        return layout;
    }

    public void print(List<ColouredText> text) {
        Platform.runLater(() -> {
            TextFlow test = new TextFlow();
            test.setPrefWidth(console2.getWidth()
                    - (console2.getBorder().getInsets().getLeft()
                    + console2.getBorder().getInsets().getRight())
            );
            test.getStyleClass().add("console-line");
            for (ColouredText t : text) {
                test.getChildren().add(t);
            }
            if (consoleStyling) {
                test.getStyleClass().add("console-line-light");
                consoleStyling = false;
            } else {
                consoleStyling = true;
            }
            console2.getChildren().addAll(test, new Text("\n"));
        });
    }

    public void print(boolean coloured, String... text) {
        Platform.runLater(() -> {
            String colour = null;
            String string = null;
            List<ColouredText> textParts = new ArrayList<>();
            for (int i = 0; i < text.length; i++) {
                if (i % 2 == 0) {
                    colour = text[i];
                } else {
                    string = text[i];
                    textParts.add(new ColouredText(string, colour));
                }
            }
            TextFlow test = new TextFlow();
            test.setPrefWidth(console2.getWidth()
                    - (console2.getBorder().getInsets().getLeft()
                    + console2.getBorder().getInsets().getRight())
            );
            test.getStyleClass().add("console-line");
            for (ColouredText t : textParts) {
                test.getChildren().add(t);
            }
            if (consoleStyling) {
                test.getStyleClass().add("console-line-light");
                consoleStyling = false;
            } else {
                consoleStyling = true;
            }
            console2.getChildren().addAll(test, new Text("\n"));
        });
    }

    public void print(String string) {
        Platform.runLater(() -> {
            Text text = new Text(string);
            TextFlow test = new TextFlow();
            test.setPrefWidth(console2.getWidth()
                    - (console2.getBorder().getInsets().getLeft()
                    + console2.getBorder().getInsets().getRight())
            );
            test.getStyleClass().add("console-line");
            text.getStyleClass().add("console-text");
            test.getChildren().add(text);
            if (consoleStyling) {
                test.getStyleClass().add("console-line-light");
                consoleStyling = false;
            } else {
                consoleStyling = true;
            }
            console2.getChildren().addAll(test, new Text("\n"));
        });
    }

}

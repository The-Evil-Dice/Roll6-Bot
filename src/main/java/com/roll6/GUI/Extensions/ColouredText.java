/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roll6.GUI.Extensions;

import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 *
 * @author The_Evil_Dice
 */
public class ColouredText extends Text {

    public ColouredText(String string, String colour) {
        this.setText(string);
        if (colour == "#NNNNNN") {
            this.getStyleClass().add("console-text");
        } else {
            this.setFill(Paint.valueOf(colour));
        }
    }

}

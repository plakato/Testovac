/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.components;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author plaka
 */
public class Singlechoice extends Question {
    private List<String> choices;
    private int correct;
    private String selected = "";
    public Singlechoice(String question, List<String> choices, int correct) {
        this.question = question;
        this.choices = choices;
        this.correct = correct;
    }
    public List<String> getChoices() {
        return choices;
    }

    @Override
    public Pane getPaneOfChoices() {
        VBox vbox = new VBox();
        ToggleGroup group = new ToggleGroup();
        for (String choice : choices) {
            RadioButton rb = new RadioButton(choice);
            rb.setToggleGroup(group);
            HBox hbox = new HBox(rb);
            vbox.getChildren().add(hbox);
        }
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue == null) {
                    selected = "";                   
                }
                else {
                    selected = ((RadioButton)newValue).getText();
                }
            }
            
        });
        return vbox;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.components;

import java.util.List;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author plaka
 */
public class Multichoice extends Question {
    private List<String> choices;
    private List<Integer> correct;
    public Multichoice(String question, List<String> choices, List<Integer> correct) {
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
        for (String choice : choices) {
            HBox hbox = new HBox(new CheckBox(), new Label(choice));
            vbox.getChildren().add(hbox);
        }
        return vbox;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.components;

import com.sun.javafx.binding.BidirectionalBinding;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private List<Boolean> selected;
    public Multichoice(String question, List<String> choices, List<Integer> correct) {
        this.question = question;
        this.choices = choices;
        this.correct = correct;
        selected = new ArrayList<Boolean>();
    }
    
    public List<String> getChoices() {
        return choices;
    }

    @Override
    public Pane getPaneOfChoices() {
        VBox vbox = new VBox();
        for (String choice : choices) {
            CheckBox checkbox = new CheckBox();            
            checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    selected.set(choices.indexOf(choice),newValue);
                }               
            });
            Label label = new Label(choice);
            HBox hbox = new HBox(checkbox, label);
            vbox.getChildren().add(hbox);
        }
        return vbox;
    }
}

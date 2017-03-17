/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import com.sun.javafx.binding.BidirectionalBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.Debugger;

/**
 *
 * @author plaka
 */
public class Multichoice extends Question {
    private List<String> choices;
    private List<Boolean> correct;
    private List<Boolean> selected;
    private double points;
    
    public Multichoice(String question, List<String> choices, List<Boolean> correct, double points) {
        this.question = question;
        this.choices = choices;
        this.correct = correct;
        this.points = points;
        selected = new ArrayList<Boolean>(Collections.nCopies(choices.size(), Boolean.FALSE));
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
                    System.out.println("New value is "+ newValue);
                    System.out.println("The size of the selected array is: " + selected.size());
                    selected.set(choices.indexOf(choice),newValue);
                }               
            });
            Label label = new Label(choice);
            HBox hbox = new HBox(checkbox, label);
            vbox.getChildren().add(hbox);
        }
        return vbox;
    }

    @Override
    public double getScore() {
        Debugger.println("Selected are: ");
        double result = 0;
        for (int i = 0; i < choices.size(); i++) {
            Debugger.println(choices.get(i) + ": " + selected.get(i));
            if (correct.get(i) == selected.get(i)) {
                result += points;
            }
        }
        
        return result;
    }
    
    @Override
    public double getPoints() {
        return points;
    }
    public List<Boolean> getCorrect() {
        return correct;
    }
}

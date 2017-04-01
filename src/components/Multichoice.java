/*
 * 
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
 * This class describes a multichoice question object for test.
 */

public class Multichoice extends Question {
    private List<String> choices;
    private List<Boolean> correct;
    private List<Boolean> selected;
    private double points;
    /** 
     * Constructor
     * @param question The text of the question - what is being asked.
     * @param choices List of all possible choices to choose from.
     * @param correct List in the same order as choices - determines
     *  which choice is correct using boolean value.
     * @param points Maximum amount of points possible to receive in this question.
     */
    public Multichoice(String question, List<String> choices, List<Boolean> correct, double points) {
        this.question = question;
        this.choices = choices;
        this.correct = correct;
        this.points = points;
        selected = new ArrayList<Boolean>(Collections.nCopies(choices.size(), Boolean.FALSE));
    }

    @Override
    public List<String> getChoices() {
        return choices;
    }

    @Override
    public Pane getPaneOfChoices() {
        VBox vbox = new VBox();
        for (String choice : choices) {
            CheckBox checkbox = new CheckBox();  
            checkbox.setStyle("-fx-font-size: 20");
            checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    Debugger.println("New value is "+ newValue);
                    Debugger.println("The size of the selected array is: " + selected.size());
                    selected.set(choices.indexOf(choice),newValue);
                }               
            });
            Label label = new Label(choice);
            label.setStyle("-fx-font-size: 20");
            HBox hbox = new HBox(checkbox, label);
            vbox.getChildren().add(hbox);
        }
        return vbox;
    }

    @Override
    public double getScore() {
        Debugger.println("Selected are: ");
        double result = 0;
        double pointsPerOne = points / choices.size();
        for (int i = 0; i < choices.size(); i++) {
            Debugger.println(choices.get(i) + ": " + selected.get(i));
            if (correct.get(i) == selected.get(i)) {
                result += pointsPerOne;
            }
        }
        
        return result;
    }
    
    @Override
    public double getPoints() {
        return points;
    }
    
    /**
     * Gives the list indicating which answers are correct
     * @return list of indicators
     */
    public List<Boolean> getCorrect() {
        return correct;
    }
}

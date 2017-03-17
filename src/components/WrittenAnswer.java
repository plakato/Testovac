/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import main.Debugger;

/**
 *
 * @author plaka
 */
public class WrittenAnswer extends Question {
    private List<String> possibleAnswers;
    private String writtenAnswer = "";
    private double points;
    
    public WrittenAnswer(String question, List<String> possibleAnswers, double points) {
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.points = points;
    }
    
    @Override
    public Pane getPaneOfChoices() {
        Pane pane = new Pane();
        TextField field = new TextField();
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                writtenAnswer = newValue;                       
            }
            
        });
        pane.getChildren().add(field);
        return pane;
    }

    @Override
    public double getScore() {
        Debugger.println("In written answer was entered: " + writtenAnswer);
        boolean foundCorrect = false;
        for (String answer : possibleAnswers) {
            if (writtenAnswer.equals(answer)) {
                foundCorrect = true;
            }
        }
        if (foundCorrect) {
            return points;
        }
        return 0;
    }

    @Override
    public double getPoints() {
        return points;
    }
    
    public List<String> getChoices() {
        return possibleAnswers;
    } 
    
}

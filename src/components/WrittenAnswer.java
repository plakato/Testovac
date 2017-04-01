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
 * This class describes a question with written answer for test.
 */
public class WrittenAnswer extends Question {
    private List<String> possibleAnswers;
    private String writtenAnswer = "";
    private double points;
    
    /**
     * Constructor
     * @param question test of the question
     * @param possibleAnswers all version of the correct answer
     * @param points maximum points
     */
    public WrittenAnswer(String question, List<String> possibleAnswers, double points) {
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.points = points;
    }
    
    @Override
    public Pane getPaneOfChoices() {
        Pane pane = new Pane();
        TextField field = new TextField();
        field.setStyle("-fx-font-size: 20");
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
    
    /**
     * Gives all the correct answers.
     * @return anweres
     */
    public List<String> getChoices() {
        return possibleAnswers;
    } 
    
}

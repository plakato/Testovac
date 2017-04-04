/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import main.Debugger;
import controllers.FXMLResultController;
import java.io.Serializable;

/**
 *
 * @author plaka
 * This class represents test. A test is composed of object implementing Question interface.
 */
public class Test implements Serializable {
    private static final long serialVersionUID = -1291138476285029378L;
    private String name;
    private ArrayList<Question> questions;
    private double totalMaxPoints;
    /**
     * Constructor.
     * @param name How the user has decided to name this test.
     */
    public Test(String name) {
        questions = new ArrayList<>();
        this.name = name;
        totalMaxPoints = 0;
    }
    
    /**
     * Adds the question to the end of the test.
     * @param q question to add
     */
    public void addQuestion(Question q) {
        questions.add(q); 
        totalMaxPoints += q.getPoints();
   }
    
    /**
     * Removed the question from the test.
     * @param q question to remove
     */
    public void removeQuestion(Question q) {
        questions.remove(q);
        totalMaxPoints -= q.getPoints();
    }
    /**
     * Removes question at the index provided.
     * @param i index
     */
    public void removeQuestionFromIndex(int i) {
        totalMaxPoints -= questions.get(i).getPoints();
        questions.remove(i);
    }
    /**
     * After question has been edited, changes the question in the test.
     * @param  oldQ old question
     * @param newQ new question
     */
    public void changeQuestion(Question oldQ, Question newQ) {
        totalMaxPoints -= oldQ.getPoints();
        totalMaxPoints += newQ.getPoints();
        int index = questions.indexOf(oldQ);
        questions.set(index, newQ);
    }
    
    /**
     * Give the name of the test.
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Changes the name of the test.
     * @param name What will the new name be.
     */
    public void changeName(String name) {
        this.name = name;
    }
    
    /**
     * Gives list of the question in the test.
     * @return questions
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }
    
    /**
     * Calculates the sum of points received in all of the questions in the test.
     * @param stage Stage where to show the result.
     * @throws IOException 
     */
     public void evaluate(Stage stage) throws IOException {
         Debugger.println("Vyhodnocujem...");
         double totalPoints = 0;
         for (Question q : questions) {
             totalPoints += q.getScore();
         }
         Debugger.println("Total points: " + totalPoints);
         FXMLLoader loader = new FXMLLoader();
         Parent root = loader.load(getClass().getResource("/resources/FXMLResult.fxml").openStream());        
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
         FXMLResultController.controller.setPointsLabel(totalPoints,totalMaxPoints);
     }
}

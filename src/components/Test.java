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
 */
public class Test implements Serializable {
    private String name;
    private ArrayList<Question> questions;
    
    public Test(String name) {
        questions = new ArrayList<>();
        this.name = name;
    }
    
    public void addQuestion(Question q) {
        questions.add(q); 
   }
    public void removeQuestion(Question q) {
        questions.remove(q);
    }
    public void removeQuestionFromIndex(int i) {
        questions.remove(i);
    }
    public void changeQuestion(Question oldQ, Question newQ) {
        int index = questions.indexOf(oldQ);
        questions.set(index, newQ);
    }
    
    public String getName() {
        return name;
    }
    
    public void changeName(String name) {
        this.name = name;
    }
    
    public ArrayList<Question> getQuestions() {
        return questions;
    }
    
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
         FXMLResultController.controller.setPointsLabel(totalPoints);
     }
}

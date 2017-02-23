/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.components;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import testy.Debugger;
import testy.controllers.FXMLResultController;

/**
 *
 * @author plaka
 */
public class Test {
    private String name;
    private ArrayList<Question> questions;
    
    public Test(String name) {
        questions = new ArrayList<>();
        this.name = name;
    }
    
    public void addQuestion(Question q) {
        questions.add(q); 
   }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<Question> getQuestions() {
        return questions;
    }
    
     public void evaluate(Stage stage) throws IOException {
         Debugger.println("Vyhodnocujem...");
         double totalPoints = 0;
         for (Question q : questions) {
             totalPoints += q.getPoints();
         }
         Debugger.println("Total points: " + totalPoints);
         FXMLLoader loader = new FXMLLoader();
         Parent root = loader.load(getClass().getResource("/resources/FXMLResult.fxml").openStream());        
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
         //FXMLResultController ctrl = (FXMLResultController) loader.getController();
         //ctrl.setPointsLabel(totalPoints); 
         FXMLResultController.controller.setPointsLabel(totalPoints);
     }
}

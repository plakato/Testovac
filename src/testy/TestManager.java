/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import testy.Debugger;
import testy.components.Question;
import testy.components.Test;

/**
 *
 * @author plaka
 */
public class TestManager {
     public static void displayTest(Test test, Stage stage) {
        System.out.println(test.getName() + " - is displayed.");
        TabPane tabPane = new TabPane();
        int counter = 1;
        for (Question q : test.getQuestions()) {
           Label instruction = new Label(q.question);
           Pane choices = q.getPaneOfChoices();
           VBox vbox = new VBox(instruction, choices);
           Tab tab = new Tab("Otázka " + Integer.toString(counter), vbox);
           tabPane.getTabs().add(tab);
           counter++;
        }
        
        Button finish = new Button("Ukončiť test!");
        finish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               try {
                   test.evaluate(stage);
               }  catch (IOException e) {
                   Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Ojoj, vyskytol sa problém. Aplikácia sa musí ukončiť.");
                    alert.showAndWait();
                    System.exit(0); 
               }    
            }      
        });
        finish.setStyle("-fx-font-size:20;");
        VBox outerVBox = new VBox(tabPane, finish);
        Scene scene = new Scene(outerVBox);
        stage.setScene(scene);
        stage.show();
    }
     
    public static void editTest(Test t, Stage stage) {
        Debugger.println("Editujem " + t.getName() + "...");
    }
    
    public static void displayCreateNew(Stage stage) {
        try {
            Parent pane = FXMLLoader.load(TestManager.class.getResource("/resources/FXMLNewTest.fxml"));
            stage.setScene(new Scene(pane));
            stage.show();
        }
        catch (IOException e) {
            ErrorInformer.exitApp();
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.controllers;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import testy.components.Multichoice;
import testy.components.Question;
import testy.components.Singlechoice;
import testy.components.Test;
import testy.components.TestSet;
import testy.components.WrittenAnswer;

/**
 *
 * @author plaka
 */
public class Loader {
    public static TestSet LoadTests() {
        TestSet tests;

        ArrayList<String> answers = new ArrayList<>();
        answers.add("Lukáš");
        answers.add("Paťka");
        answers.add("Kačka");
        Question q1 = new Singlechoice("Ako sa voláš?", answers, "Paťka", 1);
        ArrayList<Boolean> correct = new ArrayList<>();
        correct.add(true);
        correct.add(false);
        correct.add(true);
        Question q2 = new Multichoice("Kto sú tvoji super kamoši?",answers,correct, 1);
        ArrayList<String> wanswers = new ArrayList<>();
        wanswers.add("mff");
        wanswers.add("matfyz");
        wanswers.add("karlovka");
        Question q3 = new WrittenAnswer("Kde študuješ?", wanswers, 1);
        
        Test t1 = new Test("Jednootazkovy");
        t1.addQuestion(q1);
        Test t2 = new Test("Trojotazkovy");
        t2.addQuestion(q1);
        t2.addQuestion(q2);
        t2.addQuestion(q3);
        tests = new TestSet();
        tests.addTest(t1);
        tests.addTest(t2);
        return tests;
    }
    
   public static Pane givePaneForTestSet(TestSet tests, Stage stage) {
       VBox pane = new VBox();
        pane.setPadding(new Insets(10,10,10,10));
        pane.setSpacing(10);
        for (Test t : tests.tests) {
            Label label = new Label(t.getName());
            label.setStyle("-fx-font-size:30;");
            label.setPadding(new Insets(10,10,10,10));
            Button start = new Button("Začať test");
            start.setStyle("-fx-font-size:20;");
            start.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    TestManager.displayTest(t, stage);
                }
            });     
            HBox hbox = new HBox(start, label);
            pane.getChildren().add(hbox);          
        }
        return pane;
   }
    
   
   
}

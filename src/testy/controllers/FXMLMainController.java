/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.controllers;

import java.awt.Font;
import javafx.geometry.Insets;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import testy.components.Test;
import testy.components.TestSet;
import testy.controllers.Loader;
import testy.controllers.TestManager;

/**
 *
 * @author plaka
 */
public class FXMLMainController implements Initializable {
     
    @FXML
    private void handleButtonActionTeacher(ActionEvent event) throws IOException {
        System.out.println("You clicked teacher!");  
        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("/resources/FXMLSignIn.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleButtonActionStudent(ActionEvent event) throws IOException {
        System.out.println("You clicked student!");    
        Node source =(Node) event.getSource();
        Stage stage =(Stage) source.getScene().getWindow();
        
        //We dynamically create next layout with the list of all tests available
        TestSet tests = Loader.LoadTests();
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
        Button back = new Button("Späť");
        back.setStyle("-fx-font-size:20;");
        back.setOnAction(this::handleActionBackToTestList);
        pane.getChildren().add(back);
        Scene scene = new Scene(new ScrollPane(pane));
        
        stage.setScene(scene);
        stage.show();
    }
    
    private void handleActionBackToTestList(ActionEvent event) {
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/resources/FXMLMain.fxml"));
        } catch (IOException e) {
            //Never should happen
            //TODO:Inform the user of the error
            System.exit(0);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

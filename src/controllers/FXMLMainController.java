/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Debugger;
import main.ErrorInformer;
import components.Test;
import components.TestSet;
import main.Loader;
import main.TestManager;

/**
 *
 * @author plaka
 */
public class FXMLMainController implements Initializable {
     
    @FXML
    private void handleButtonActionTeacher(ActionEvent event) throws IOException {
        Debugger.println("You clicked teacher!");  
        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("/resources/FXMLSignIn.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleButtonActionStudent(ActionEvent event) throws IOException {
        Debugger.println("You clicked student!");    
        Node source =(Node) event.getSource();
        Stage stage =(Stage) source.getScene().getWindow();
        
        //We dynamically create next layout with the list of all tests available
        TestSet tests = Loader.LoadTests();
        Pane pane = Loader.givePaneForTestSet(tests, stage);
        Button back = new Button("Späť");
        back.setStyle("-fx-font-size:20;");
        back.setOnAction(this::handleActionBackToStart);
        pane.getChildren().add(back);
        Scene scene = new Scene(new ScrollPane(pane)); 
        stage.setScene(scene);
        stage.show();
    }
    
    private void handleActionBackToStart(ActionEvent event) {
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/resources/FXMLMain.fxml"));
        } catch (IOException e) {
            //Never should happen
            ErrorInformer.exitApp();
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

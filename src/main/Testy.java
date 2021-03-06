/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author plaka
 * Main class that starts the application.
 */
public class Testy extends Application {
    
    /**
     * Shows main screen. Sets the basic information and dimensions.
     * @param stage Stage on which the application runs.
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/FXMLMain.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Testovač");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(800);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

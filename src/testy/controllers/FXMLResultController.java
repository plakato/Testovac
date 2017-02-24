/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.controllers;

import testy.Loader;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.plaf.basic.BasicMenuUI;
import testy.Debugger;
import testy.ErrorInformer;
import testy.components.TestSet;

/**
 * FXML Controller class
 *
 * @author plaka
 */
public class FXMLResultController implements Initializable {

    public static FXMLResultController controller = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controller = this;
    }    
    
    @FXML public Label lPoints;
    
    
    public void setPointsLabel(double points) {
        if (points == (long)points) {
            lPoints.setText(String.format("%d",(long) points));
        }
        else {
            lPoints.setText(String.format("%s", points));
        }
        
    }
    
    @FXML
    private void closeApp() {
        System.exit(0);
    }
    
    @FXML
    private void showTests(ActionEvent event) {
        TestSet tests = Loader.LoadTests();
        Node source =  (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Pane pane = Loader.givePaneForTestSet(tests, stage);
        Scene scene = new Scene(new ScrollPane(pane));
        
        Button back = new Button("Späť");
        back.setStyle("-fx-font-size:20;");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/resources/FXMLMain.fxml"));
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    ErrorInformer.exitApp();
                }
            }
        }) ;
        pane.getChildren().add(back);
        stage.setScene(scene);
        stage.show();
    }
}

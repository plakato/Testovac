/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import main.Loader;
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
import main.Debugger;
import main.ErrorInformer;
import components.TestSet;

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
    
    
    public void setPointsLabel(double points, double max) {
        String result = "";
        if (points == (long)points) {
            result = String.format("%d",(long) points);
        }
        else {
            result = String.format("%s", points);
        }
        result += "/";
        
        if (max == (long)max) {
            result += String.format("%d",(long) max);
        }
        else {
            result += String.format("%s", max);
        }
        lPoints.setText(result);
        
    }
    
    @FXML
    private void closeApp() {
        System.exit(0);
    }
    
    @FXML
    private void showTests(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/FXMLMain.fxml"));
            loader.load();
            FXMLMainController ctrl = loader.getController();
            ctrl.displayTests((Stage)lPoints.getScene().getWindow());
        } catch (IOException e) {
            ErrorInformer.exitApp();
        }
        
    }
}

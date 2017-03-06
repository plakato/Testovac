/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import main.TestManager;
import main.Loader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import main.Debugger;
import main.ErrorInformer;
import components.Test;
import components.TestSet;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author plaka
 */
public class FXMLSignInController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleActionBack(ActionEvent event) {
        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/FXMLMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            ErrorInformer.exitApp();
        }
        
    }
    
    @FXML
    private PasswordField pass;
    @FXML
    private Label warning;
    
    @FXML
    private void verifyPasswordAndLoadTests(ActionEvent event) {
        if (!pass.getText().equals("heslo")) {
            warning.setText("To nebolo správne heslo!");
            warning.setTextFill(Color.RED);
            pass.clear();
        }
        else {
            Stage stage = (Stage)pass.getScene().getWindow();
            TeacherAllTestsViewController ctrl = new TeacherAllTestsViewController(); 
            ctrl.loadAndShowAllTests(stage);
        }
    }
    
    
    
}

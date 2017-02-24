/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import testy.Loader;
import testy.components.Test;

/**
 * FXML Controller class
 *
 * @author plaka
 */
public class FXMLNewTestController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void addMultichoiceQuestion() {
        Stage stage = new Stage();
    }
    
    @FXML
    private void addSinglechoiceQuestion() {
        
    }
    
    @FXML
    private void addWrittenAnswerQuestion() {
        
    }
    
    @FXML
    private TextField name;
    @FXML
    private Label nameWarning;
    
    @FXML
    private void createThisTest() {
        if (name.getText().equals("")) {
            nameWarning.setText("Každý test musí mať nejaký názov!");
            nameWarning.setTextFill(Paint.valueOf("RED"));
            return;
        }
        if (testWithSameName(name.getText())) {
            nameWarning.setText("Test s týmto názvom už existuje. Buďte originálny!");
            nameWarning.setTextFill(Paint.valueOf("RED"));
            return;
        }
        
    }
    
    private boolean testWithSameName(String name) {
        for (Test t : Loader.LoadTests().tests) {
            if (t.getName().equals(name)) {
                return true;
            }
        }
        return false;           
    }
}

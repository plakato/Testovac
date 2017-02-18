/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author plaka
 */
public class FXMLMainController implements Initializable {
     
    @FXML
    private void handleButtonActionTeacher(ActionEvent event) {
        System.out.println("You clicked teacher!");       
    }
    
    @FXML
    private void handleButtonActionStudent(ActionEvent event) {
        System.out.println("You clicked student!");       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

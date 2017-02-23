/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import testy.Debugger;
import testy.components.Test;
import testy.components.TestSet;

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
    private void handleActionBack(ActionEvent event) throws IOException {
        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        
        Parent root = FXMLLoader.load(getClass().getResource("/resources/FXMLMain.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private PasswordField pass;
    @FXML
    private Label warning;
    
    @FXML
    private void verifyPassword(ActionEvent event) {
        pass.clear();
        if (pass.getText() == "heslo") {
            Node source = (Node)event.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            TestSet tests = Loader.LoadTests();
            Parent testPane = getPaneWithTests(tests ,stage);
            VBox vb = new VBox();
            Button back = new Button("Späť");
            //add back-action and create new test button
            back.setOnAction(e -> Loader.loadStartScreen(stage));
            
        }
        else {
            warning.setText("To nebolo správne heslo!");
            warning.setTextFill(Paint.valueOf("RED"));
        }
    }
    
    private Parent getPaneWithTests(TestSet tests,Stage stage) {
        GridPane pane = new GridPane();
        int counter = 1;
        for (Test test : tests.tests) {
            Label name = new Label(test.getName());
            Button edit = new Button("Editovať");
            edit.setOnAction(e -> editTest(test,stage));
            pane.add(name, 1, counter);
            pane.add(edit, 2, counter);
            counter++;
        }
        return pane;
    }
    
    private void editTest(Test t, Stage stage) {
        Debugger.println("Editujem " + t.getName() + "...");
    }
}

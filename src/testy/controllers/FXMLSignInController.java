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
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ojoj, vyskytol sa problém. Aplikácia sa musí ukončiť.");
            alert.showAndWait();
            System.exit(0); 
        }
        
    }
    
    @FXML
    private PasswordField pass;
    @FXML
    private Label warning;
    
    @FXML
    private void verifyPassword(ActionEvent event) {
        if (!pass.getText().equals("heslo")) {
            warning.setText("To nebolo správne heslo!");
            warning.setTextFill(Paint.valueOf("RED"));
            pass.clear();
        }
        else {
            Node source = (Node)event.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            TestSet tests = Loader.LoadTests();
            Parent testPane = getPaneWithTests(tests ,stage);
            FlowPane footer = new FlowPane();
            Button back = new Button("Späť");
            //add back-action and create new test button
            back.setOnAction(e -> handleActionBack(e));
            Button createNew = new Button("Vytvoriť nový");

            footer.getChildren().addAll(back, createNew);
            createNew.setAlignment(Pos.CENTER_RIGHT);
            VBox pane = new VBox(new ScrollPane(testPane), footer);
            pane.setSpacing(10);
            pane.setPadding(new Insets(10,10,10,10));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
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
        pane.setPadding(new Insets(10,10,10,10));
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setBorder(Border.EMPTY);
        return pane;
    }
    
    private void editTest(Test t, Stage stage) {
        Debugger.println("Editujem " + t.getName() + "...");
    }
}

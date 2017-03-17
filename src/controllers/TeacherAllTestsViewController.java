/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import components.Test;
import components.TestSet;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Debugger;
import main.ErrorInformer;
import main.Loader;
import main.TestManager;

/**
 *
 * @author plaka
 */
public class TeacherAllTestsViewController {

    private GridPane testPane;
    public void loadAndShowAllTests(Stage stage) {
    TestSet tests = Loader.LoadTests();
        Parent testPane = getPaneWithTests(tests ,stage);

        Button back = new Button("Späť");
        back.setOnAction(e -> handleActionBack(e));
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button createNew = new Button("Vytvoriť nový");
        createNew.setOnAction(e -> TestManager.displayCreateNew(stage));

        HBox footer = new HBox(back, spacer, createNew);
        footer.setSpacing(10);

        VBox pane = new VBox(new ScrollPane(testPane), footer);
        pane.setSpacing(10);
        pane.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
   
    private Parent getPaneWithTests(TestSet tests,Stage stage) {
        GridPane pane = new GridPane();
        testPane = pane;
        int row = 1;
        for (Test test : tests.tests) {
            Label name = new Label(test.getName());
            Button edit = new Button("Editovať");
            Button delete = new Button("Odstrániť");
            edit.setOnAction(e -> editTest(test, stage));
            delete.setOnAction(e -> deleteTest(test, e));
            pane.add(name, 0, row);
            pane.add(edit, 1, row);
            pane.add(delete, 2, row);
            row++;
        }
        pane.setPadding(new Insets(10,10,10,10));
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setBorder(Border.EMPTY);
        return pane;
    }
    
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
    private void deleteTest(Test test, ActionEvent e) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Potvrdenie");
    alert.setHeaderText("Odstrániť");
    alert.setContentText("Naozaj chcete tento test odstrániť?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
        //remove it from the window
        int row = testPane.getRowIndex((Node)e.getSource());
        Set<Node> toRemove = new HashSet<>();
        for (Node node : testPane.getChildren()) {
            int thisRow = testPane.getRowIndex(node);
            Debugger.println("ThisRow index of " + node.getClass() + " is " + thisRow);
            if (thisRow > row) {
                testPane.setRowIndex(node, thisRow-1);
                Debugger.println("thisRow of " + node.getClass() + " was set to"+ (thisRow-1));
            }
            if (thisRow == row) {
                toRemove.add(node);
                Debugger.println("object " + node.getClass() + " is removed.");
            }
        }
        //testPane.getRowConstraints().remove(testPane.getRowConstraints().size() -1);
        testPane.getChildren().removeAll(toRemove);
        Stage stage = (Stage)testPane.getScene().getWindow();
        stage.sizeToScene();
        //remove it from memory
        File file = new File("src/tests/"+test.getName()+".ser");
        file.delete(); 
    } else {
    }        
           
    }
    private void editTest(Test t, Stage stage) {
        Debugger.println("Editujem " + t.getName() + "...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/FXMLNewTest.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            ErrorInformer.exitApp();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        FXMLNewTestController ctrl = loader.getController();
        ctrl.setThisTest(t);
    }
}

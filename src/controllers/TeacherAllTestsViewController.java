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
import javafx.geometry.Pos;
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
 * Manages actions taken on the screen with available tests.
 * (teacher mode only)
 */
public class TeacherAllTestsViewController {

    private GridPane testPane;
    /**
     * Loads all tests available and shows them on the screen with options to 
     * remove or edit them. Also shows options to download questions from the server
     * and upload to the server.
     * @param stage 
     */
    public void loadAndShowAllTests(Stage stage) {
    TestSet tests = Loader.LoadTests();
        Parent testPane = getPaneWithTests(tests ,stage);

        Button back = new Button("Späť");
        back.setStyle("-fx-font-size: 20");
        back.setOnAction(e -> handleActionBack(e));
        Button createNew = new Button("Vytvoriť nový");
        createNew.setStyle("-fx-font-size: 20");
        createNew.setOnAction(e -> TestManager.displayCreateNew(stage));
        Button download = new Button("Stiahnuť zo servru");
        download.setStyle("-fx-font-size: 20");
        download.setOnAction(e -> {TestManager.syncTestsWithServer(); loadAndShowAllTests(stage);});
        Button upload = new Button("Uložiť na server");
        upload.setStyle("-fx-font-size: 20");
        upload.setOnAction(e -> {TestManager.syncServerWithTest(); loadAndShowAllTests(stage);});

        HBox footer = new HBox(back, createNew, download, upload);
        footer.setSpacing(10);
        footer.setAlignment(Pos.BOTTOM_CENTER);

        VBox pane = new VBox(new ScrollPane(testPane), footer);
        pane.setSpacing(10);
        pane.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
   
    /**
     * Creates a layout with the available tests.
     * @param tests
     * @param stage
     * @return 
     */
    private Parent getPaneWithTests(TestSet tests,Stage stage) {
        GridPane pane = new GridPane();
        testPane = pane;
        int row = 1;
        for (Test test : tests.tests) {
            Label name = new Label(test.getName());
            name.setStyle("-fx-font-size: 20");
            Button edit = new Button("Editovať");
            edit.setStyle("-fx-font-size: 20");
            Button delete = new Button("Odstrániť");
            delete.setStyle("-fx-font-size: 20");
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
    
    /**
     * Returns to the main screen.
     * @param event 
     */
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
    /**
     * After the user clicked button to remove test, 
     * shows a window to confirm this action. 
     * If action confirmed - deletes the test.
     * If action not confirmed - does nothing.
     * @param test Test to be deleted.
     * @param e 
     */
    private void deleteTest(Test test, ActionEvent e) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Potvrdenie");
        alert.setHeaderText("Odstrániť");
        alert.setContentText("Naozaj chcete tento test odstrániť?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //remove it from memory
            File file = new File("src/tests/"+test.getName()+".ser");
            boolean success  = file.delete(); 
            Debugger.println("File " + file.getName() + " deleted: " + success);
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            loadAndShowAllTests(stage);
        } else {
        }                  
    }
    /**
     * If the user clicked edit test, shows the screen with the list
     * of its questions and options to edit.
     * @param t test to edit
     * @param stage 
     */
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

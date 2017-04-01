/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import components.Multichoice;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import main.ErrorInformer;
import main.Loader;
import components.Question;
import components.Singlechoice;
import components.Test;
import components.WrittenAnswer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import main.Debugger;

/**
 * FXML Controller class
 *
 * @author plaka
 * This class manages actions taken when the user creates/edits new test.
 */
public class FXMLNewTestController implements Initializable {

    private Test test;
    private static FXMLNewTestController controller;
    private String previousTestName = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        test = new Test("");
        controller = this;
    }   
    
    @FXML
    private TextField name;    
    @FXML
    private Label nameWarning;
    @FXML
    private GridPane questionsGrid; 
    
    /**
     * Gives an instance of the controller that initialized this class.
     * @return controller instance
     */
    public static FXMLNewTestController getController() {
        return controller;
    }  
    
    /**
     * When editing a test, this fills the information from the
     * previous version to the form.
     * @param test instance of the test
     */
    public void setThisTest(Test test) {
        previousTestName = test.getName();
        this.test.changeName(test.getName());
        name.setText(test.getName());
        for (Question q : test.getQuestions()) {
            showQuestion(q);
        }
    }
    /**
     * Opens a window where user can create a new multichoice question
     */
    @FXML
    private void addMultichoiceQuestion() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/FXMLNewMultichoice.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Nová otázka s viacerými možnosťami");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            ErrorInformer.exitApp();
        }
    }
    /**
     * Opens a window where user can create a new singlechoice question
     */
    @FXML
    private void addSinglechoiceQuestion() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/FXMLNewSinglechoice.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Nová otázka s jednou možnosťou");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException ex) {
            ErrorInformer.exitApp();
        }        
    }
    /**
     * Opens a window where user can create a new written answer question
     */
    @FXML
    private void addWrittenAnswerQuestion() {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/FXMLNewWrittenAnswer.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Nová otázka s písomnou odpoveďou");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException ex) {
            ErrorInformer.exitApp();
        }      
    }  
    
    /**
     * Creates an instance of test from the form that the user edited.
     * Saves it to tests folder.
     */
    @FXML
    private void createThisTest() {
        if (name.getText().equals("")) {
            nameWarning.setText("Každý test musí mať nejaký názov!");
            nameWarning.setTextFill(Color.RED);
            return;
        }
        test.changeName(name.getText());
        Debugger.println(test.getName());
        for (Question q : test.getQuestions()) {
            Debugger.println(q.question);
        }
        //delete the previous version of the test
        if (previousTestName != null) {
          File file = new File("src/tests/"+previousTestName+".ser");
          file.delete();  
        }   
        if (testWithSameName(name.getText())) {
          nameWarning.setText("Test s týmto názvom už existuje. Buďte originálny!");
          nameWarning.setTextFill(Color.RED);
          return;
        }
        //save this test
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/tests/"+test.getName()+".ser"))) {
            out.writeObject(test);
            out.flush();
            out.close();
        } catch (IOException ex) {
            ErrorInformer.failedInSavingTest();
            ex.printStackTrace();
            return;
        }
        //show view with all tests
        Stage stage = (Stage)name.getScene().getWindow();
        TeacherAllTestsViewController ctrl  = new TeacherAllTestsViewController();
        ctrl.loadAndShowAllTests(stage);
    }
    @FXML SplitMenuButton addQuestionMenuButton;
    /**
     * Shows the right-sided menu when the user clicks the button
     * instead of the arrow.
     * @param e 
     */
    @FXML
    private void showMenu(ActionEvent e) {
        addQuestionMenuButton.show();
    }
    /**
     * Checks if some test with the same name doesn't
     * already exist.
     * @param name name of the test
     * @return true if test with the same name exists, false otherwise
     */
    private boolean testWithSameName(String name) {
        for (Test t : Loader.LoadTests().tests) {
            if (t.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;           
    }
    
    /**
     * When the test is only being edited, shows the question
     * with the button options to edit.
     * @param q question to show
     */
    public void showQuestion(Question q) {
        Label text = new Label(q.question);
        text.setStyle("-fx-font-size: 20");
        Button edit = new Button("Editovať");
        edit.setStyle("-fx-font-size: 20");
        Button remove = new Button("Odstrániť");
        remove.setStyle("-fx-font-size: 20");
        edit.setOnAction(e->editQuestion(e,q));
        remove.setOnAction(e->removeQuestion(e));
        int rowNumber = questionsGrid.getRowConstraints().size();
        questionsGrid.getRowConstraints().add(new RowConstraints(50));
        questionsGrid.add(text,0,rowNumber);
        questionsGrid.add(edit,1,rowNumber);
        questionsGrid.add(remove,2, rowNumber);
        questionsGrid.setRowIndex(text, rowNumber);
        questionsGrid.setRowIndex(edit, rowNumber);
        questionsGrid.setRowIndex(remove, rowNumber);
        questionsGrid.setColumnIndex(text, 0);
        questionsGrid.setColumnIndex(edit, 1);
        questionsGrid.setColumnIndex(remove, 2);
        test.addQuestion(q);
    }
    
    /**
     * When user chooses to edit the question, this opens 
     * editing window.
     * @param e
     * @param q question to edit
     */
    private void editQuestion(ActionEvent e, Question q) {
        Stage stage = new Stage();
        Parent root = null;
        Label labelToSet = null;
        Button buttonToEdit = null;
        
        int row = questionsGrid.getRowIndex((Node)e.getSource());
        
        FXMLLoader loader = new FXMLLoader();
        try {
            if (q instanceof Multichoice){
                loader = new FXMLLoader(getClass().getResource("/resources/FXMLNewMultichoice.fxml"));
                root = loader.load();
                ((IFXMLNewQuestion)loader.getController()).setQuestion(q, row);
                stage.setTitle("Editácia otázky s viacerými možnosťami");
            }
            else if (q instanceof Singlechoice) {
                loader = new FXMLLoader(getClass().getResource("/resources/FXMLNewSinglechoice.fxml"));
                root = loader.load();
                ((IFXMLNewQuestion)loader.getController()).setQuestion(q, row);
                stage.setTitle("Editácia otázky s jednou možnosťou");
            }
            else if (q instanceof WrittenAnswer) {
                loader = new FXMLLoader(getClass().getResource("/resources/FXMLNewWrittenAnswer.fxml"));
                root = loader.load();
                ((IFXMLNewQuestion)loader.getController()).setQuestion(q, row);
                stage.setTitle("Editácia otázky s písomnou odpoveďou");
            }
            
        } catch (IOException ex) {
            ErrorInformer.exitApp();
        }
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    
    /**
     * When user click the remove button, removes the
     * question belonging to that button.
     * @param e 
     */
    private void removeQuestion(ActionEvent e) {
        int removedRow = questionsGrid.getRowIndex((Node)e.getSource());
        Set<Node> toRemove = new HashSet<>();
        for (Node node : questionsGrid.getChildren()) {
            int nodeRow = questionsGrid.getRowIndex(node);
            if (nodeRow > removedRow) {
                questionsGrid.setRowIndex(node,nodeRow-1);
            }
            if (nodeRow == removedRow) {
                toRemove.add(node);
            }
        }
        for (Node node : toRemove) {
            questionsGrid.getChildren().remove(node);
        }
        test.removeQuestionFromIndex(removedRow);
        questionsGrid.getRowConstraints().remove(questionsGrid.getRowConstraints().size()-1);
    }
    
    /**
     * After user has edited the question, changes the old question to new one.
     * @param oldQ old question
     * @param newQ new question
     * @param row row on which this question is in the test
     */
    public void changeQuestion(Question oldQ, Question newQ, int row) {
        for (Node n : questionsGrid.getChildren()) {
            if (n instanceof Label && questionsGrid.getRowIndex(n) == row) {
                ((Label)n).setText(newQ.question);
            }
            if (n instanceof Button && questionsGrid.getRowIndex(n) == row && questionsGrid.getColumnIndex(n) == 1) {
                ((Button)n).setOnAction(e->editQuestion(e, newQ));
            }
        }
        test.changeQuestion(oldQ, newQ);
    }
}

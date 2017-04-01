/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import components.Question;
import components.Singlechoice;
import components.WrittenAnswer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Debugger;

/**
 * FXML Controller class
 *
 * @author plaka
 * Controls actions on the screen where user creates new written answer question.
 */
public class FXMLNewWrittenAnswerController implements Initializable, IFXMLNewQuestion {

    /**
     * Initializes the controller class. Sets row index for the first empty answer.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    for (Node node : optionsGridPane.getChildren()) {
           if (node instanceof TextField) {
               optionsGridPane.setRowIndex(node, 0);
           }
       }
    }    
    @FXML
    private GridPane optionsGridPane;   
    @FXML
    private HBox addRemoveButtons;
    @FXML
    private TextField points;
    @FXML
    private TextArea questionText;
    @FXML
    private Label warning;
    @FXML
    private Button addQuestionButton;
    
    /**
     * Adds an empty choice to the list of possible correct answers.
     * @param event 
     */
    @FXML
    private void addChoice(ActionEvent event) {
        TextField field = new TextField();
        field.setStyle("-fx-font-size: 20");
        field.setPrefWidth(530);
        field.styleProperty().set("-fx-font-size: 20");
        optionsGridPane.getRowConstraints().add(new RowConstraints(50));
        int buttonsIndex = optionsGridPane.getRowIndex(addRemoveButtons);
        optionsGridPane.setRowIndex(addRemoveButtons, buttonsIndex+1);
        optionsGridPane.setRowIndex(field,optionsGridPane.getRowConstraints().size()-2);        
        optionsGridPane.add(field,0,optionsGridPane.getRowConstraints().size()-2);

        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.sizeToScene();
        stage.show();
        
    }
    
    /**
     * Removes the last choice from the list of possible answers.
     */
    @FXML
    private void removeChoice() {
        if (optionsGridPane.getRowConstraints().size() < 2) {
            return;
        }
        int buttonsIndex = optionsGridPane.getRowIndex(addRemoveButtons);
        Set<Node> deleteNodes = new HashSet();
        for (Node node : optionsGridPane.getChildren()) {
            if (optionsGridPane.getRowIndex(node) == buttonsIndex-1) {
                deleteNodes.add(node);
            }
        }
        optionsGridPane.getChildren().removeAll(deleteNodes);
        optionsGridPane.getRowConstraints().remove(buttonsIndex);
        optionsGridPane.setRowIndex(addRemoveButtons, buttonsIndex-1);
        Stage stage = (Stage)optionsGridPane.getScene().getWindow();
        stage.sizeToScene();
    }
    
    /**
     * Closes the editing window without saving the test.
     */
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) optionsGridPane.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Saves the question edited by user. Instance is created
     * from the data entered in the form.
     */
    @FXML
    private void addQuestion() {
        if (!correctEntries())
            return;
        FXMLNewTestController ctrl = FXMLNewTestController.getController();
        ctrl.showQuestion(createQuestionFromForm());
        Stage stage = (Stage)questionText.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Checks whether all data entered is valid.
     * @return true if all valid, false otherwise
     */
    private boolean correctEntries() {
        if (questionText.getText().equals("")) {
            warning.setText("Otázka sa niečo musí pýtať!");
            warning.setTextFill(Color.RED);
            return false;
        }  
        int pointsValue;
        try {
            Double.parseDouble(points.getText());
        } catch (NumberFormatException e) {
            warning.setText("Zadajte validnú hodnotu počtu bodov.");
            warning.setTextFill(Color.RED);
            return false;
        }
        for (Node node : optionsGridPane.getChildren()) {
            if (node instanceof TextField && ((TextField)node).getText().equals("")) {
            warning.setText("Prázdna možnosť nemôže byť správna, to by bolo priľahké.");
            warning.setTextFill(Color.RED);
            return false;
            }
        }
        return true;
    }
    
    /**
     * Creates an instance of WrittenAnswer from the form.
     * @return instance of WirttenAnswer
     */
    private Question createQuestionFromForm() {
        int choicesCount = optionsGridPane.getRowConstraints().size()-1;
        Debugger.println("choices count calculated is " + choicesCount);
        List<String> choices = new ArrayList<>(choicesCount);
        Collections.fill(choices, null);
        
        for (Node node : optionsGridPane.getChildren()) {
            if (node instanceof TextField) {
                Debugger.println("text at "+optionsGridPane.getRowIndex(node));
                choices.add(optionsGridPane.getRowIndex(node),((TextField) node).getText());
            }
        }

        // Points are valid - we checked in addQuestion()
        Question q = new WrittenAnswer(questionText.getText(), choices, Double.parseDouble(points.getText()));
        return q;
    }

    /**
     * Sets the data in the form from the instance.
     * @param q instance of WrittenAnswer
     * @param fromRow question's number in the test
     */
    @Override
    public void setQuestion(Question q, int fromRow) {
        //we remove the default empty option
        optionsGridPane.getRowConstraints().remove(0);
        Set<Node> toRemove = new HashSet<>();
        for (Node n : optionsGridPane.getChildren()) {
            if (n instanceof TextField) {
                toRemove.add(n);
            }
        }
        optionsGridPane.getChildren().removeAll(toRemove);
        questionText.setText(q.question);
        WrittenAnswer wa = (WrittenAnswer)q;
        double waPoints = wa.getPoints();  
        
        if(waPoints == (long) waPoints)
            points.setText(String.format("%d",(long)waPoints));
        else
            points.setText(String.format("%s",waPoints));
        
        for (String s : wa.getChoices()) {
            TextField field = new TextField();
            field.styleProperty().set("-fx-font-size: 20");
            field.setPrefWidth(530);
            field.setText(s);
            optionsGridPane.getRowConstraints().add(new RowConstraints(50));
            optionsGridPane.setRowIndex(field,optionsGridPane.getRowConstraints().size()-2);
            optionsGridPane.add(field,0,optionsGridPane.getRowConstraints().size()-2);
        }
        int buttonsIndex = optionsGridPane.getRowIndex(addRemoveButtons);
        optionsGridPane.setRowIndex(addRemoveButtons, wa.getChoices().size());
        addQuestionButton.setText("Uložiť zmeny");
        addQuestionButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if (!correctEntries())
                    return;
                Question newQuestion = createQuestionFromForm();
                Debugger.println("new question choices count is "+ ((WrittenAnswer)newQuestion).getChoices().size());
                Stage stage = (Stage)addQuestionButton.getScene().getWindow();
                stage.close();
                FXMLNewTestController ctrl = FXMLNewTestController.getController();
                ctrl.changeQuestion(q, newQuestion, fromRow);
            }
            
        });
    }    
    
}

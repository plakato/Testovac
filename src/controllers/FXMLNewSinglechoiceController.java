/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import components.Multichoice;
import components.Question;
import components.Singlechoice;
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
import javafx.scene.control.CheckBox;
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
 * Controls actions on the screen where user creates new singlechoice question.
 */
public class FXMLNewSinglechoiceController implements Initializable, IFXMLNewQuestion {

    /**
     * Initializes the controller class. Sets row indexes of the first radio button prepared.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       for (Node node : optionsGridPane.getChildren()) {
           if (node instanceof RadioButton || node instanceof TextField) {
               optionsGridPane.setRowIndex(node, 0);
           }
       }
    }    
    @FXML
    private GridPane optionsGridPane; 
    @FXML
    private ToggleGroup toggles;  
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
     * Adds new row with an emtpy choice.
     * @param event 
     */
    @FXML
    private void addChoice(ActionEvent event) {
        RadioButton radio = new RadioButton();
        radio.setToggleGroup(toggles);
        TextField field = new TextField();
        field.setStyle("-fx-font-size: 20");
        field.setPrefWidth(530);
        radio.styleProperty().set("-fx-font-size: 20");
        optionsGridPane.getRowConstraints().add(new RowConstraints(50));
        int buttonsIndex = optionsGridPane.getRowIndex(addRemoveButtons);
        optionsGridPane.setRowIndex(addRemoveButtons, buttonsIndex+1);
        optionsGridPane.setRowIndex(field,optionsGridPane.getRowConstraints().size()-2);
        optionsGridPane.setRowIndex(radio,optionsGridPane.getRowConstraints().size()-2);
        
        optionsGridPane.add(field,1,optionsGridPane.getRowConstraints().size()-2);
        optionsGridPane.add(radio,0,optionsGridPane.getRowConstraints().size()-2); 

        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.sizeToScene();
        stage.show();
        
    }
    
    /**
     * Removes last choice from the list.
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
     * Closes the window without saving the question.
     */
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) optionsGridPane.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Saves the question to the currently edited test.
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
     * Checks whether the user entered valid data.
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
        if (toggles.getSelectedToggle() == null) {
            warning.setText("Zvoľte nejakú odpoveď.");
            warning.setTextFill(Color.RED);
            return false;
        }
        return true;
    }
    
    /**
     * Creates an instance of singlechoice from the form with information from the user.
     * @return 
     */
    private Question createQuestionFromForm() {
        int choicesCount = optionsGridPane.getRowConstraints().size()-1;
        Debugger.println("choices count calculated is " + choicesCount);
        List<String> choices = new ArrayList<>(choicesCount);
        String correct = "";
        Collections.fill(choices, null);
        int selectedRow = -1;
        for (Node n : optionsGridPane.getChildren()) {
            if (n instanceof RadioButton) {
                if (((RadioButton)n).isSelected()) {
                    selectedRow = optionsGridPane.getRowIndex(n);
                }
            }
        }
        
        for (Node node : optionsGridPane.getChildren()) {
            if (node instanceof TextField) {
                Debugger.println("text at "+optionsGridPane.getRowIndex(node));
                choices.add(optionsGridPane.getRowIndex(node),((TextField) node).getText());
                if (optionsGridPane.getRowIndex(node) == selectedRow) {
                    correct = ((TextField)node).getText();
                }
            }
        }

        Debugger.println("correct is " + correct);
        // Points are valid - we checked in addQuestion()
        Question q = new Singlechoice(questionText.getText(), choices, correct, Double.parseDouble(points.getText()));
        return q;
    }

    /**
     * Fills the form with information from the given instance.
     * @param q instance of question
     * @param fromRow question's number in the test
     */
    @Override
    public void setQuestion(Question q, int fromRow) {
        //we remove the default empty option
        optionsGridPane.getRowConstraints().remove(0);
        Set<Node> toRemove = new HashSet<>();
        for (Node n : optionsGridPane.getChildren()) {
            if (n instanceof RadioButton || n instanceof TextField) {
                toRemove.add(n);
            }
        }
        optionsGridPane.getChildren().removeAll(toRemove);
        questionText.setText(q.question);
        Singlechoice sq = (Singlechoice)q;
        double sqPoints = sq.getPoints();  
        
        if(sqPoints == (long) sqPoints)
            points.setText(String.format("%d",(long)sqPoints));
        else
            points.setText(String.format("%s",sqPoints));
        
        for (String s : sq.getChoices()) {
            RadioButton radio = new RadioButton();
            radio.styleProperty().set("-fx-font-size: 20");
            radio.setToggleGroup(toggles);
            TextField field = new TextField();
            field.setStyle("-fx-font-size: 20");
            field.setPrefWidth(530);
            field.setText(s);
            if (sq.getCorrect().equals(s)) {
                Debugger.println("found selectd toggle");
                toggles.selectToggle(radio);
            }
            optionsGridPane.getRowConstraints().add(new RowConstraints(50));

            optionsGridPane.setRowIndex(field,optionsGridPane.getRowConstraints().size()-2);
            optionsGridPane.setRowIndex(radio,optionsGridPane.getRowConstraints().size()-2);

            optionsGridPane.add(field,1,optionsGridPane.getRowConstraints().size()-2);
            optionsGridPane.add(radio,0,optionsGridPane.getRowConstraints().size()-2); 

        }
        int buttonsIndex = optionsGridPane.getRowIndex(addRemoveButtons);
        optionsGridPane.setRowIndex(addRemoveButtons, sq.getChoices().size());
        addQuestionButton.setText("Uložiť zmeny");
        addQuestionButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if (!correctEntries())
                    return;
                Question newQuestion = createQuestionFromForm();
                Debugger.println("new question choices count is "+ ((Singlechoice)newQuestion).getChoices().size());
                Stage stage = (Stage)addQuestionButton.getScene().getWindow();
                stage.close();
                FXMLNewTestController ctrl = FXMLNewTestController.getController();
                ctrl.changeQuestion(q, newQuestion, fromRow);
            }
            
        });
    }
    
}

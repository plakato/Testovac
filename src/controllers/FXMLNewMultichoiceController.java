/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import components.Multichoice;
import components.Question;
import java.awt.Checkbox;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
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
 */
public class FXMLNewMultichoiceController implements Initializable,IFXMLNewQuestion {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    @FXML
    private GridPane optionsGridPane; 
    @FXML
    private HBox addRemoveButtons;
    @FXML
    private Button addChoiceButton;
    @FXML
    private TextArea questionText;
    @FXML
    private Label warning;
    @FXML
    private Button addQuestionButton;
    @FXML
    private TextField points;
    
    @FXML
    private void addChoice(ActionEvent event) {
        CheckBox checkbox = new CheckBox();
        checkbox.styleProperty().set("-fx-font-size: 20");
        TextField field = new TextField();
        field.setStyle("-fx-font-size: 20");
        field.setPrefWidth(530);
        optionsGridPane.getRowConstraints().add(new RowConstraints(50));
        int buttonsIndex = optionsGridPane.getRowIndex(addRemoveButtons);
        optionsGridPane.setRowIndex(addRemoveButtons, buttonsIndex+1);
        optionsGridPane.setRowIndex(field,optionsGridPane.getRowConstraints().size()-2);
        optionsGridPane.setRowIndex(checkbox,optionsGridPane.getRowConstraints().size()-2);
        
        optionsGridPane.add(field,1,optionsGridPane.getRowConstraints().size()-2);
        optionsGridPane.add(checkbox,0,optionsGridPane.getRowConstraints().size()-2); 

        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.sizeToScene();
        stage.show();
        
    }
    
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
    
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) optionsGridPane.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void addQuestion() {
        if (!correctEntries())
            return;
        FXMLNewTestController ctrl = FXMLNewTestController.getController();
        ctrl.showQuestion(createQuestionFromForm());
        Stage stage = (Stage)questionText.getScene().getWindow();
        stage.close();
    }
    
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
        return true;
    }

    
    private Question createQuestionFromForm() {
        int choicesCount = optionsGridPane.getRowConstraints().size()-1;
        Debugger.println("choices count calculated is " + choicesCount);
        List<String> choices = new ArrayList<>(choicesCount);
        List<Boolean> correct = new ArrayList<>(choicesCount);
        Collections.fill(choices, null);
        Collections.fill(correct, false);
        
        for (Node node : optionsGridPane.getChildren()) {
            if (node instanceof TextField) {
                Debugger.println("text at "+optionsGridPane.getRowIndex(node) );
                choices.add(optionsGridPane.getRowIndex(node),((TextField) node).getText());
            }
            if (node instanceof CheckBox) {
                Debugger.println("box at "+optionsGridPane.getRowIndex(node) );
                correct.add(optionsGridPane.getRowIndex(node),((CheckBox) node).isSelected());
            }
        }
        for (int i = 0; i < choicesCount; i++) {
            Debugger.println(choices.get(i) + " : " + correct.get(i));
        }
        Debugger.println(questionText.getText());
        Question q = new Multichoice(questionText.getText(), choices, correct, Double.parseDouble(points.getText()));
        return q;
    }

    @Override
    public void setQuestion(Question q, int fromRow) {
        //we remove the default empty option
        optionsGridPane.getRowConstraints().remove(0);
        Set<Node> toRemove = new HashSet<>();
        for (Node n : optionsGridPane.getChildren()) {
            if (n instanceof CheckBox || n instanceof TextField) {
                toRemove.add(n);
            }
        }
        optionsGridPane.getChildren().removeAll(toRemove);
        questionText.setText(q.question);
        Multichoice mq = (Multichoice)q;
        double mqPoints = mq.getPoints();  
        
        if(mqPoints == (long) mqPoints)
            points.setText(String.format("%d",(long)mqPoints));
        else
            points.setText(String.format("%s",mqPoints));
        Debugger.println("choices count is "+ mq.getChoices().size());
                Debugger.println("row contraints count is "+ optionsGridPane.getRowConstraints().size());

        for (String s : mq.getChoices()) {
            CheckBox checkbox = new CheckBox();
            TextField field = new TextField();
            field.setStyle("-fx-font-size: 20;");
            field.setPrefWidth(530);
            field.setText(s);
            checkbox.setSelected(mq.getCorrect().get(mq.getChoices().indexOf(s)));
            checkbox.styleProperty().set("-fx-font-size: 20");
            optionsGridPane.getRowConstraints().add(new RowConstraints(50));

            optionsGridPane.setRowIndex(field,optionsGridPane.getRowConstraints().size()-2);
            optionsGridPane.setRowIndex(checkbox,optionsGridPane.getRowConstraints().size()-2);

            optionsGridPane.add(field,1,optionsGridPane.getRowConstraints().size()-2);
            optionsGridPane.add(checkbox,0,optionsGridPane.getRowConstraints().size()-2); 

        }
        int buttonsIndex = optionsGridPane.getRowIndex(addRemoveButtons);
        optionsGridPane.setRowIndex(addRemoveButtons, mq.getChoices().size());
        addQuestionButton.setText("Uložiť zmeny");
        addQuestionButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if (!correctEntries())
                    return;
                Question newQuestion = createQuestionFromForm();
                Debugger.println("new question choices count is "+ ((Multichoice)newQuestion).getChoices().size());
                Stage stage = (Stage)addQuestionButton.getScene().getWindow();
                stage.close();
                FXMLNewTestController ctrl = FXMLNewTestController.getController();
                ctrl.changeQuestion(q, newQuestion, fromRow);
            }
            
        });
    }
    
}

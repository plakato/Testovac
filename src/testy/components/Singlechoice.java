/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.components;

import java.util.List;
import javafx.scene.layout.Pane;

/**
 *
 * @author plaka
 */
public class Singlechoice extends Question {
    private List<String> choices;
    private int correct;
    public Singlechoice(String question, List<String> choices, int correct) {
        this.question = question;
        this.choices = choices;
        this.correct = correct;
    }
    public List<String> getChoices() {
        return choices;
    }

    @Override
    public Pane getPaneOfChoices() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

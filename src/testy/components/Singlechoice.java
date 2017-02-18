/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.components;

import java.util.List;

/**
 *
 * @author plaka
 */
public class Singlechoice implements Question{
    private String question;
    private List<String> choices;
    private int correct;
    public Singlechoice(String question, List<String> choices, int correct) {
        this.question = question;
        this.choices = choices;
        this.correct = correct;
    }
}

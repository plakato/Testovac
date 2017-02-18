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
public class Multichoice implements Question {
    private String question;
    private List<String> choices;
    private List<Integer> correct;
    public Multichoice(String question, List<String> choices, List<Integer> correct) {
        this.question = question;
        this.choices = choices;
        this.correct = correct;
    }
}

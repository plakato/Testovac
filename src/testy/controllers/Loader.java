/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.controllers;
import java.util.ArrayList;
import testy.components.Multichoice;
import testy.components.Question;
import testy.components.Singlechoice;
import testy.components.Test;
import testy.components.TestSet;

/**
 *
 * @author plaka
 */
public class Loader {
    public static void LoadTests() {
        TestSet tests;

        ArrayList<String> answers = new ArrayList<>();
        answers.add("Lukáš");
        answers.add("Paťka");
        answers.add("Kačka");
        Question q1 = new Singlechoice("Ako sa voláš?", answers, 2);
        ArrayList<Integer> correct = new ArrayList<>();
        correct.add(1);
        correct.add(3);
        Question q2 = new Multichoice("Kto sú tvoji super kamoši?",answers,correct);
        
        Test t1 = new Test("Jednootazkovy");
        t1.addQuestion(q1);
        Test t2 = new Test("Dvojotazkovy");
        t2.addQuestion(q1);
        t2.addQuestion(q2);
        tests = new TestSet();
        tests.addTest(t1);
        tests.addTest(t2);

    }
}

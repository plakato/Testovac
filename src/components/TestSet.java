/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.util.ArrayList;

/**
 *
 * @author plaka
 * This class represents a set of all the tests.
 * 
 */
public class TestSet {
    public ArrayList<Test> tests;
    /**
     * Constructor.
     */
    public TestSet() {
        tests = new ArrayList<>();
    }
    /**
     * Adds test to the set.
     * @param test test to add
     */
    public void addTest(Test test) {
        tests.add(test);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.components;

import java.util.ArrayList;

/**
 *
 * @author plaka
 */
public class TestSet {
    public ArrayList<Test> tests;
    
    public TestSet() {
        tests = new ArrayList<>();
    }
    
    public void addTest(Test test) {
        tests.add(test);
    }
}

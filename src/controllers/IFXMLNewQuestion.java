/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import components.Question;
import javafx.scene.control.Label;

/**
 *
 * @author plaka
 * Interface that all new questions' editing windows
 * must implement.
 */
public interface IFXMLNewQuestion {
    public void setQuestion(Question q, int row);
}

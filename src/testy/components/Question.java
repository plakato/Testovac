/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testy.components;

import javafx.scene.layout.Pane;

/**
 *
 * @author plaka
 */
public abstract class Question {
  public String question;
  public abstract Pane getPaneOfChoices();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.io.Serializable;
import javafx.scene.layout.Pane;

/**
 *
 * @author plaka
 */
public abstract class Question implements Serializable {
  public String question;
  public abstract Pane getPaneOfChoices();
  public abstract double getScore();
  public abstract double getPoints();
}

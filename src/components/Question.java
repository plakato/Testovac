/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.io.Serializable;
import java.util.List;
import javafx.scene.layout.Pane;

/**
 *
 * @author plaka
 * Interface which all the questions in the test
 * must implement.
 */
public abstract class Question implements Serializable {
  	public static final long serialVersionUID = 296646684679228885L;

    /**
   * The text of the question.
   */
    public String question;
    /**
     * Creates a pane with all choices. Establishes a listener on choices to
     * evaluate when the student selects it.
     * @return pane of choices
     */
  public abstract Pane getPaneOfChoices();
     /**
     * Calculates the score of the student according
     * to the choices he selected.
     * @return amount of points received
     */
  public abstract double getScore();
  /**
   * Gives maximum amount of points possible to receive
   * in this question.
   * @return points
   */
  public abstract double getPoints();
  
     /**
     * Gives list of all choices to choose from
     * @return choices
     */
  public abstract List<String> getChoices();
}

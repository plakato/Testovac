/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.scene.control.Alert;

/**
 *
 * @author plaka
 */
public class ErrorInformer {
    public static void exitApp() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ojoj, vyskytol sa problém. Aplikácia sa musí ukončiť.");
                    alert.showAndWait();
                    System.exit(0);
    }
    public static void failedInSavingTest() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Váš test nebolo možné uložiť. Sorry.");
                    alert.showAndWait();
                    alert.close();
    }
    
    public static void failedSyncing() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Nepodarilo sa synchronizovať testy so servrom.");
                    alert.showAndWait();
                    alert.close();
    }
}

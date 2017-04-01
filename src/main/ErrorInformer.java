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
 * Shows different alerts.
 */
public class ErrorInformer {
    /**
     * When a serious problem happened, informs user
     * that the application must exit.
     */
    public static void exitApp() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ojoj, vyskytol sa problém. Aplikácia sa musí ukončiť.");
                    alert.showAndWait();
                    System.exit(0);
    }
    /**
     * When there was some problem saving the test, informs the user
     * a gives him an advice to solve the problem.
     */
    public static void failedInSavingTest() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Váš test nebolo možné uložiť. Uistite sa, že váš názov neobsahuje divné znaky, alebo ho skúste zmeniť.");
                    alert.showAndWait();
                    alert.close();
    }
    
    /**
     * When some error happened during the ftp communication with the server,
     * informs the user that the tests were not uploaded/downloaded successfully.
     */
    public static void failedSyncing() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Nepodarilo sa synchronizovať testy so servrom.");
                    alert.showAndWait();
                    alert.close();
    }
}

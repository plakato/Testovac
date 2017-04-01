/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.sun.javafx.scene.control.behavior.TabPaneBehavior;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Debugger;
import components.Question;
import components.Test;
import components.TestSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
/**
 *
 * @author plaka
 * Manages different operations with test.
 * Manages communication with the server.
 */
public class TestManager {
    /**
     * Displays given test. Intended for testing and
     * evaluating correct answers. (student mode only)
     * @param test test to display
     * @param stage 
     */
     public static void displayTest(Test test, Stage stage) {
        Debugger.println(test.getName() + " - is displayed.");
        TabPane tabPane = new TabPane();
        int counter = 1;
        for (Question q : test.getQuestions()) {
           Label instruction = new Label(q.question);
           instruction.setStyle("-fx-font-size: 20");
           Pane choices = q.getPaneOfChoices();
           VBox vbox = new VBox(instruction, choices);
           vbox.setSpacing(10);
           Tab tab = new Tab("Otázka " + Integer.toString(counter), vbox);
           tab.setStyle("-fx-font-size: 20");
           tabPane.getTabs().add(tab);
           counter++;
        }
        
        Button finish = new Button("Ukončiť test!");
        finish.setStyle("-fx-font-size: 20");
        finish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               try {
                   test.evaluate(stage);
               }  catch (IOException e) {
                   Alert alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Ojoj, vyskytol sa problém. Aplikácia sa musí ukončiť.");
                    alert.showAndWait();
                    System.exit(0); 
               }    
            }      
        });
        Button nextQuestion = new Button("Ďalšia");
        nextQuestion.setStyle("-fx-font-size: 20");
        nextQuestion.setOnAction(e -> tabPane.getSelectionModel().selectNext());
        HBox buttons = new HBox(finish, nextQuestion);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        VBox outerVBox = new VBox(tabPane, buttons);
        outerVBox.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(outerVBox);
        stage.setScene(scene);
        stage.show();
    }
    
     /**
      * Displays a screen on which teacher can create a new test.
      * @param stage current stage
      */
    public static void displayCreateNew(Stage stage) {
        try {
            Parent pane = FXMLLoader.load(TestManager.class.getResource("/resources/FXMLNewTest.fxml"));
            stage.setScene(new Scene(pane));
            stage.show();
        }
        catch (IOException e) {
            ErrorInformer.exitApp();
        }
    }
    
    /**
     * Deletes all local tests, downloads tests from the server
     * and saves all downloaded test in the local directory.
     */
    public static void syncTestsWithServer() {
        FTPClient ftp = new FTPClient();
        boolean error = false;
        try {
          int reply;
          String server = "zajicek.endora.cz";
          ftp.connect(server, 21);

          // After connection attempt, we check the reply code to verify
          // success.
          reply = ftp.getReplyCode();

          //Not connected successfully - inform the user
          if(!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            Debugger.println("FTP server refused connection.");
            ErrorInformer.failedSyncing();
            return;
          }
          // LOGIN
        boolean success = ftp.login("plakato", "L13nK4");
        Debugger.println("Login successful: " + success);
        if (success == false) {
            ftp.disconnect();
            ErrorInformer.failedSyncing();
            return;
        }
        ftp.enterLocalPassiveMode();
        // Get all files from the server
        FTPFile[] files = ftp.listFiles();
        System.out.println("Got files! Count: " + files.length);
        // Delete all current test to be replaced with 
        // actulized version
        File[] locals = new File("src/tests/").listFiles();
        for (File f : locals) {
            if (f.getName() == "." || f.getName() == "..") {
                continue;
            }
            f.delete();
        }
        // Copy the files from server to local folder
        int failed = 0;
        for (FTPFile f : files) {
            if (f.isFile()) {
                Debugger.println(f.getName());
                if (f.getName() == "." || f.getName() == "..") {
                    continue;
                }
                File file = new File("src/tests/"+f.getName());
                file.createNewFile();
                OutputStream output = new FileOutputStream(file);
                if (!ftp.retrieveFile(f.getName(), output)) failed++;
                output.close();
            }
        }
        // If we failed to download some file, inform the user
        if (failed != 0) {
            ftp.disconnect();
            ErrorInformer.failedSyncing();
            return;
        }
        // Disconnect ftp client or resolve the potential error
        ftp.logout();
        } catch(IOException e) {
          error = true;
          e.printStackTrace();
        } finally {
          if(ftp.isConnected()) {
            try {
              ftp.disconnect();
            } catch(IOException ioe) {
              // do nothing
            }
          }
        }
        if (error) {
            ErrorInformer.failedSyncing();
              
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Download hotový");
        alert.setHeaderText(null);
        alert.setContentText("Všetky testy boli zo servru úspešne stiahnuté.");

        alert.showAndWait();
        alert.close();
    }
    
    /**
     * Deletes all files on the server and uploads all the
     * tests from the local directory.
     */
    public static void syncServerWithTest() {
        FTPClient ftp = new FTPClient();
        boolean error = false;
        try {
          int reply;
          String server = "zajicek.endora.cz";
          ftp.connect(server, 21);

          // After connection attempt, we check the reply code to verify
          // success.
          reply = ftp.getReplyCode();

          //Not connected successfully - inform the user
          if(!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            Debugger.println("FTP server refused connection.");
            ErrorInformer.failedSyncing();
            return;
          }
          // LOGIN
        boolean success = ftp.login("plakato", "L13nK4");
        Debugger.println("Login successful: " + success);
        if (success == false) {
            ftp.disconnect();
            ErrorInformer.failedSyncing();
            return;
        }
        ftp.enterLocalPassiveMode();
        // Get all files from the server
        FTPFile[] files = ftp.listFiles();
        System.out.println("Got files! Count: " + files.length);
        // Delete all current tests on the server to be replaced with 
        // actualized version from local directory
        for (FTPFile f : files) {
            if (f.getName() == "." || f.getName() == "..") {
                    continue;
            }
            if (f.isFile()) {
                ftp.deleteFile(f.getName());
            }
        }
        // Copy the files from local folder to server
        File localFolder = new File("src/tests/");
        File[] localFiles = localFolder.listFiles();
        int failed = 0;
        for (File f : localFiles) {
            if (f.getName() == "." || f.getName() == "..") {
                continue;
            }
            if (f.isFile()) {
                Debugger.println(f.getName());
                File file = new File("src/tests/"+f.getName());
                InputStream input = new FileInputStream(file);
                if (!ftp.storeFile(f.getName(), input)) failed++;
                input.close();
            }
        }
        // If we failed to upload some file, inform the user
        if (failed != 0) {
            ftp.disconnect();
            ErrorInformer.failedSyncing();
            return;
        }
        // Disconnect ftp client or resolve the potential error
        ftp.logout();
        } catch(IOException e) {
          error = true;
          e.printStackTrace();
        } finally {
          if(ftp.isConnected()) {
            try {
              ftp.disconnect();
            } catch(IOException ioe) {
              // do nothing
            }
          }
          if (error) {
              ErrorInformer.failedSyncing();
              return;
          }
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Upload hotový");
        alert.setHeaderText(null);
        alert.setContentText("Úspešne sa podarilo skopírovať všetky testy na server!");

        alert.showAndWait();
        alert.close();
        }
    
}

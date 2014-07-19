/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Stephen
 */
public class UpdateGui  implements Runnable {

    String fileName;
    Label updateNotification;
    // a new stage for update scene
    Stage window;
    Scene check;
    GridPane stack;
    ProgressBar pb;
   
    boolean running = true;
    boolean sessions = true;
 
    Label updateLabel = new Label("Update in Progress");
/**
 * Builds the Gui that is used when the update button is pressed
 * it has a couple labels and a progress bar
 */
    public UpdateGui() {

        window = new Stage();
        stack = new GridPane();
        pb = new ProgressBar();
        check = new Scene(stack, 380, 120);
        stack.setAlignment(Pos.CENTER);
        stack.setHgap(0);
        stack.setVgap(10);
        stack.setPadding(new Insets(25, 25, 25, 25));
        stack.add(updateLabel, 0, 0, 2, 1);
        // new progress bar
        pb.setMinWidth(240);
        stack.add(pb, 0, 2, 2, 1);
        window.setTitle("Update");
        updateNotification = new Label("updating");
        updateNotification.setId("update");
        stack.add(updateNotification, 0, 1, 2, 1);
        // add the stylesheet
        check.getStylesheets().add("fxml.css");
        window.setScene(check);
        window.show();
        window.toFront();
    }

    /**
     * will run through and find and update the most current session
     */
    public void run() {
        for (int i = 0; i< 10000; i++) {
            fileName = "You are here" + i;
            //update the label
            if (!window.isShowing())
                this.kill();
            if (running) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateNotification.setText("updating " + fileName);
                    }

                });
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    updateNotification.setText("Interrupted!");
                    pb.setVisible(false);
                    this.kill();

                }

            } else {
                break;
            }

        }
        // if the loop finishes 
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updateNotification.setText("Done!");
                pb.setVisible(false);
                window.close();
            }

        });
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            this.kill();
        }
    }

    /**
     * change running to false;
     */
    public void kill() {

        this.running = false;
    }
}

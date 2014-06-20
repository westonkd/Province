/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Stephen
 */
public class Miner extends Application {

    Gui2 gui = Gui2.getInstance();
    //Librarian librarian = Librarian.getInstance();
    //Scrapper scrapper = Scrapper.getInstance();
    //FileWriter writer = FileWriter.getInstance();
    String person;
    String topic;
    String startDate;
    String endDate;
    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
/**
 * This function calls the guis start method and builds the user interface
 * it also gets the search parameters and calls the librarian to search. uses
 * action event with a button to search
 * @param primaryStage
 * @throws InterruptedException 
 */
    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        gui.start(primaryStage);
        gui.getFind().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gui.getProgress().setVisible(true);
                person = gui.getPeople().getValue();
                topic = gui.getTopical().getValue();
                startDate = gui.getStartDate().getText();
                endDate = gui.getEndDate().getText();
                
                if (person.equals("")  && !topic.equals("")){
                    //librarian.searchTopic(topic,startDate,endDate);
                }
                else if (!person.equals("") && topic.equals("")){
                    //librarian.searchPerson(person,startDate,endDate);
                }
                else if (!person.equals("") && !topic.equals("")){
                    //librarian.searchBoth(person,topic,startDate,endDate);
                }
                gui.getProgress().setVisible(false);
            }
        });
    }

    /**
     * Main method will check to see if the database is empty if it is empty
     * it will crawl through the html of the website to populate the xml data 
     * base so that it can be searchable. It will build the GUI and call the 
     * file writer to write the file from xml to txt and a docx file. 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

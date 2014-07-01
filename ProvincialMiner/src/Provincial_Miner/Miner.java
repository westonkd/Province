/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner;

import Provincial_Miner.system.Librarian;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 *
 * @author Stephen
 */
public class Miner extends Application {

    Gui2 gui = Gui2.getInstance();
    Librarian librarian = new Librarian();
    //Scrapper scrapper = Scrapper.getInstance();
    //FileWriter writer = FileWriter.getInstance();
    String person = "";
    String topic = "";
    LocalDate startDate;
    LocalDate endDate;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * This function calls the guis start method and builds the user interface
     * it also gets the search parameters and calls the librarian to search.
     * uses action event with a button to search
     *
     * @param primaryStage
     * @throws InterruptedException
     */
    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        gui.start(primaryStage);
        /**
         * Button even will search the parameters given. User must enter a
         * person or topic or both. If dates start date is left blank it will
         * get everything back to 1900 if end date left blank it will be set to
         * current date.
         */
        gui.getFind().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                person = gui.getPeople().getValue();
                topic = gui.getTopical().getValue();
                // if nothing entered in for start date set it to 1900
                if (gui.getStartDate().getValue() == null) {
                    startDate = startDate.of(1900, 1, 1);
                } else {
                    startDate = gui.getStartDate().getValue();
                }
                // if nothing entered for end date set it to current date
                if (gui.getEndDate().getValue() == null) {
                    endDate = now();
                } else {
                    endDate = gui.getEndDate().getValue();
                }
                // if dates are reversed or both person and topic are null
                // display error 
                if (startDate.isAfter(endDate)){
                        gui.error("Invalid Dates");
                }
                 if (person == null && topic == null) {
                    gui.error("Invalid Parameters");
                } else {
                    //progress bar to know its in process
                    gui.getProgress().setVisible(true);

                    // topic search
                    if (person == null && !topic.equals("")) {
                        librarian.searchTopic(topic, startDate, endDate);
                    } // person search
                    else if (!person.equals("") && topic == null) {
                        librarian.searchPerson(person,startDate,endDate);
                    } // both search
                    else if (!person.equals("") && !topic.equals("")) {
                        librarian.searchBoth(person,topic,startDate,endDate);
                    }
                    gui.getProgress().setVisible(false);
                }
            }
        });
    }

    /**
     * Main method will check to see if the database is empty if it is empty it
     * will crawl through the html of the website to populate the xml data base
     * so that it can be searchable. It will build the GUI and call the file
     * writer to write the file from xml to txt and a docx file.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
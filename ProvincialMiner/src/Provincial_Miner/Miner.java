/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Provincial_Miner;

import com.gtranslate.Language;
import com.gtranslate.Translator;

import Provincial_Miner.system.FileFinder;
import Provincial_Miner.system.Librarian;
import Provincial_Miner.system.PartialQuebecScraper;
import Provincial_Miner.system.Populator;
import Provincial_Miner.system.WriteFile;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 *
 * @author Stephen
 */
public class Miner extends Application {

    Gui2 gui = Gui2.getInstance();
    Translator translate = Translator.getInstance();
    Librarian librarian = new Librarian();
    FileFinder files = new FileFinder();
    PartialQuebecScraper scraper = new PartialQuebecScraper();
    //FileWriter writer = FileWriter.getInstance();
    String person = "";
    String topic = "";
    LocalDate startDate;
    LocalDate endDate;
    Thread thread;
    boolean isOn = false;
    Populator pop = new Populator();

    public Miner() {

    }

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
         * This will update the topics depending on what speaker is chosen
         */
        gui.getPeople().valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                String name = gui.getPeople().getValue();

                try {
                    // create a new populator
                    Populator pop = new Populator();
                    FileFinder files = new FileFinder();
                    // find the files to populate from
                    ArrayList<String> allFiles = files.findFiles();
                    ArrayList<String> allTopics = new ArrayList();
                    // find all topics related to the person speaking
                    for (String s : allFiles) {
                        pop.setFileName(s);
                        allTopics.addAll(pop.personToTopicPopulate(name));
                    }
                    //clear the old list
                    gui.getTopicalList().clear();
                    LinkedHashSet noDupes = new LinkedHashSet();
                    noDupes.addAll(allTopics);
                    gui.getTopicalList().addAll(noDupes);
                    //repopulate if empty
                    if (gui.getTopicalList().isEmpty()) {
                        noDupes.addAll(gui.getSubs());
                        gui.getTopicalList().addAll(noDupes);
                    }
                    gui.getTopical().setItems(gui.getTopicalList());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        /**
         * update will get the most recent session and replace it with a new
         * version
         */
        gui.getUpdate().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // new runnable thread update gui
                if (isOn) {
                    if (!thread.isAlive()) {
                        isOn = false;
                    }
                }
                if (!isOn) {
                    Runnable updater = new UpdateGui();
                    thread = new Thread(updater);
                    thread.start();
                    isOn = true;
                }
            }
        });

        /**
         * Button event will search the parameters given. User must enter a
         * person or topic or both. If dates start date is left blank it will
         * get everything back to 1800 if end date left blank it will be set to
         * current date.
         */
        gui.getFind().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                gui.getProgress().setVisible(true);

                person = gui.getPeople().getValue();
                topic = gui.getTopical().getValue();
                // if nothing entered in for start date set it to 1900
                if (gui.getStartDate().getValue() == null) {
                    startDate = startDate.of(1800, 1, 1);
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
                if (startDate.isAfter(endDate)) {
                    gui.error("Invalid Dates");
                }
                if (person == null && topic == null) {
                    gui.error("Invalid Parameters");
                } else {
                    //progress bar to know its in process

                    ArrayList<String> validFiles = files.findFiles();
                    String total = "";
                    String head = "";
                    // topic search
                    for (String s : validFiles) {
                        librarian.setFileName(s);
                        if ((person == null || person.equals("")) && !topic.equals("")) {
                            total = total + librarian.searchTopic(topic, startDate, endDate);
                            head = "<topic>" + topic + "</topic>" + "\n"
                                    + "<date>" + startDate + " to " + endDate + "</date>" + "\n";
                        } // person search
                        else if (!person.equals("") && (topic == null || topic.equals(""))) {
                            total = total + librarian.searchPerson(person, startDate, endDate);
                            head = "<name>" + person + "</name>" + "\n"
                                    + "<date>" + startDate + " to " + endDate + "</date>" + "\n";
                        } // both search
                        else if (!person.equals("") && !topic.equals("")) {
                            total = total + librarian.searchBoth(person, topic, startDate, endDate);
                            head = "<name>" + person + " </name> " + "\n"
                                    + "<topic>" + topic + "</topic>" + "\n"
                                    + "<date>" + startDate + " to " + endDate + "</date>" + "\n";
                        }

                    }
                    String complete = null;
                    total += "VNV." + total;
                    if (total.equals("")) {
                        gui.error("No content for search parameters");
                    } else {
                        if (gui.getLanguage().isSelected()) {

                            //translate the content
                            String parts[];

                            //split by periods if greater than 1500
                            parts = total.split("\\.");
                            for (String s : parts) {
                                total = translateContent(s);
                                complete += total + ". ";

                            }

                        } else {
                            complete = total;
                        }
                        String done = (head + complete);

                        //write to the file
                        new WriteFile().writeDataFile(done, person, topic);
                        System.out.println(done);
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

    private String translateContent(String content) {
        try {
            //create new translator and translate the text

            content = translate.translate(content, Language.FRENCH, Language.ENGLISH);
        } catch (Exception e) {
            return content;
        }
        return content;
    }

}

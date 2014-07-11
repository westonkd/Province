package Provincial_Miner;

import Provincial_Miner.system.FileFinder;
import Provincial_Miner.system.Populator;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This will build the User Interface for the provincial miner application.
 *
 * @author Stephen
 *
 */
public class Gui2 extends Application {

    private static Gui2 instance = null;

    /**
     * This is a grid style layout that will be centered at the bottom
     */
    GridPane grid;

    /**
     * Label Identifying the start date with MM/DD/YYYY
     */
    Label startDateFinder;

    /**
     * Label Identifying the end date with MM/DD/YYYY up to current date
     */
    Label endDateFinder;

    /**
     * Drop down list of all the people that can be searched
     */
    ComboBox<String> people;

    /**
     * Observable list will populate the people combo box
     */
    ObservableList<String> peopleList;

    /**
     * Drop down list of all the topics that can be searched
     */
    ComboBox<String> topical;

    /**
     * topicalList will populate the topical combo box
     */
    ObservableList<String> topicalList;

    /**
     * User enters the start date
     */
    DatePicker startDate;

    /**
     * User enters the end date
     */
    DatePicker endDate;

    /**
     * Button will start the search and open the file
     */
    Button find;

    /**
     * Progress bar will show it is working.
     */
    ProgressBar progress;

    /**
     * scene will use a css sheet to style and hold all the GUI items
     */
    Scene scene;

    /**
     * Label identifying person
     */
    Label personLabel;

    /**
     * Label identifying topic
     */
    Label topicLabel;

    /**
     * title for the GUI
     */
    Label title;
    
    ArrayList<String> peeps = new ArrayList<String>();
    ArrayList<String> subs = new ArrayList<String>();

    public GridPane getGrid() {
        return grid;
    }

    public Label getStartDateFinder() {
        return startDateFinder;
    }

    public Label getEndDateFinder() {
        return endDateFinder;
    }

    public ComboBox<String> getPeople() {
        return people;
    }

    public ObservableList<String> getPeopleList() {
        return peopleList;
    }

    public ComboBox<String> getTopical() {
        return topical;
    }

    public ObservableList<String> getTopicalList() {
        return topicalList;
    }

    public DatePicker getStartDate() {
        return startDate;
    }

    public DatePicker getEndDate() {
        return endDate;
    }

    public Button getFind() {
        return find;
    }

    public ProgressBar getProgress() {
        return progress;
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * empty constructor to make sure it doesn't get instantiation.
     */
    protected Gui2() {

    }

    /**
     * Returns an instance of the Gui to keep it seperate from the controller
     *
     * @return instance
     */
    public static Gui2 getInstance() {
        if (instance == null) {
            instance = new Gui2();
        }
        return instance;
    }

    public void getData() {
        Populator pop = new Populator();
        FileFinder files = new FileFinder();
        LocalDate begin = null;
        begin = begin.of(1800, 1, 1);
        LocalDate end = now();
        ArrayList<String> allFiles = files.findFiles(begin, end);
        for (String s : allFiles) {
            pop.setFileName(s);
            pop.populate();
            for (String r : pop.getPeople()) {
            }
        }
            peeps = pop.getPeople();
            subs = pop.getTopics();
        }

      

    

    /**
     * Start method is where it builds the GUI
     */
    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        primaryStage.setTitle("Provincial Mining");
        peopleList = FXCollections.observableArrayList();
        topicalList = FXCollections.observableArrayList();

        this.getData();
        Collections.sort(peeps);
        Collections.sort(subs);
        
        LinkedHashSet noDuplicates = new LinkedHashSet(peeps);
        LinkedHashSet noDuplicates2 = new LinkedHashSet(subs);
        peopleList.addAll(noDuplicates);
        topicalList.addAll(noDuplicates2);
        grid = new GridPane();
        grid.setAlignment(Pos.BOTTOM_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        title = new Label("Quebec");
        title.setId("title");
        grid.add(title, 0, 0);

        personLabel = new Label("Person");
        grid.add(personLabel, 0, 2);
        topicLabel = new Label("Topic");
        grid.add(topicLabel, 1, 2);

        startDateFinder = new Label("Start Date");
        grid.add(startDateFinder, 0, 6);

        endDateFinder = new Label("End Date");
        grid.add(endDateFinder, 1, 6);
        people = new ComboBox();
        people.setPromptText("Person");
        people.setMinWidth(225);
        people.setEditable(true);
        grid.add(people, 0, 3);
        people.setItems(peopleList);

        topical = new ComboBox();
        topical.setPromptText("Topic");
        topical.setMinWidth(225);
        topical.setEditable(true);
        grid.add(topical, 1, 3);
        topical.setItems(topicalList);

        startDate = new DatePicker();
        startDate.setPromptText("Start Date MM/DD/YYYY");
        startDate.setTooltip(new Tooltip("Can be left blank" + "\n"
                + "to get furthest back date"));
        grid.add(startDate, 0, 7);

        endDate = new DatePicker();
        endDate.setPromptText("End Date MM/DD/YYYY");
        endDate.setTooltip(new Tooltip("Can be left blank" + "\n"
                + "for current date"));
        grid.add(endDate, 1, 7);
        Font font = new Font(14);

        find = new Button("Search");
        find.setTooltip(new Tooltip("Must have a Person\nor topic\nor both"));
        find.setFont(font);
        find.setMinSize(60, 30);
        grid.add(find, 2, 7);

        progress = new ProgressBar();
        progress.setMaxWidth(65);
        grid.add(progress, 2, 8);
        progress.setVisible(false);

        // set the scene and display it
        scene = new Scene(grid, 600, 300);
        scene.getStylesheets().add("fxml.css");
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    /**
     * Error message if the inputs are invalid
     */
    public void error(String error_message) {
        GridPane stack = new GridPane();
        stack.setAlignment(Pos.CENTER);
        stack.setHgap(0);
        stack.setVgap(30);
        stack.setPadding(new Insets(50, 50, 50, 50));
        Label errorMessage = new Label(error_message);
        //errorMessage.setMinWidth(200);
        stack.add(errorMessage, 0, 0,2,1);

        Button ok = new Button("ok");
        ok.setMinWidth(80);
        stack.add(ok, 1, 1);

        Stage message = new Stage();
        message.setTitle("Error");
        Scene check = new Scene(stack, 280, 120);
        // check.getStylesheets().add("fxml.css");
        message.setScene(check);
        message.show();
        message.toFront();

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                message.close();
            }
        });
    }
}

package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.EditTaskDemo;
import ui.ListView;
import ui.PomoTodoApp;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private static final String todobarFXML
            = "/Users/sensiray7/IdeaProjects/PomoTODO_phase4_c6t1b/resources/fxml/Todobar.fxml";
    private File todoOptionsPopUpFxmlFile = new File(todoOptionsPopUpFXML);
    private File todoActionsPopUpFxmlFile = new File(todoActionsPopUpFXML);
    private File todobarFxmlFile = new File(todobarFXML);

    private JFXPopup optionPopUp;
    private JFXPopup actionPopUp;


    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private Task task;

    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }

    // EFFECTS: returns the task
    public Task getTask() {
        return task;
    }

    // EFFECTS: initialize file
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setResources(resources);

        loadTodoBarOptionsPopUp();
        loadTodoOptionsPopUpActionListener();

        loadTodoBarActionsPopUp();
        loadTodoActionsPopUpActionListener();
    }


    // EFFECTS: load options pop up (edit, delete)
    private void loadTodoBarOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader
                    = new FXMLLoader(todoOptionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoOptionsPopUpController());
            optionPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException i) {
            throw new RuntimeException(i);
        }
    }

    // EFFECTS: load actions pop up (to do, up next, in progress, done, pomodoro!)
    private void loadTodoBarActionsPopUp() {
        try {
            FXMLLoader fxmlLoader
                    = new FXMLLoader(todoActionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new TodoActionsPopUpController());
            actionPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException i) {
            throw new RuntimeException(i);
        }
    }

    // EFFECTS: show to do options pop up when its icon is clicked
    private void loadTodoOptionsPopUpActionListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                optionPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }

    // EFFECTS: show to do actions pop up when its icon is clicked
    private void loadTodoActionsPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                actionPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12,
                        15);
            }
        });
    }


    class TodoOptionsPopUpController {
        @FXML
        private JFXListView<?> optionPopUpList;

        // EFFECTS: selects option based on user selection
        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    editTask(getTask());
                    Logger.log("TodoBarOptionsPopUpController", "Edit task " + getTask().getDescription());
                    break;
                case 1:
                    deleteTask(getTask());
                    Logger.log("TodoBarOptionsPopUpController", "Delete task " + getTask().getDescription());
                    break;
                default:
                    Logger.log("TodoBarOptionsPopUpController", "No action is implemented for selected option");
            }
            optionPopUp.hide();
        }

        // MODIFIES: this
        // EFFECTS: creates new edit task
        @FXML
        private void editTask(Task t) {
            PomoTodoApp.setScene(new EditTask(t));
        }

        // MODIFIES: this
        // EFFECTS: deletes selected task
        @FXML
        private void deleteTask(Task t) {
            PomoTodoApp.getTasks().remove(t);
            returnToListView();
        }

        // EFFECTS: returns user to list view
        private void returnToListView() {
            Logger.log("TodoBarOptionsPopUpController", "Return to the list view UI.");
            PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));
        }
    }

    class TodoActionsPopUpController {

        @FXML
        private JFXListView<?> actionPopUpList;

        // EFFECTS: selects action based on user selection
        @FXML
        private void submit() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0: Logger.log("TodoBarActionsPopUpController", "To Do is not supported in this version");
                    break;
                case 1: Logger.log("TodoBarActionsPopUpController", "Up Next is not supported in this version");
                    break;
                case 2: Logger.log("TodoBarActionsPopUpController", "In Progress is not supported in this version");
                    break;
                case 3: Logger.log("TodoBarActionsPopUpController", "Done is not supported in this version");
                    break;
                case 4: Logger.log("TodoBarActionsPopUpController", "Pomodoro! is not supported in this version");
                    break;
                default: Logger.log("TodoBarActionsPopUpController", "No action is implemented for selected option");
            }
            actionPopUp.hide();
        }
    }
}

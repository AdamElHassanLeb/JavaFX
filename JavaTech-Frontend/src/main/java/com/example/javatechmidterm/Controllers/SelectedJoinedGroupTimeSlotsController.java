package com.example.javatechmidterm.Controllers;

import com.example.javatechmidterm.Main;
import com.example.javatechmidterm.Models.GroupMember;
import com.example.javatechmidterm.Models.TimeGroup;
import com.example.javatechmidterm.Models.TimeSlot;
import com.example.javatechmidterm.Models.User;
import com.example.javatechmidterm.Services.GroupMember_Service;
import com.example.javatechmidterm.Services.TimeGroup_Service;
import com.example.javatechmidterm.Services.TimeSlot_Service;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class SelectedJoinedGroupTimeSlotsController implements Initializable {

    private static User user;
    private static GroupMember groupMember;
    private static ArrayList<TimeSlot> arr;
    private static TimeGroup timeGroup;
    @FXML
    protected Button backButton;
    @FXML
    protected DatePicker datePicker;
    @FXML
    protected ListView<TimeSlot> listView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = Main.getUser();
        datePicker.setValue(LocalDate.from(LocalDateTime.now()));

        try {
            listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                int index = listView.getSelectionModel().getSelectedIndex();
                if (arr.get(index).isReserved() && arr.get(index).getUserId() != user.getId()) return;
                showDialog(index);
            });

        }catch (Exception e){
            System.out.println("Batata");
        }
    }

    @FXML
    protected void leaveTimeGroup(ActionEvent event) {

        try {
            GroupMember_Service.deleteGroupMember(groupMember.getId());
            Parent root = FXMLLoader.load(Main.class.getResource("Views/joinedTimeGroups-view.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println("Potato");
        }

    }

    @FXML
    protected void backToDashboard(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(Main.class.getResource("Views/joinedTimeGroups-view.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println("Potato");
        }
    }
    @FXML
    protected void datePicked(ActionEvent event) {
        setTable();
    }
    public void setTimeGroup(int id){
        try {
            timeGroup = TimeGroup_Service.getTimeGroupById(id);
        }catch (Exception e){
            System.out.println("Couldn't create object");
        }
        setTable();
    }

    //TODO Why does it crash when I call this from an event listener ONLY THEN
    private void setTable(){

        LocalDate selectedDate = datePicker.getValue();

        // Extract data from the selected date
        int day = selectedDate.getDayOfMonth();
        int month = selectedDate.getMonthValue();
        int year = selectedDate.getYear();


        try {
            arr = TimeSlot_Service.getTimeSlotsByGroupAndDay(timeGroup.getId(), year, month, day);
        }catch (Exception e){
            System.out.println("Error");
            return;
        }

        if(arr == null){return;}
        printToTable();
    }

    private void printToTable(){

        /*for (int i = 0; i < arr.toArray().length; i++) {
            TimeSlot x = arr.get(i);
            listView.getItems().add("From " + x.getAmPmStartTime() + " To " +
                    x.getAmPmEndTime() + " Status: " + (x.isReserved() ? "Reserved" : "Available"));
        }*/

        ObservableList<TimeSlot> list = FXCollections.observableArrayList(arr);

        listView.setItems(list);

        listView.setCellFactory(timeSlotListView -> new ListCell<TimeSlot>() {
            @Override
            protected void updateItem(TimeSlot item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText("From " + item.getAmPmStartTime() + " To " +
                            item.getAmPmEndTime() + " Status: " + (item.isReserved() ? "Reserved" : "Available")
                            + (item.isReserved() && item.getUserId() == user.getId() ? " By You" : ""));
                }
                getStyleClass().removeAll("reserved", "notReserved", "reservedByMe");
                if (item != null && item.isReserved() && Objects.equals(item.getUserId(), user.getId())) {
                    getStyleClass().add("reservedByMe");
                }
                else if(item != null && item.isReserved() && !Objects.equals(item.getUserId(), user.getId())){
                    getStyleClass().add("reserved");
                }
                else if(item != null && !item.isReserved()) {
                    getStyleClass().add("notReserved");
                }

            }
        });
    }

    private void showDialog(int index) {
        // Create the dialog
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Reservation Confirmation");
        dialog.setResizable(false);

        // Create UI elements
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #333333;");

        Label confirmationLabel = new Label(arr.get(index).isReserved() ?
                "Are You Sure You Want To Cancel Your Reservation" :
                "Are you sure you want to reserve this timeslot?");
        confirmationLabel.getStyleClass().add("labels"); // Apply CSS style class

        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("dashboard-button"); // Apply CSS style class
        Button yesButton = new Button("Yes");
        yesButton.getStyleClass().add("dashboard-button"); // Apply CSS style class

        // Close button action
        cancelButton.setOnAction(e -> dialog.close());

        // Join button action
        yesButton.setOnAction(e -> {

            try {
                TimeSlot ts = arr.get(index);
                if(!ts.isReserved()){
                    TimeSlot_Service.reserveTimeSlot(ts.getId(), user.getId());
                }
                else
                    TimeSlot_Service.removeTimeSlotReservation(ts.getId(), user.getId());
            } catch (Exception ex) {
                System.out.println("Error");
            }
            //setTable();
            reload();
            dialog.close(); // Close the dialog after handling the action
        });

        // Add UI elements to grid
        grid.add(confirmationLabel, 0, 0, 2, 1);
        grid.add(cancelButton, 0, 1);
        grid.add(yesButton, 1, 1);

        // Add grid to scene
        Scene dialogScene = new Scene(grid, 400, 150);
        dialogScene.setFill(Color.web("#333333")); // Set transparent background
        dialogScene.getStylesheets().add(Main.class.getResource("Styles/Style.css").toExternalForm()); // Add your CSS file
        dialog.setScene(dialogScene);

        // Show the dialog
        dialog.showAndWait(); // Show dialog and wait for it to be closed
    }

    //Solution for event listener bug
    private void reload(){
        LocalDate d = this.datePicker.getValue();
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/selectedJoinedGroupTimeSlots-view.fxml"));
            Parent root = loader.load();
            SelectedJoinedGroupTimeSlotsController s = loader.getController();
            Stage stage = Main.getStage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            s.setDateLoad(d);
            s.setTimeGroup(timeGroup.getId());
            stage.show();
        }catch (Exception e){
            System.out.println("Potato");
        }
    }

    private void alert(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(message);
        String cssFile = Main.class.getResource("Styles/Style.css").toExternalForm();
        a.getDialogPane().getStylesheets().add(cssFile);
        a.show();
    }

    public void setDateLoad(LocalDate d){
        datePicker.setValue(d);
    }

    public void setGroupMember(GroupMember groupMember){
        this.groupMember = groupMember;
    }
}

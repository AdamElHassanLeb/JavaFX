package com.example.javatechmidterm.Controllers;

import com.example.javatechmidterm.Main;
import com.example.javatechmidterm.Models.TimeGroup;
import com.example.javatechmidterm.Models.TimeSlot;
import com.example.javatechmidterm.Models.User;
import com.example.javatechmidterm.Services.TimeGroup_Service;
import com.example.javatechmidterm.Services.TimeSlot_Service;
import com.example.javatechmidterm.Services.User_Service;
import com.example.javatechmidterm.Utils.LocalDateTimeAdapter;
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
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ManageTimeSlotsController implements Initializable {

    private static User user;
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
                showDialog(index);
            });

        }catch (Exception e){
            System.out.println("Batata");
        }
    }

    @FXML
    protected void deleteTimeGroup(ActionEvent event){

        try {
            TimeGroup_Service.deleteTimeGroup(timeGroup.getId());
            Parent root = FXMLLoader.load(Main.class.getResource("Views/manageTimeGroups-view.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println("Potato");
        }
    }

    @FXML
    protected void addTimeSlot(ActionEvent e){
        showReservationDialog();
    }

    @FXML
    protected void backToDashboard(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(Main.class.getResource("Views/manageTimeGroups-view.fxml"));
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
            System.out.println("Error Getting By Date");
            e.printStackTrace();
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
                    try {
                        setText("From " + item.getAmPmStartTime() + " To " +
                                item.getAmPmEndTime() + " Status: " + (item.isReserved() ?
                                ("Reserved By: " + (User_Service.getUserById(item.getUserId()).getFirstName()) + " " +
                                        (User_Service.getUserById(item.getUserId()).getLastName())) : "Available"));
                    }catch (Exception exception){
                        setText("From " + item.getAmPmStartTime() + " To " +
                                item.getAmPmEndTime() + " Status: " + (item.isReserved() ?
                                "Error Unavailable User" : "Available"));
                    }
                }
                getStyleClass().removeAll("reserved", "notReserved", "reservedByMe");
                if (item != null && item.isReserved()) {
                    getStyleClass().add("reservedByMe");
                }
                else if(item != null && !item.isReserved()) {
                    getStyleClass().add("notReserved");
                }

            }
        });
    }

    private void showDialog(int index) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Reservation Options");
        dialog.setHeaderText("Reservation Actions");

        // Set the button types
        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        ButtonType chooseUserButtonType = new ButtonType("Choose User", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType freeButtonType = new ButtonType("Free", ButtonBar.ButtonData.OK_DONE); // Declare it here

        // Add buttons based on reservation status
        if (arr.get(index).isReserved()) {
            dialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, freeButtonType /*,chooseUserButtonType*/, cancelButtonType);
        } else {
            dialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, /*chooseUserButtonType,*/ cancelButtonType);
        }

        // Set content text
        dialog.setContentText("Choose an action for the reservation:");

        // Handle button actions
        dialog.setResultConverter(buttonType -> {
            if (buttonType == deleteButtonType) {
                // Handle delete action
                deleteReservation(index);
            } else if (buttonType == freeButtonType) {
                // Handle free action
                freeReservation(index);
            } else if (buttonType == chooseUserButtonType) {
                // Handle choose user action
                chooseUserForReservation(index);
            }
            return null;
        });

        // Apply CSS
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(Main.class.getResource("Styles/Style.css").toExternalForm()); // Add your CSS file
        dialogPane.getStyleClass().add("dialog-pane");

        dialog.showAndWait();
    }


    // Method to delete reservation
    private void deleteReservation(int index) {
        try{
            TimeSlot_Service.deleteTimeSlot(arr.get(index).getId());
            reload();
        }catch (Exception ex){
            System.out.println("Can't Delete");
        }
    }

    // Method to free reservation
    private void freeReservation(int index) {
        try {
            TimeSlot_Service.removeTimeSlotReservation(arr.get(index).getId(), user.getId());
        }catch (Exception ex){
            System.out.println("Couldn't Free");
        }

        reload();
    }

    // Method to choose user for reservation
    private void chooseUserForReservation(int index) {
        reload();
    }


    //Solution for event listener bug
    private void reload(){
        LocalDate d = this.datePicker.getValue();
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/manageTimeSlots-view.fxml"));
            Parent root = loader.load();
            ManageTimeSlotsController s = loader.getController();
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


    public void showReservationDialog() {
        // Create the dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Reservation Date and Time");
        dialog.setHeaderText("Select Date and Time for Reservation");

        // Set the button types
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, cancelButtonType);

        // Create DatePickers
        DatePicker datePicker1 = new DatePicker();
        DatePicker datePicker2 = new DatePicker();
        //Set To Current Date
        datePicker1.setValue(LocalDate.from(LocalDateTime.now()));
        datePicker2.setValue(LocalDate.from(LocalDateTime.now()));

        // Create ComboBoxes for time with default values set to 1:00 AM
        ComboBox<String> timePicker1Hour = new ComboBox<>();
        ComboBox<String> timePicker1Minute = new ComboBox<>();
        ComboBox<String> timePicker1AMPM = new ComboBox<>();
        ComboBox<String> timePicker2Hour = new ComboBox<>();
        ComboBox<String> timePicker2Minute = new ComboBox<>();
        ComboBox<String> timePicker2AMPM = new ComboBox<>();

        // Populate ComboBoxes for time
        for (int i = 1; i <= 12; i++) {
            timePicker1Hour.getItems().add(String.valueOf(i));
            timePicker2Hour.getItems().add(String.valueOf(i));
        }
        for (int i = 0; i <= 59; i++) {
            timePicker1Minute.getItems().add(String.format("%02d", i));
            timePicker2Minute.getItems().add(String.format("%02d", i));
        }
        timePicker1AMPM.getItems().addAll("AM", "PM");
        timePicker2AMPM.getItems().addAll("AM", "PM");

        // Set default time to 1:00 AM
        timePicker1Hour.setValue("1");
        timePicker1Minute.setValue("00");
        timePicker1AMPM.setValue("AM");
        timePicker2Hour.setValue("1");
        timePicker2Minute.setValue("00");
        timePicker2AMPM.setValue("AM");

        // Create layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("From Date:"), datePicker1, new Label("Time:"), timePicker1Hour, new Label(":"), timePicker1Minute, timePicker1AMPM);
        grid.addRow(1, new Label("To Date:"), datePicker2, new Label("Time:"), timePicker2Hour, new Label(":"), timePicker2Minute, timePicker2AMPM);

        // Add layout to dialog pane
        dialog.getDialogPane().setContent(grid);

        // Apply CSS styles
        dialog.getDialogPane().getStylesheets().add(Main.class.getResource("Styles/Style.css").toExternalForm());

        // Add action listener for the Add button
        Button addButton = (Button) dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setOnAction(e -> {
            // Check if any field is null
            if (datePicker1.getValue() == null || datePicker2.getValue() == null ||
                    timePicker1Hour.getValue() == null || timePicker1Minute.getValue() == null || timePicker1AMPM.getValue() == null ||
                    timePicker2Hour.getValue() == null || timePicker2Minute.getValue() == null || timePicker2AMPM.getValue() == null) {
                showAlert("All fields are required.");
                return;
            }


            // Get the selected date and time for both slots
            int year1 = datePicker1.getValue().getYear();
            int month1 = datePicker1.getValue().getMonthValue();
            int day1 = datePicker1.getValue().getDayOfMonth();
            int hour1 = Integer.parseInt(timePicker1Hour.getValue());
            int minute1 = Integer.parseInt(timePicker1Minute.getValue());
            if (timePicker1AMPM.getValue().equals("PM")) {
                hour1 += 12;
            }

            int year2 = datePicker2.getValue().getYear();
            int month2 = datePicker2.getValue().getMonthValue();
            int day2 = datePicker2.getValue().getDayOfMonth();
            int hour2 = Integer.parseInt(timePicker2Hour.getValue());
            int minute2 = Integer.parseInt(timePicker2Minute.getValue());
            if (timePicker2AMPM.getValue().equals("PM")) {
                hour2 += 12;
            }

            if(year1 > year2){
                alert("Start Time Must Be less then End Time");
                return;
            }
            else if(month1 > month2){
                alert("Start Time Must Be less then End Time");
                return;}
            else if(day1 > day2){
                alert("Start Time Must Be less then End Time");
                return;
            }
            else if(hour1 > hour2){
                alert("Start Time Must Be less then End Time");
                return;
            }
            else if(minute1 > minute2){
                alert("Start Time Must Be less then End Time");
                return;
            }

            // Create Calendar objects and set the date and time
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(year1, month1 - 1, day1, hour1, minute1, 0); // Month is 0-based
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(year2, month2 - 1, day2, hour2, minute2, 0); // Month is 0-based



            // Convert Calendar objects to Timestamps
            Timestamp timestamp1 = new Timestamp(calendar1.getTimeInMillis());
            Timestamp timestamp2 = new Timestamp(calendar2.getTimeInMillis());

            try{
                TimeSlot_Service.insertTimeSlot(timeGroup.getId(), timestamp1.toLocalDateTime(), timestamp2.toLocalDateTime(), false);
            }catch (Exception ex){
                System.out.println("Couldnt add");
            }

            setTable();
            dialog.close(); // Close the dialog after handling the action
        });

        // Show the dialog
        dialog.showAndWait();
    }

    // Method to show alert dialog
    private static void showAlert(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(message);
        String cssFile = Main.class.getResource("Styles/Style.css").toExternalForm();
        a.getDialogPane().getStylesheets().add(cssFile);
        a.show();
    }
}

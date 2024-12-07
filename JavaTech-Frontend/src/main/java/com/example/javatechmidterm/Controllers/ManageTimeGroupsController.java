package com.example.javatechmidterm.Controllers;

import com.example.javatechmidterm.Main;
import com.example.javatechmidterm.Models.GroupMember;
import com.example.javatechmidterm.Models.TimeGroup;
import com.example.javatechmidterm.Models.User;
import com.example.javatechmidterm.Services.GroupMember_Service;
import com.example.javatechmidterm.Services.TimeGroup_Service;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageTimeGroupsController implements Initializable {

    private ArrayList<TimeGroup> arr;
    private static User user;
    @FXML
    protected ListView<String> myListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = Main.getUser();

        try {
            arr = TimeGroup_Service.getTimeGroupsByAdminId(user.getId());

            for (int i = 0; i < arr.toArray().length; i++) {
                myListView.getItems().add(arr.get(i).getName());
            }
        }catch (Exception e){}

        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                int index = myListView.getSelectionModel().getSelectedIndex();
                    viewTimeSlots(arr.get(index).getId());
            }
        });
    }

    @FXML
    protected void joinTimeGroup(ActionEvent e){
        showDialog(e);
    }

    @FXML
    protected void backToDashboard(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("Views/dashboard-view.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println("IO-Exception");
            e.printStackTrace();
        }
    }

    private static void showDialog(ActionEvent event) {
        // Create the dialog
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Login Dialog");
        dialog.setResizable(false);

        // Create UI elements
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #333333;");

        Label nameLabel = new Label("Name:");
        nameLabel.getStyleClass().add("labels"); // Apply CSS style class
        TextField nameTextField = new TextField();
        nameTextField.getStyleClass().add("text-field"); // Apply CSS style class
        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("labels"); // Apply CSS style class
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("text-field"); // Apply CSS style class

        Button closeButton = new Button("Close");
        closeButton.getStyleClass().add("dashboard-button"); // Apply CSS style class
        Button joinButton = new Button("Create");
        joinButton.getStyleClass().add("dashboard-button"); // Apply CSS style class

        // Close button action
        closeButton.setOnAction(e -> dialog.close());

        // Join button action
        joinButton.setOnAction(e -> {
            if (nameTextField.getText().equals("")) { alert("Username Field Cannot Be Empty"); return;}
            else if (passwordField.getText().equals("")) { alert("Password Field Cannot Be Empty"); return;}

            String name = nameTextField.getText();
            String password = passwordField.getText();

            try {
                TimeGroup_Service.insertTimeGroup(user.getId(), name, password);
                reload(event);
            } catch (Exception ex) {
                alert("Could Not Add");
                dialog.close();
                return;
            }
            dialog.close(); // Close the dialog after handling the action
        });

        // Add UI elements to grid
        grid.add(nameLabel, 0, 0);
        grid.add(nameTextField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        // Button box
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(closeButton, joinButton);

        // Add grid and buttons to scene
        grid.add(buttonBox, 0, 2, 2, 1);
        Scene dialogScene = new Scene(grid, 300, 150);
        dialogScene.setFill(Color.web("#333333")); // Set transparent background
        dialogScene.getStylesheets().add(Main.class.getResource("Styles/Style.css").toExternalForm()); // Add your CSS file
        dialog.setScene(dialogScene);

        // Show the dialog
        dialog.showAndWait(); // Show dialog and wait for it to be closed
    }

    private static void alert(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(message);
        String cssFile = Main.class.getResource("Styles/Style.css").toExternalForm();
        a.getDialogPane().getStylesheets().add(cssFile);
        a.show();
    }

    private static void reload(ActionEvent event){
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

    //TODO Modify for new page
    private void viewTimeSlots(int id){
        try {
            FXMLLoader f = new FXMLLoader(Main.class.getResource("Views/manageTimeSlots-view.fxml"));
            Parent root = f.load();
            ManageTimeSlotsController s = f.getController();
            s.setTimeGroup(id);
            Stage stage = Main.getStage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            //e.printStackTrace();
            System.out.println("Potato");
        }
    }
}

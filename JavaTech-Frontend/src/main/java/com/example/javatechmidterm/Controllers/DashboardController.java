package com.example.javatechmidterm.Controllers;

import com.example.javatechmidterm.Main;
import com.example.javatechmidterm.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    private User user;
    @FXML
    protected TextField usernameField, fullNameField, phoneNumberField;
    @FXML
    protected BorderPane bPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = Main.getUser();

        bPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.widthProperty().addListener((obs, oldVal, newVal) -> adjustScaleAndPadding());
                newScene.heightProperty().addListener((obs, oldVal, newVal) -> adjustScaleAndPadding());
            }
        });

        usernameField.setText(user.getUsername());
        fullNameField.setText(user.getFirstName() + " " + user.getLastName());
        phoneNumberField.setText(user.getPhoneNumber());
        usernameField.setEditable(false);
        fullNameField.setEditable(false);
        usernameField.setEditable(false);
    }

    @FXML
    protected void viewJoinedTimeSlots(ActionEvent event){
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
    protected void manageCreatedTimeSlots(ActionEvent event){

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

    private void adjustScaleAndPadding() {
        double width = bPane.getScene().getWidth();
        double height = bPane.getScene().getHeight();

        if(width > 600 && height > 400) {
            // Calculate scaling factors based on available space
            double scaleX = width / 600.0; // 400.0 is the original width of the VBox
            double scaleY = height / 400.0; // 250.0 is the original height of the VBox
            // Set scaling factors
            bPane.setScaleX(scaleX);
            bPane.setScaleY(scaleY);
        }
    }


    @FXML
    protected void manageAccount(ActionEvent event){

        try {
            Parent root = FXMLLoader.load(Main.class.getResource("Views/manageUser.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println("Potato");
        }
    }
}

package com.example.javatechmidterm.Controllers;

import com.example.javatechmidterm.Main;
import com.example.javatechmidterm.Services.User_Service;
import com.example.javatechmidterm.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    protected TextField usernameField;
    @FXML
    protected PasswordField passwordField;
    @FXML
    protected Button signInBtn, signUpBtn;
    @FXML
    protected VBox vbox;

    @FXML
    protected void signUp(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("Views/signUp-view.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println("IO-Exception");
        }
    }
    @FXML
    protected void signIn(ActionEvent ev){
        if (usernameField.getText().equals("")) { alert("Username Field Cannot Be Empty"); return;}
        else if (passwordField.getText().equals("")) { alert("Password Field Cannot Be Empty"); return;}

        try {
           User user = User_Service.authUser(usernameField.getText(), passwordField.getText());
            Main.setUser(user);
        }
        catch (Exception e){
            alert("Invalid User");
            return;
        }
        goToDashboard(ev);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add listeners to scene width and height
        vbox.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.widthProperty().addListener((obs, oldVal, newVal) -> adjustScaleAndPadding());
                newScene.heightProperty().addListener((obs, oldVal, newVal) -> adjustScaleAndPadding());
            }
        });
    }
    private void adjustScaleAndPadding() {
        double width = vbox.getScene().getWidth();
        double height = vbox.getScene().getHeight();

        if (width > 400 && height > 250) {
            // Calculate scaling factors based on available space
            double scaleX = width / 400; // 400.0 is the original width of the VBox
            double scaleY = height / 250; // 250.0 is the original height of the VBox
            // Set scaling factors
            vbox.setScaleX(scaleX);
            vbox.setScaleY(scaleY);
        }
    }

    private void alert(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(message);
        String cssFile = Main.class.getResource("Styles/Style.css").toExternalForm();
        a.getDialogPane().getStylesheets().add(cssFile);
        a.show();
    }

    private void goToDashboard(ActionEvent event){
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
}
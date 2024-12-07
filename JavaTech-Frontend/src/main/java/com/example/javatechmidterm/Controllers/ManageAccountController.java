package com.example.javatechmidterm.Controllers;

import com.example.javatechmidterm.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.example.javatechmidterm.Models.*;
import com.example.javatechmidterm.Services.*;
import javafx.stage.Stage;

public class ManageAccountController implements Initializable {

    private static User user;

    @FXML
    protected VBox vbox;
    @FXML
    protected TextField usernameField, firstNameField, lastNameField, phoneNumberField;
    @FXML
    protected PasswordField passwordField;


    public void setUser(User user) {
        this.user = user;
        usernameField.setText(user.getUsername());
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        phoneNumberField.setText(user.getPhoneNumber());
        passwordField.setText(user.getPassword());
    }

    @FXML
    protected void back(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("Views/dashboard-view.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println("IO-Exception");
            //e.printStackTrace();
        }
    }

    @FXML
    protected void update(ActionEvent event)  {
        try {
            User_Service.updateUser(
                    user.getId(),
                    usernameField.getText().equals("") ?
                            user.getUsername() : usernameField.getText(),
                    passwordField.getText().equals("") ?
                            user.getPassword() : passwordField.getText(),
                    firstNameField.getText().equals("") ?
                            user.getFirstName() : firstNameField.getText(),
                    lastNameField.getText().equals("") ?
                            user.getLastName() : lastNameField.getText(),
                    phoneNumberField.getText().equals("") ?
                            user.getPhoneNumber() : phoneNumberField.getText()
            );
            Main.setUser(User_Service.getUserById(user.getId()));
        } catch (SQLException e) {
            System.out.println("Batata");
        } catch (URISyntaxException e) {
            System.out.println("Batata");
        } catch (IOException e) {
            System.out.println("Batata");
        } catch (InterruptedException e) {
            System.out.println("Batata");
        }



        setUser(Main.getUser());
    }

    @FXML
    protected void deleteAccount(ActionEvent event)  {

        try {
            User_Service.deleteUser(Main.getUser().getId());
            Main.setUser(null);
        } catch (IOException e) {
            System.out.println("Batata");
        } catch (InterruptedException e) {
            System.out.println("Batata");
        } catch (SQLException e) {
            System.out.println("Batata");
        }

        try {
            Parent root = FXMLLoader.load(Main.class.getResource("Views/login-view.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println("IO-Exception");
            //e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUser(Main.getUser());

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
            double scaleX = width / 400.0; // 400.0 is the original width of the VBox
            double scaleY = height / 250.0; // 250.0 is the original height of the VBox
            // Set scaling factors
            vbox.setScaleX(scaleX);
            vbox.setScaleY(scaleY);
        }
    }
}



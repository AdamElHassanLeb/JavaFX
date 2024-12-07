package com.example.javatechmidterm.Controllers;

import com.example.javatechmidterm.Main;
import com.example.javatechmidterm.Services.User_Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    protected VBox vbox;
    @FXML
    protected TextField usernameField, firstNameField, lastNameField, phoneNumberField;
    @FXML
    protected PasswordField passwordField;

    @FXML
    protected void signUp(ActionEvent e) {
        if (usernameField.getText().equals("")) { alert("Username Field Cannot Be Empty"); return;
        }else if (passwordField.getText().equals("")) { alert("Password Field Cannot Be Empty"); return;
        } else if (firstNameField.getText().equals("")) { alert("First Name Field Cannot Be Empty"); return;
        } else if (lastNameField.getText().equals("")) { alert("Last Name Field Cannot Be Empty"); return;
        } else if (phoneNumberField.getText().equals("")) { alert("Phone Number Field Cannot Be Empty"); return;
        }

        int x = 0;
        try {
            x = User_Service.insertUser(usernameField.getText(), passwordField.getText(), firstNameField.getText(), lastNameField.getText(), phoneNumberField.getText());
        }
        catch (Exception ex){ alert("Could Not Add User");}

        if(x == 0){alert("Could Not Add User");}
        else{
            try {
                goToLoginWithParams(e, usernameField.getText(), passwordField.getText());
            }
            catch (Exception ex){alert("IO Error");}
        }
    }

    @FXML
    protected void backToSignIn(ActionEvent e){
        try {
            goToLogin(e);
        }catch (Exception ex){
            alert("Error");
        }
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
            double scaleX = width / 400.0; // 400.0 is the original width of the VBox
            double scaleY = height / 250.0; // 250.0 is the original height of the VBox
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


    protected void goToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("Views/login-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    protected void goToLoginWithParams(ActionEvent event, String username, String password) throws IOException {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/login-view.fxml"));
        Parent root = loader.load();
        LoginController lgc = loader.getController();
        lgc.usernameField.setText(username);
        lgc.passwordField.setText(password);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
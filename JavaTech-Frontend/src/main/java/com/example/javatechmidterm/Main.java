package com.example.javatechmidterm;
import com.example.javatechmidterm.Models.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    private static User user;
    private static Stage stager;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hjoz Ma3i");
        stage.setScene(scene);
        stage.show();
        stager = stage;

        stage.setOnCloseRequest(e -> {

        });
    }

    public static void main(String[] args) {launch();}

    public static User getUser() {
        return user;
    }

    public static void setUser(User usr) {
        user = usr;
    }

    public static Stage getStage(){
        return stager;
    }
}
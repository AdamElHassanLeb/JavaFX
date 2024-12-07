module com.example.javatechmidterm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires com.google.gson;


    opens com.example.javatechmidterm to javafx.fxml;
    exports com.example.javatechmidterm;
    exports com.example.javatechmidterm.Controllers;
    opens com.example.javatechmidterm.Controllers to javafx.fxml;
    opens com.example.javatechmidterm.Services to com.google.gson;
    opens com.example.javatechmidterm.Models to com.google.gson;


}
package com.example.javatechmidterm.Services;
import com.example.javatechmidterm.Main;
import com.example.javatechmidterm.Models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;

public class TimeGroup_Service {


    public static ArrayList<TimeGroup> getTimeGroups() throws IOException, InterruptedException, SQLException, IOException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timegroup/timegroups"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> getResponse =
                httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        if (getResponse.statusCode() != 200) {
            throw new SQLClientInfoException();
        }

        Gson gson = new Gson();
        Type timeGroupListType = new TypeToken<ArrayList<TimeGroup>>(){}.getType();
        ArrayList<TimeGroup> timeGroups = gson.fromJson(getResponse.body(), timeGroupListType);
        return timeGroups;
    }


    public static TimeGroup getTimeGroupById(int id) throws IOException, InterruptedException, SQLException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timegroup/timegroupById/" + id))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> getResponse =
                httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        if (getResponse.statusCode() != 200) {
            throw new SQLClientInfoException();
        }

        Gson gson = new Gson();
        TimeGroup timeGroup = gson.fromJson(getResponse.body(), TimeGroup.class);
        return timeGroup;
    }





    public static ArrayList<TimeGroup> getTimeGroupsByAdminId(int id) throws IOException, InterruptedException, SQLException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timegroup/timegroupsByAdmin/" + id))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> getResponse =
                httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        if (getResponse.statusCode() != 200) {
            throw new SQLClientInfoException();
        }

        Gson gson = new Gson();
        Type timeGroupListType = new TypeToken<ArrayList<TimeGroup>>(){}.getType();
        ArrayList<TimeGroup> timeGroups = gson.fromJson(getResponse.body(), timeGroupListType);
        return timeGroups;
    }

    public static int insertTimeGroup(int adminId, String name, String password) throws IOException, InterruptedException, SQLException {
        // Create an instance of TimeGroup with the provided admin ID, name, and password
        TimeGroup timeGroup = new TimeGroup();
        timeGroup.setAdminId(adminId);
        timeGroup.setName(name);
        timeGroup.setPassword(password);

        // Convert the TimeGroup object to JSON
        Gson gson = new Gson();
        String json = gson.toJson(timeGroup);

        // Create an HTTP POST request with the JSON payload
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timegroup/timegroup"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Send the request using HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (postResponse.statusCode() != 201) {
            return 1;
        }

        // If the request was successful, return 0 or any other appropriate success code
        return 0;
    }


    public static int updateTimeGroup(int id, int adminId, String name, String password) throws IOException, InterruptedException, SQLException {
        // Create an instance of TimeGroup with the provided data
        TimeGroup timeGroup = new TimeGroup();
        timeGroup.setId(id);
        timeGroup.setAdminId(adminId);
        timeGroup.setName(name);
        timeGroup.setPassword(password);

        // Convert the TimeGroup object to JSON
        Gson gson = new Gson();
        String json = gson.toJson(timeGroup);

        // Create an HTTP PUT request with the JSON payload
        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timegroup/timegroup"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Send the request using HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> putResponse = httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (putResponse.statusCode() != 200) {
            return 1;
        }

        // If the request was successful, return 0 or any other appropriate success code
        return 0;
    }


    public static int deleteTimeGroup(int id) throws IOException, InterruptedException, SQLException {
        // Create an HTTP DELETE request
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timegroup/timegroup/" + id))
                .DELETE()
                .build();

        // Send the request using HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> deleteResponse = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (deleteResponse.statusCode() != 200) {
            return 1;
        }

        // If the request was successful, return 0 or any other appropriate success code
        return 0;
    }


    public static TimeGroup getTimeGroupByNameAndPassword(String name, String password) throws IOException, InterruptedException, SQLException {
        // Create an instance of AuthJson with the provided name and password
        AuthJson authJson = new AuthJson();
        authJson.name = name;
        authJson.password = password;

        // Convert the AuthJson object to JSON
        Gson gson = new Gson();
        String json = gson.toJson(authJson);

        // Create an HTTP POST request with the JSON payload
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timegroup/timegroupByNameAndPassword"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Send the request using HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (postResponse.statusCode() != 200) {
            throw new SQLClientInfoException();
        }

        // Parse the JSON response body into a TimeGroup object
        TimeGroup timeGroup = gson.fromJson(postResponse.body(), TimeGroup.class);
        return timeGroup;
    }

    static class AuthJson{
        public AuthJson(){}
        public String name;
        public String password;
    }


}

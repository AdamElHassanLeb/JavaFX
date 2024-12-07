package com.example.javatechmidterm.Services;
import com.example.javatechmidterm.Main;
import com.example.javatechmidterm.Models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.ArrayList;

public class User_Service {


    public static ArrayList<User> getUsers() throws IOException, InterruptedException, SQLClientInfoException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/user/users"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> getResponse =
                httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        ArrayList<User> userList = new ArrayList<>();

        if (getResponse.statusCode() != 200)
            throw new SQLClientInfoException();

        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
        userList = gson.fromJson(getResponse.body(), userListType);
        return userList;
    }


    public static User getUserById(int id) throws IOException, InterruptedException, SQLException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/user/userById/" + id))
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
        User user = gson.fromJson(getResponse.body(), User.class);
        return user;
    }


    public static User getUserByUsername(String username) throws IOException, InterruptedException, SQLClientInfoException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/user/userByUsername/" + username))
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
        User user = gson.fromJson(getResponse.body(), User.class);
        return user;
    }


    public static int insertUser(String username, String password, String firstName, String lastName, String phoneNumber) throws SQLException, URISyntaxException, IOException, InterruptedException {

        User newUser = new User(null, username, password, firstName, lastName, phoneNumber);

        Gson gson = new Gson();

        String json = gson.toJson(newUser);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/user/user"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(json))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> postResponse =
                httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        if(postResponse.statusCode() != 200)
            return 1;

        return 0;
    }

    public static int updateUser(int id, String username, String password, String firstName, String lastName, String phoneNumber) throws SQLException, URISyntaxException, IOException, InterruptedException {
        User newUser = new User(id, username, password, firstName, lastName, phoneNumber);

        Gson gson = new Gson();

        String json = gson.toJson(newUser);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/user/user"))
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(json))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> postResponse =
                httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        if(postResponse.statusCode() != 200)
            return 1;

        return 0;
    }

    public static int deleteUser(int id) throws IOException, InterruptedException, SQLException {
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/user/user/" + id))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> deleteResponse =
                httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        if (deleteResponse.statusCode() == 200)
            return 0;

        return 1;
    }


    public static User authUser(String username, String password) throws SQLException, URISyntaxException, IOException, InterruptedException {

        AuthJson authJson = new AuthJson();
        authJson.username = username;
        authJson.password = password;

        Gson gson = new Gson();

        String json = gson.toJson(authJson);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/user/authUser"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(json))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> postResponse =
        httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        if(postResponse.statusCode() != 200)
            throw new SQLClientInfoException();

        User returnUser = gson.fromJson(postResponse.body(), User.class);

        return returnUser;
    }

    static class AuthJson{
        public AuthJson(){}
        public String username;
        public String password;
    }
}

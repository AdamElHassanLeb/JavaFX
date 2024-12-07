package com.example.javatechmidterm.Services;
import com.example.javatechmidterm.Models.*;
import com.example.javatechmidterm.Main;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.example.javatechmidterm.Utils.*;

import java.time.LocalDateTime;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.sql.*;
import java.util.ArrayList;

public class TimeSlot_Service {


    public static ArrayList<TimeSlot> getTimeSlots() throws IOException, InterruptedException, SQLException {
        // Create an HTTP GET request
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timeslot/timeslots"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        // Send the request using HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (getResponse.statusCode() != 200) {
            throw new SQLClientInfoException();
        }


        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        Type timeSlotListType = new TypeToken<ArrayList<TimeSlot>>(){}.getType();
        ArrayList<TimeSlot> timeSlots = gson.fromJson(getResponse.body(), timeSlotListType);

        // Return the ArrayList<TimeSlot>
        return timeSlots;
    }


    public static TimeSlot getTimeSlotById(int id) throws IOException, InterruptedException, SQLException {
        // Create an HTTP GET request
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timeslot/timeslotById/" + id))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        // Send the request using HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (getResponse.statusCode() != 200) {
            throw new SQLClientInfoException();
        }

        // Parse the JSON response body into a TimeSlot object using Gson
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        TimeSlot timeSlot = gson.fromJson(getResponse.body(), TimeSlot.class);

        // Return the TimeSlot object
        return timeSlot;
    }

    public static ArrayList<TimeSlot> getTimeSlotsByUserId(int id) throws SQLException, IOException, InterruptedException {
        // Create an HTTP GET request
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timeslot/timeslotsByUser/" + id))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        // Send the request using HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (getResponse.statusCode() != 200) {
            throw new SQLClientInfoException();
        }

        // Create Gson instance with custom adapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        // Parse the JSON response body into an ArrayList<TimeSlot> using Gson
        Type timeSlotListType = new TypeToken<ArrayList<TimeSlot>>(){}.getType();
        ArrayList<TimeSlot> timeSlots = gson.fromJson(getResponse.body(), timeSlotListType);

        // Return the ArrayList<TimeSlot>
        return timeSlots;
    }

    public static ArrayList<TimeSlot> getTimeSlotsByGroupId(int id) throws SQLException, IOException, InterruptedException {
        // Create an HTTP GET request
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timeslot/timeslotsByGroup/" + id))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        // Send the request using HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (getResponse.statusCode() != 200) {
            throw new SQLClientInfoException();
        }

        // Create Gson instance with custom adapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        // Parse the JSON response body into an ArrayList<TimeSlot> using Gson
        Type timeSlotListType = new TypeToken<ArrayList<TimeSlot>>(){}.getType();
        ArrayList<TimeSlot> timeSlots = gson.fromJson(getResponse.body(), timeSlotListType);

        // Return the ArrayList<TimeSlot>
        return timeSlots;
    }

    public static ArrayList<TimeSlot> getTimeSlotsByGroupAndMonth(int id, int monthNb) throws SQLException, IOException, InterruptedException {
        // Create an HTTP GET request
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timeslot/timelotsByGroupAndMonth/" + id + "/" + monthNb))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        // Send the request using HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (getResponse.statusCode() != 200) {
            throw new SQLClientInfoException();
        }

        // Create Gson instance with custom adapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        // Parse the JSON response body into an ArrayList<TimeSlot> using Gson
        Type timeSlotListType = new TypeToken<ArrayList<TimeSlot>>(){}.getType();
        ArrayList<TimeSlot> timeSlots = gson.fromJson(getResponse.body(), timeSlotListType);

        // Return the ArrayList<TimeSlot>
        return timeSlots;
    }



    public static ArrayList<TimeSlot> getTimeSlotsByGroupAndDay(int groupId, int yearNb, int monthNb, int dayNb) throws SQLException, IOException, InterruptedException {
        // Create an HTTP GET request
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timeslot/timeslotsByGroupAndDay/" + groupId + "/" + dayNb + "/" + monthNb + "/" + yearNb))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        // Send the request using HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (getResponse.statusCode() != 200) {
            throw new SQLClientInfoException();
        }

        // Create Gson instance with custom adapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        // Parse the JSON response body into an ArrayList<TimeSlot> using Gson
        Type timeSlotListType = new TypeToken<ArrayList<TimeSlot>>(){}.getType();
        ArrayList<TimeSlot> timeSlots = gson.fromJson(getResponse.body(), timeSlotListType);

        // Return the ArrayList<TimeSlot>
        return timeSlots;
    }

    public static int insertTimeSlot(int groupId, LocalDateTime startTime, LocalDateTime endTime, boolean isReserved) throws SQLException, IOException, InterruptedException {
        // Create a TimeSlot object with no arguments
        TimeSlot timeSlot = new TimeSlot();

        // Set properties using setters
        timeSlot.setGroupId(groupId);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);
        timeSlot.setReserved(isReserved);

        // Serialize the TimeSlot object to JSON using Gson with custom adapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        String json = gson.toJson(timeSlot);

        // Create an HTTP POST request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timeslot/timeslot"))
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

        // Return the success code
        return 0;
    }

    public static int updateTimeSlot(int id, Integer user_id, int groupId, LocalDateTime startTime, LocalDateTime endTime, boolean isReserved) throws SQLException, IOException, InterruptedException {
        // Create a TimeSlot object with the provided parameters
        TimeSlot timeSlot = new TimeSlot(id, user_id, groupId, startTime, endTime, isReserved);

        // Serialize the TimeSlot object to JSON using Gson with custom adapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        String json = gson.toJson(timeSlot);

        // Create an HTTP PUT request
        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timeslot/timeslot"))
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

        // Return the success code
        return 0;
    }

    public static int reserveTimeSlot(int id, int user_id) throws SQLException, IOException, InterruptedException {
        // Retrieve the time slot by its ID
        TimeSlot timeSlot = getTimeSlotById(id);

        // Check if the time slot is already reserved
        if (timeSlot.isReserved()) {
            // If it's already reserved, return 1
            return 1;
        }

        // Set the user ID and reserved status
        timeSlot.setUserId(user_id);
        timeSlot.setReserved(true);

        // Update the time slot
        return updateTimeSlot(timeSlot.getId(), user_id, timeSlot.getGroupId(), timeSlot.getStartTime(), timeSlot.getEndTime(), true);
    }



    public static int removeTimeSlotReservation(int id, int user_id) throws SQLException, IOException, InterruptedException {
        // Retrieve the time slot by its ID
        TimeSlot timeSlot = getTimeSlotById(id);

        // Check if the time slot is reserved by the specified user
        if (timeSlot.isReserved() && timeSlot.getUserId() == user_id) {
            // Remove the reservation
            timeSlot.setUserId(null);
            timeSlot.setReserved(false);

            // Update the time slot
            return updateTimeSlot(timeSlot.getId(), null, timeSlot.getGroupId(), timeSlot.getStartTime(), timeSlot.getEndTime(), false);
        }
            // If the time slot is not reserved by the specified user, return 1 to indicate failure
            return 1;
    }


    public static int deleteTimeSlot(int id) throws SQLException, IOException, InterruptedException {
        // Create the DELETE request
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/timeslot/timeslot/" + id))
                .DELETE()
                .build();

        // Send the DELETE request
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> deleteResponse = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (deleteResponse.statusCode() != 200) {
            // If the request was not successful, return 1 to indicate failure
            return 1;
        }

        // If the request was successful, return 0 to indicate success
        return 0;
    }


}

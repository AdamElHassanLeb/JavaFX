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

public class GroupMember_Service {

    public static ArrayList<GroupMember> getGroupMembers() throws InterruptedException, IOException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/groupMember/groupMembers"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> getResponse =
                httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        if (getResponse.statusCode() != 200) {
            // Handle non-200 status code
            System.out.println("Failed to fetch group members. Status code: " + getResponse.statusCode());
            return new ArrayList<>(); // Return an empty list or handle error as needed
        }

        Gson gson = new Gson();
        Type groupMemberListType = new TypeToken<ArrayList<GroupMember>>(){}.getType();
        ArrayList<GroupMember> groupMembers = gson.fromJson(getResponse.body(), groupMemberListType);
        return groupMembers;
    }


    public static ArrayList<GroupMember> getGroupMembersById(int id) throws IOException, InterruptedException, SQLException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/groupMember/groupMemberById/" + id))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> getResponse =
                httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        ArrayList<GroupMember> groupMembers = new ArrayList<>();

        if (getResponse.statusCode() == 200) {
            Gson gson = new Gson();
            GroupMember groupMember = gson.fromJson(getResponse.body(), GroupMember.class);
            groupMembers.add(groupMember);
        } else {
            throw new SQLClientInfoException();
        }

        return groupMembers;
    }


    public static ArrayList<GroupMember> getGroupMembersByGroupId(int id) throws IOException, InterruptedException, SQLException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/groupMember/groupMembersByGroup/" + id))
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
        Type groupMemberListType = new TypeToken<ArrayList<GroupMember>>(){}.getType();
        ArrayList<GroupMember> groupMembers = gson.fromJson(getResponse.body(), groupMemberListType);
        return groupMembers;
    }


    public static ArrayList<GroupMember> getGroupMembersByUserId(int id) throws IOException, InterruptedException, SQLException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/groupMember/groupMembersByUser/" + id))
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
        Type groupMemberListType = new TypeToken<ArrayList<GroupMember>>(){}.getType();
        ArrayList<GroupMember> groupMembers = gson.fromJson(getResponse.body(), groupMemberListType);
        return groupMembers;
    }


    public static int insertGroupMember(int userId, int groupId) throws SQLException, IOException, InterruptedException {

        GroupMember member = new GroupMember();
        member.setUserId(userId);
        member.setGroupId(groupId);

        String json = new Gson().toJson(member);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/groupMember/groupMember"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> postResponse =
                httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (postResponse.statusCode() != 201) {
            // If the request was not successful, throw an exception or return an appropriate error code
            return 1;
        }

        // If the request was successful, return 0 or any other appropriate success code
        return 0;
    }

    public static int updateGroupMember(int id, int userId, int groupId) throws SQLException, IOException, InterruptedException {

        GroupMember member = new GroupMember(id, userId, groupId);

        String json = new Gson().toJson(member);

        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/groupMember/groupMember"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> putResponse =
                httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (putResponse.statusCode() != 200) {
            // If the request was not successful, throw an exception or return an appropriate error code
            return 1;
        }

        // If the request was successful, return 0 or any other appropriate success code
        return 0;
    }


    public static int deleteGroupMember(int id) throws IOException, InterruptedException, SQLException {
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/groupMember/groupMember/" + id))
                .DELETE()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> deleteResponse =
                httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        // Check the status code of the response
        if (deleteResponse.statusCode() != 200) {
            // If the request was not successful, throw an exception or return an appropriate error code
            return 1;
        }

        // If the request was successful, return 0 or any other appropriate success code
        return 0;
    }

}

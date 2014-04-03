package com.ehc.angel.api;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

/**
 * Created by venkatesh on 3/22/14.
 */
public class MainClass {

    User[] users = null;

    public static void main(String... args) {
        getUsers();
    }

    private static void getUsers() {
        try {
            HttpResponse<JsonNode> request = Unirest.get("https://api.angel.co/1/startups/batch?ids=51,100")
                    .asJson();
            JsonNode node = request.getBody();
            System.out.println(node.isArray());
            System.out.println(node.toString());
            System.out.println(request);
            Gson gson = new Gson();
            User[] users = gson.fromJson(node.toString(), User[].class);
            System.out.println("size:" + users.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

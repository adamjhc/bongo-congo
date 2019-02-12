package com.knightlore.server.test;

import com.google.gson.Gson;
import com.knightlore.server.database.Connection;
import com.knightlore.server.database.model.*;

import java.util.Calendar;
import java.util.Optional;

public class Test {



    public static void main(String[] args){

        Connection.connect();


        RegistrationKey token = new RegistrationKey();
        token.where(new Condition("description", "=", "yeet"));
        Optional<Model> mine = token.first();

        System.out.println(mine.get().getAttribute("key"));

//        ApiKey key = new ApiKey("mykey");
//
//        Gson gson = new Gson();
//        String json = gson.toJson(key);
//
//
//        ApiKey newUser = gson.fromJson(json, ApiKey.class);
//
//
//        Sendable send = new Sendable();
//        send.setData(json);
//
//        System.out.println(json);
//        System.out.println(send.getData());

    }
}

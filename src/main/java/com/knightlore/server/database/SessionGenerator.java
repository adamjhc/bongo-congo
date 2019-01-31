package com.knightlore.server.database;

import org.json.JSONException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class SessionGenerator {

    java.sql.Connection con = Connection.getConnection();

    public String generateActivationKey(){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        String toReturn = "";
        for(int i=0; i<32; i++){
            int randomNum = ThreadLocalRandom.current().nextInt(1, letters.length());
            toReturn += letters.charAt(randomNum);
        }

        System.out.println(toReturn);
        return toReturn;
    }


    public Optional<String> getToken(String key) throws JSONException{


        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM registration_keys WHERE `key`=? LIMIT 1");
            stmt.setString(1, key);
            ResultSet results = stmt.executeQuery();


            if(results.next() == false) {
                // No key found
                return Optional.empty();
            }

            int registrationKeyID = results.getInt("id");

            // Create new key!
            String newKey = generateActivationKey();


            stmt = con.prepareStatement("INSERT INTO session_tokens " +
                    "(registration_key_id, token, ip, created_at, last_used_at, expires_at)" +
                    "VALUES (?, ?, ?, NOW(), NOW(), ?);");

            stmt.setInt(1, registrationKeyID);
            stmt.setString(2, newKey);
            stmt.setString(3, "192.168.0.1");

            Date now = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(now);

            Date currentDatePlusOne = c.getTime();

            c.add(Calendar.DATE, 1);

            java.sql.Date date = new java.sql.Date(currentDatePlusOne.getTime());

            stmt.setDate(4, date);
            stmt.execute();

            return Optional.of(newKey);

        }catch (SQLException e){
            System.out.println(e);
        }

        return Optional.empty();

    }
}

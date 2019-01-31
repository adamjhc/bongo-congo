package com.knightlore.server.database;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authenticate {

    java.sql.Connection con = Connection.getConnection();



    public JSONObject checkToken(String token) throws JSONException{

        JSONObject response = new JSONObject();

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM activation_tokens WHERE token=? LIMIT 1");
            stmt.setString(1, token);
            ResultSet results = stmt.executeQuery();

            if(results.next() == false) {
                // No key found
                response.put("success", "false");
                response.put("reason", "token_not_found");
                return response;
            }
            // TODO update last used

            response.put("success", "true");
            return response;

        }catch (SQLException e){
            System.out.println(e);
        }

        response.put("success", "false");
        response.put("reason", "unknown");
        return response;

    }
}

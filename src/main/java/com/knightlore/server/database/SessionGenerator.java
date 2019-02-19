package com.knightlore.server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class SessionGenerator {

    java.sql.Connection con = Connection.getConnection();

    public static String generateActivationKey(){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        String toReturn = "";
        for(int i=0; i<32; i++){
            int randomNum = ThreadLocalRandom.current().nextInt(1, letters.length());
            toReturn += letters.charAt(randomNum);
        }

        System.out.println(toReturn);
        return toReturn;
    }

}

package com.knightlore.server.test;

import com.google.gson.Gson;
import com.knightlore.game.entity.Coordinate;
import com.knightlore.game.map.Location;
import com.knightlore.game.util.Map;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.Sendable;

public class Test {



    public static void main(String[] args){
        Location location = new Location(1,1,1);

        Coordinate coordinate = Map.getCoordinateFromTile(location);

        System.out.println(coordinate);

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

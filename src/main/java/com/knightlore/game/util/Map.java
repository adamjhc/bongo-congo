package com.knightlore.game.util;

import com.knightlore.game.entity.Coordinate;
import com.knightlore.game.map.Location;

public class Map {

    /**
     * Retrieves coordinate at the centre of provided tile location
     * @param location
     * @return
     */
    public static Coordinate getCoordinateFromTile(Location location){
        float x = (float) (location.x * 100) + 50;
        float y = (float) (location.y * 100);
        float z = (float) (location.z * 100) + 50;

        return new Coordinate(x,y,z);
    }


}

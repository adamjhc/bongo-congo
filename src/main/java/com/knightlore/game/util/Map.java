package com.knightlore.game.util;


import com.knightlore.game.math.Vector3f;
import com.knightlore.game.math.Vector3i;

public class Map {

    /**
     * Retrieves coordinate at the centre of provided tile location
     * @param location
     * @return
     */
    public static Vector3f getCoordinateFromTile(Vector3i location){
        float x = (float) (location.x * 100) + 50;
        float y = (float) (location.y * 100);
        float z = (float) (location.z * 100) + 50;

        return new Vector3f(x,y,z);
    }


}

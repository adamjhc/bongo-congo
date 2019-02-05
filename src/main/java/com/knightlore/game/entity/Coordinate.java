package com.knightlore.game.entity;

public class Coordinate {

    float x;
    float y;
    float z;

    public Coordinate(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return this.x + " " + this.y + " " + this.z;
    }
}

package com.knightlore.game.model.map;

public class TileProperty {

    public boolean hazard;
    public boolean goal;
    public boolean climbable;
    public boolean solid;


    public TileProperty(){
        this.hazard = false;
        this.goal = false;
        this.climbable = true;
        this.solid = true;
    }

}

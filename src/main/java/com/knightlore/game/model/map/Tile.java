package com.knightlore.game.model.map;

public class Tile {

    boolean hazard;
    boolean goal;
    boolean climbable;
    boolean solid;
    boolean empty;

    int tileX;
    int tileY;
    int tileZ;

    public Tile(TileProperty property){
        this.hazard = property.hazard;
        this.goal = property.goal;
        this.climbable = property.climbable;
        this.solid = property.solid;
        this.empty = false;
    }

    // Generate empty
    public Tile(){
        this.hazard = false;
        this.goal = false;
        this.climbable = false;
        this.solid = false;
        this.empty = true;
    }

    public void setHazard(boolean hazard) {
        this.hazard = hazard;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    public void setClimbable(boolean climbable) {
        this.climbable = climbable;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isHazard() {
        return hazard;
    }

    public boolean isGoal() {
        return goal;
    }

    public boolean isClimbable() {
        return climbable;
    }

    public boolean isSolid() {
        return solid;
    }




}

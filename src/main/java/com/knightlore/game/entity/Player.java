package com.knightlore.game.entity;

import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.map.Map;
import com.knightlore.game.map.Tile;
import com.knightlore.game.util.CoordinateUtils;
import java.util.UUID;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Player extends Entity {

    private static int inc = 0;
    private int lives;
    private int score;
    private boolean fallFlag = false;
    private PlayerState playerState;
    private String associatedSession;

    /**
     * Constructor for a new Player object
     */
    public Player() {
        id = inc;
        inc++;

        direction = Direction.SOUTH;
        position = new Vector3f(1, 1, 0);
        playerState = PlayerState.IDLE;

        lives = 3;
        score = 0;
    }

    public Player(String sessionID) {
        this();
        this.associatedSession = sessionID;
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }

    @Override
    void update() {}

    /**
     * The main method called in the game loop. Continuously checks for collision events with
     * specific tiles such as blocking or hazards. Also allows the player to climb up layers of
     * the map.
     *
     * @param oldPos The position of the player before collision check update
     * @param newPos The potential position of the player after collision check update
     * @param map The current map being played
     * @author Jacqueline Henes
     */
    public void update(Vector3f oldPos, Vector3f newPos, Map map) {
        Vector3i coords = CoordinateUtils.getTileCoord(setPadding(newPos));

        try {
            Tile newTile = map.getTile(coords);

            if (!newTile.isWalkable() && !newTile.isClimbable()) { // Checks if tile is an air tile
                if (fallFlag) {
                    newPos.z -= 1;
                    setPosition(newPos);
                    System.out.println("Dead"); //TODO: replace this with death state/reset
                    reset();                    //TODO: needs some sort of pause/animation here too
                 } else {
                    fallFlag = true;
                    newPos.z -= 1;
                    setPosition(newPos);
                }

            } else if (!newTile.isWalkable()) { // Checks if tile is a blocking tile
                setPosition(oldPos);

            } else { // Sets new position
                if (GameConnection.instance != null) {
                    GameConnection.instance.updatePosition(position);
                }
                fallFlag = false;
                setPosition(newPos);
            }

            if (newTile.isClimbable()) { // Checks for climbable tile
                newPos.z += 1;
                coords = CoordinateUtils.getTileCoord(setPadding(newPos));
                newTile = map.getTile(coords);
                if (newTile.isWalkable()) { // Checks if the tile above climbable tile is accessible
                    fallFlag = false;
                    setPosition(newPos);
                } else {
                    setPosition(oldPos);
                }
            }

            // catches SW and SE edges    catches NE and NW edges
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            setPosition(oldPos);
        }
    }

    /**
     * Returns the coordinates of the player plus padding to give the look of
     * actual collisions in game.
     *
     * @param pos The position to be padded
     * @return The padded coordinates of the player
     * @author Jacqueline Henes
     */
    private Vector3f setPadding(Vector3f pos) {
        Vector3f padded = new Vector3f();
        direction.getNormalisedDirection().mul(0.4f, padded);
        pos.add(padded, padded);
        return padded;
    }

    private void reset() {
        lives -= 1;
        if (lives <= 0) {
            System.out.println("Lost all lives");
        }
        setPosition(new Vector3f(1,1,0)); // TODO: need a 'start position' variable to use here
        setDirection(Direction.SOUTH);
        fallFlag = false;
    }


    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }
}

package com.knightlore.server.database.model;


import java.sql.Types;

public class GamePlayer extends Model{

    public static GamePlayer instance = new GamePlayer();

    public GamePlayer(){
        this.table = "game_players";
        this.attributes = new AttributeBag();
        this.attributes.addAttribute("id", new Attribute(Types.INTEGER, null, true));
        this.attributes.addAttribute("session_id", new Attribute(Types.INTEGER, null));
        this.attributes.addAttribute("score", new Attribute(Types.INTEGER, null));
        this.attributes.addAttribute("created_at", new Attribute(Types.TIMESTAMP, null));
        this.attributes.addAttribute("updated_at", new Attribute(Types.TIMESTAMP, null));
        this.attributes.addAttribute("game_id", new Attribute(Types.INTEGER, null));

        this.identifier = "id";
    }

    public Model createNewInstance() {
        return new GamePlayer();
    }

}

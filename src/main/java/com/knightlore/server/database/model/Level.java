package com.knightlore.server.database.model;


import com.google.gson.Gson;

import java.sql.Types;
import java.util.Optional;

public class Level extends Model{

    public static Level instance = new Level();

    public Level(){
        this.table = "levels";
        this.attributes = new AttributeBag();
        this.attributes.addAttribute("id", new Attribute(Types.INTEGER, null, true));
        this.attributes.addAttribute("created_by", new Attribute(Types.INTEGER, null));
        this.attributes.addAttribute("uuid", new Attribute(Types.VARCHAR, null));
        this.attributes.addAttribute("name", new Attribute(Types.VARCHAR, null));
        this.attributes.addAttribute("data", new Attribute(Types.LONGNVARCHAR, null));

        this.identifier = "id";
    }

    public Model createNewInstance() {
        return new Level();
    }

    public com.knightlore.game.Level getModelLevel(){
        String levelData = (String) this.getAttribute("data");

        Gson gson = new Gson();

        return gson.fromJson(levelData, com.knightlore.game.Level.class);
    }
}

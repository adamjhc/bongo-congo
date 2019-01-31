package com.knightlore.server.database.model;
import java.sql.Types;

public class Venue extends Model{

    public static Venue instance = new Venue();

    public Venue(){
        this.createStatement = "create table Venue( vid SERIAL PRIMARY KEY, name VARCHAR(128) NOT NULL UNIQUE, cost int NOT NULL CHECK(cost > 0) )";
        this.table = "Venue";
        this.attributes = new AttributeBag();
        this.attributes.addAttribute("vid", new Attribute(Types.INTEGER, null, true));
        this.attributes.addAttribute("name", new Attribute(Types.VARCHAR, null));
        this.attributes.addAttribute("cost", new Attribute(Types.INTEGER, null));

        this.identifier = "vid";
    }


    public Model createNewInstance() {
        return new Venue();
    }
}

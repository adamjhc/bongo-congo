package com.knightlore.server.database.model;


import java.sql.Types;

public class Menu extends Model{

    public static Menu instance = new Menu();

    public Menu(){
        this.createStatement = "create table Menu( mid SERIAL PRIMARY KEY, description VARCHAR(1024) NOT NULL, cost INT NOT NULL CHECK(cost > 0) )";
        this.table = "Menu";
        this.attributes = new AttributeBag();
        this.attributes.addAttribute("mid", new Attribute(Types.INTEGER, null, true));
        this.attributes.addAttribute("description", new Attribute(Types.VARCHAR, null));
        this.attributes.addAttribute("cost", new Attribute(Types.INTEGER, null));

        this.identifier = "mid";
    }

    public Model createNewInstance() {
        return new Menu();
    }


    public Party party(){
        // Select all venues with that ID
        Party party = new Party();
        party.where(new Condition("mid", "=", this.getAttribute("mid")));
        return party;
    }

}

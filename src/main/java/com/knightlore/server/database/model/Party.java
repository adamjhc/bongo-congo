package com.knightlore.server.database.model;


import java.sql.Types;

public class Party extends Model{

    public static Party instance = new Party();

    public Party(){
        this.createStatement = "create table Party( pid SERIAL PRIMARY KEY, name varchar(128) NOT NULL, mid INT NULL REFERENCES Menu(mid), eid INT NULL REFERENCES Entertainment(eid), vid INT NULL REFERENCES Venue(vid), price INT NULL CHECK(price > 0), timing TIMESTAMP NULL, guestcount INT NOT NULL CHECK(guestcount > 0) )";
        this.table = "Party";
        this.attributes = new AttributeBag();
        this.attributes.addAttribute("pid", new Attribute(Types.INTEGER, null, true));
        this.attributes.addAttribute("name", new Attribute(Types.VARCHAR, null));
        this.attributes.addAttribute("timing", new Attribute(Types.TIMESTAMP, null));
        this.attributes.addAttribute("price", new Attribute(Types.INTEGER, null));
        this.attributes.addAttribute("guestcount", new Attribute(Types.INTEGER, null));


        this.attributes.addAttribute("eid", new Attribute(Types.INTEGER, null));
        this.attributes.addAttribute("vid", new Attribute(Types.INTEGER, null));
        this.attributes.addAttribute("mid", new Attribute(Types.INTEGER, null));

        this.identifier = "pid";
    }

    public Model createNewInstance() {
        return new Party();
    }


    public Venue venue(){
        Venue venue = Venue.instance;
        venue.find(this.getAttribute("vid"));
        return venue;
    }

    public Menu menu(){
        Menu menu = Menu.instance;
        menu.find(this.getAttribute("mid"));
        return menu;
    }

    public Entertainment entertainment(){
        Entertainment entertainment = Entertainment.instance;
        entertainment.find(this.getAttribute("eid"));
        return entertainment;
    }

    public int calculateCost(){
        return (int) this.venue().getAttribute("cost") + (int)this.entertainment().getAttribute("cost") + ((int) this.menu().getAttribute("cost") * (int) this.getAttribute("guestcount"));
    }
}

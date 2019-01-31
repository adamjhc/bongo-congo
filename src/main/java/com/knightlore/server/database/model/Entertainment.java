package com.knightlore.server.database.model;

import java.sql.Types;

public class Entertainment extends Model{

    public static Entertainment instance = new Entertainment();

    public Entertainment(){
        this.createStatement = "create table Entertainment( eid SERIAL PRIMARY KEY, description VARCHAR(1024) NOT NULL, cost INT NOT NULL CHECK(cost > 0) )";

        this.table = "Entertainment";
        this.attributes = new AttributeBag();
        this.attributes.addAttribute("eid", new Attribute(Types.INTEGER, null, true));
        this.attributes.addAttribute("description", new Attribute(Types.VARCHAR, null));
        this.attributes.addAttribute("cost", new Attribute(Types.INTEGER, null));

        this.identifier = "vid";
    }

    public Model createNewInstance() {
        return new Entertainment();
    }

}

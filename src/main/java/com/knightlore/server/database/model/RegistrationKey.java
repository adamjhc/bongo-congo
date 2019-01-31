package com.knightlore.server.database.model;


import java.sql.Types;

public class RegistrationKey extends Model{

    public static RegistrationKey instance = new RegistrationKey();

    public RegistrationKey(){
        this.table = "registration_keys";
        this.attributes = new AttributeBag();
        this.attributes.addAttribute("id", new Attribute(Types.INTEGER, null, true));
        this.attributes.addAttribute("key", new Attribute(Types.VARCHAR, null));
        this.attributes.addAttribute("description", new Attribute(Types.VARCHAR, null));

        this.identifier = "id";
    }

    public Model createNewInstance() {
        return new RegistrationKey();
    }

}

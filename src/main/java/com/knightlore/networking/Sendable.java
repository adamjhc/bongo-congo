package com.knightlore.networking;

import java.io.Serializable;
import java.util.UUID;

public class Sendable implements Serializable{

    private String function;
    private String uuid;
    private String data;
    public boolean success;

    public String getUuid() {
        return uuid;
    }

    public boolean hasUUID(){
        if(this.uuid.equals("")){
            return false;
        }

        return true;
    }

    public void setUuid() {
        this.uuid = UUID.randomUUID().toString();
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Sendable makeResponse(){
        Sendable toReturn = new Sendable();
        toReturn.uuid = this.uuid;

        return toReturn;
    }



}

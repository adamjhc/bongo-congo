package com.knightlore.networking;


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ListGameObject {

    UUID uuid;
    InetAddress ip;
    int port;

    ArrayList<String> usernames;

    String name;

    public ListGameObject(UUID uuid, InetAddress ip, int port) {
        this.uuid = uuid;
        this.ip = ip;
        this.port = port;
    }

    public UUID getUuid() {
        return uuid;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

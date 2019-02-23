package com.knightlore.networking;


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ListGameObject {

    UUID uuid;
    InetAddress ip;
    ArrayList<String> connectedSessions;
    int port;

    public ListGameObject(UUID uuid, InetAddress ip, int port, ArrayList<String> connectedSessions) {
        this.uuid = uuid;
        this.ip = ip;
        this.port = port;
        this.connectedSessions = connectedSessions;
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

    public int getConnectedSessionCount(){
        return this.connectedSessions.size();
    }

}

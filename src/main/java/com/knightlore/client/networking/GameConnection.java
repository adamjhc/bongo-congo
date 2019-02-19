package com.knightlore.client.networking;

import com.google.gson.Gson;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.client.networking.backend.ResponseHandler;
import com.knightlore.client.networking.backend.responsehandlers.game.GameRegister;
import com.knightlore.client.networking.backend.responsehandlers.server.GameRequest;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.Sendable;

import java.io.IOException;
import java.util.Optional;

public class GameConnection {

    public static GameConnection instance;

    Optional<String> sessionKey;
    boolean authenticated = false;
    Client client;


    // Generate new key
    public GameConnection(Client client) {
        this.client = client;
        this.sessionKey = Optional.empty();
    }

    // Key already validated
    public GameConnection(Client client, String sessionKey) {
        this.client = client;
        this.sessionKey = Optional.of(sessionKey);
    }

    public boolean ready(){
        return this.client.ready;
    }

    public void close() throws IOException{
        this.client.close();
    }

    public void startGame(){
        // Build up get session string
        Sendable sendable = new Sendable();
        sendable.setUuid();
        sendable.setFunction("game_start");

        Gson gson = new Gson();

        com.knightlore.networking.GameRequest request = new com.knightlore.networking.GameRequest();
        sendable.setData(gson.toJson(request));

        // Specify handler
        System.out.println("SENDING " + sendable.getData());

        try{
            client.dos.writeObject(sendable);
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public void register(String sessionKey){
        // Build up get session string
        Sendable sendable = new Sendable();
        sendable.setUuid();
        sendable.setFunction("register");

        Gson gson = new Gson();

        ApiKey key = new ApiKey(sessionKey);
        sendable.setData(gson.toJson(key));

        // Specify handler
        System.out.println("SENDING " + sendable.getData());
        ResponseHandler.waiting.put(sendable.getUuid(), new GameRegister());


        try{
            client.dos.writeObject(sendable);
        }catch(IOException e){
            System.out.println(e);
        }
    }

    // Run code after a connection to the game server has been successfully made
    public void gameConnectionMade(){

    }

    // Game connection was unable to be established
    public void gameConnectionFailed(){

    }
}
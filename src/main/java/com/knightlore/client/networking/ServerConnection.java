package com.knightlore.client.networking;

import com.google.gson.Gson;
import com.knightlore.client.exceptions.ClientAlreadyAuthenticatedException;
import com.knightlore.client.exceptions.ConfigItemNotFoundException;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.client.networking.backend.ResponseHandler;
import com.knightlore.client.networking.backend.responsehandlers.server.GameList;
import com.knightlore.client.networking.backend.responsehandlers.server.GameRequest;
import com.knightlore.client.networking.backend.responsehandlers.server.SessionKey;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.Sendable;
import com.knightlore.util.Config;

import java.io.IOException;
import java.util.Optional;

public class ServerConnection {

    public static ServerConnection instance;

    Optional<String> sessionKey;
    boolean authenticated = false;
    Client client;


    // Generate new key
    public ServerConnection(Client client) {
        this.client = client;
        this.sessionKey = Optional.empty();
    }

    // Key already validated
    public ServerConnection(Client client, String sessionKey) {
        this.client = client;
        this.sessionKey = Optional.of(sessionKey);
    }

    // Manually overwrite
    public void setSessionKey(String sessionKey){
        this.sessionKey = Optional.of(sessionKey);
    }

    public void auth() throws ClientAlreadyAuthenticatedException, IOException, ConfigItemNotFoundException{
        // Check if already authenticated
        if(authenticated){
            throw new ClientAlreadyAuthenticatedException();
        }

        // Check for api key in config
        Optional<String> apikey = Config.apiKey();

        if(!apikey.isPresent()){
            throw new ConfigItemNotFoundException();
        }

        // Build up get session string
        Sendable sendable = new Sendable();
        sendable.setUuid();
        sendable.setFunction("get_session_token");

        Gson gson = new Gson();

        ApiKey myKey = new ApiKey(apikey.get());
        sendable.setData(gson.toJson(myKey));

        ResponseHandler.waiting.put(sendable.getUuid(), new SessionKey());

        System.out.println("SENDING" + sendable.getData());
        client.dos.writeObject(sendable);

    }

    public void authSuccess(String sessionKey){
        // Update local variables
        this.sessionKey = Optional.of(sessionKey);
        this.authenticated = true;


        // Listeners for auth success
        System.out.println("SUCCESSFUL");
    }

    public void authFail(){
        // Listeners for auth fail
        System.out.println("FAILIURE");
    }

    public boolean ready(){
        return this.client.ready;
    }

    public boolean isAuthenticated(){
        return this.authenticated;
    }

    public void close() throws IOException{
        this.client.close();
    }

    public void requestGame(){
        if(this.authenticated){
            // Build up get session string
            Sendable sendable = new Sendable();
            sendable.setUuid();
            sendable.setFunction("request_game");

            Gson gson = new Gson();

            com.knightlore.networking.GameRequest request = new com.knightlore.networking.GameRequest();
            sendable.setData(gson.toJson(request));

            // Specify handler
            ResponseHandler.waiting.put(sendable.getUuid(), new GameRequest());

            System.out.println("SENDING" + sendable.getData());

            try{
                client.dos.writeObject(sendable);
            }catch(IOException e){
                System.out.println(e);
            }
        }
    }

    public void listGames(){
        if(this.authenticated){
            // Build up get session string
            Sendable sendable = new Sendable();
            sendable.setUuid();
            sendable.setFunction("list_games");


            // Specify handler
            ResponseHandler.waiting.put(sendable.getUuid(), new GameList());


            try{
                client.dos.writeObject(sendable);
            }catch(IOException e){
                System.out.println(e);
            }
        }
    }

    public Optional<String> getSessionKey() {
        return sessionKey;
    }
}

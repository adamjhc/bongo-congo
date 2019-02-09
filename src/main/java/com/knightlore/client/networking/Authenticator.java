package com.knightlore.client.networking;

import com.google.gson.Gson;
import com.knightlore.client.exceptions.ClientAlreadyAuthenticatedException;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.client.networking.backend.ResponseHandler;
import com.knightlore.client.networking.backend.ResponseHandlers.SessionKey;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.Sendable;

import java.io.IOException;
import java.util.Optional;

public class Authenticator {

    public static Authenticator instance;

    Optional<String> sessionKey;
    boolean authenticated = false;
    Client client;


    // Generate new key
    public Authenticator(Client client) {
        this.client = client;
        this.sessionKey = Optional.empty();
    }

    // Key already validated
    public Authenticator(Client client, String sessionKey) {
        this.client = client;
        this.sessionKey = Optional.of(sessionKey);
    }

    // Manually overwrite
    public void setSessionKey(String sessionKey){
        this.sessionKey = Optional.of(sessionKey);
    }

    public void auth() throws ClientAlreadyAuthenticatedException, IOException{
        // Check if already authenticated
        if(authenticated){
            throw new ClientAlreadyAuthenticatedException();
        }

        // Build up get session string
        Sendable sendable = new Sendable();
        sendable.setUuid();
        sendable.setFunction("get_session_token");

        Gson gson = new Gson();

        ApiKey myKey = new ApiKey("MY Keys");
        sendable.setData(gson.toJson(myKey));

        ResponseHandler.waiting.put(sendable.getUuid(), new SessionKey());

        System.out.println("SENDING" + sendable.getData());
        client.dos.writeObject(sendable);

        this.authenticated = true;
        this.sessionKey = Optional.of("Key");
    }

    public void authSuccess(){
        // Listeners for auth success

    }

    public void authFail(){
        // Listeners for auth fail

    }
}

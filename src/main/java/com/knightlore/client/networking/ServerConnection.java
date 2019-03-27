package com.knightlore.client.networking;

import com.google.gson.Gson;
import com.knightlore.client.exceptions.ClientAlreadyAuthenticatedException;
import com.knightlore.client.exceptions.ConfigItemNotFoundException;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.client.networking.backend.ResponseHandler;
import com.knightlore.client.networking.backend.responsehandlers.server.GameList;
import com.knightlore.client.networking.backend.responsehandlers.server.GameRequest;
import com.knightlore.client.networking.backend.responsehandlers.server.ListLevels;
import com.knightlore.client.networking.backend.responsehandlers.server.SessionKey;
import com.knightlore.game.Level;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.Sendable;
import com.knightlore.util.Config;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ServerConnection {

    public static ServerConnection instance;

    Optional<String> sessionKey;
    Optional<String> username;
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
        Optional<String> apiKey = Config.apiKey();

        if(!apiKey.isPresent()){
            throw new ConfigItemNotFoundException();
        }

        // Build up get session string
        Sendable sendable = new Sendable();
        sendable.setUuid();
        sendable.setFunction("get_session_token");

        Gson gson = new Gson();

        ApiKey myKey = new ApiKey(apiKey.get());
        sendable.setData(gson.toJson(myKey));

        ResponseHandler.waiting.put(sendable.getUuid(), new SessionKey());

        System.out.println("SENDING" + sendable.getData());
        client.dos.writeObject(sendable);

    }

    public void authSuccess(String sessionKey, String username){
        // Update local variables
        this.sessionKey = Optional.of(sessionKey);
        this.username = Optional.of(username);
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
            // TODO make dynamic
            request.addLevel(UUID.fromString("47eb096a-a88c-4933-afc2-ed961ce2158e"));
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

    public void listLevels(){
        if(this.authenticated){
            // Build up get session string
            Sendable sendable = new Sendable();
            sendable.setUuid();
            sendable.setFunction("list_levels");


            // Specify handler
            ResponseHandler.waiting.put(sendable.getUuid(), new ListLevels());

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

    public static boolean makeConnection() throws ConfigItemNotFoundException{
        Optional<Integer> authServerPort = Config.authServerPort();
        Optional<String> authServerIp = Config.authServerIp();

        if(authServerIp.isPresent() && authServerPort.isPresent()){
            com.knightlore.client.networking.backend.Client authClient;
            try{
                authClient = new com.knightlore.client.networking.backend.Client(InetAddress.getByName(authServerIp.get()), authServerPort.get());
                if (!authClient.run()) {
                    return false;
                }
                ServerConnection.instance = new ServerConnection(authClient);
            }catch(UnknownHostException e){
                e.printStackTrace();
                return false;
            }
        }else{
            throw new ConfigItemNotFoundException();
        }

        // Wait for instance to be ready
        int timeout = 5;
        int wait = 0;
        while(!ServerConnection.instance.ready()){
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException ignored){
                // Timeout should never interrupt
            }
            System.out.println("WAITING");
            wait++;

            if (wait == timeout) {
                return false;
            }
        }

        // Send authenticaton packet
        try {
            ServerConnection.instance.auth();
        }catch(ClientAlreadyAuthenticatedException e){
        }catch (IOException e){
            System.out.println("Client could not connect ");
            return false;
        }catch (ConfigItemNotFoundException e){
            System.out.println("ApiKey not provided");
            return false;
        }

        return true;
    }

    public void sendLevel(Level level, String name){
        Sendable sendable = new Sendable();
        sendable.setFunction("level_upload");

        Gson gson = new Gson();
        sendable.setData(gson.toJson(level));

        try{
            client.dos.writeObject(sendable);
        }catch(IOException e){
            System.out.println(e);
        }
    }
}

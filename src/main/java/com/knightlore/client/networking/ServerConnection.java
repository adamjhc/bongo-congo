package com.knightlore.client.networking;

import com.google.gson.Gson;
import com.knightlore.client.exceptions.ClientAlreadyAuthenticatedException;
import com.knightlore.client.exceptions.ConfigItemNotFoundException;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.client.networking.backend.ResponseHandler;
import com.knightlore.client.networking.backend.responsehandlers.server.*;
import com.knightlore.game.Level;
import com.knightlore.networking.server.ApiKey;
import com.knightlore.networking.server.LevelUpload;
import com.knightlore.networking.Sendable;
import com.knightlore.util.Config;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Store for current server connection & related information
 *
 * @author Lewis Relph
 */
public class ServerConnection {

    public static ServerConnection instance;

    Optional<String> sessionKey;
    Optional<String> username;
    boolean authenticated = false;
    Client client;


    /**
     * Default constructor, no key provided
     * @param client
     */
    public ServerConnection(Client client) {
        this.client = client;
        this.sessionKey = Optional.empty();
    }

    /**
     * Constructor if session key has already been established
     * @param client
     * @param sessionKey
     */
    public ServerConnection(Client client, String sessionKey) {
        this.client = client;
        this.sessionKey = Optional.of(sessionKey);
    }

    /**
     * Setter for session key
     * @param sessionKey
     */
    public void setSessionKey(String sessionKey){
        this.sessionKey = Optional.of(sessionKey);
    }

    /**
     * Authenticate with the remote server
     * @throws ClientAlreadyAuthenticatedException
     * @throws IOException
     * @throws ConfigItemNotFoundException
     */
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

        client.dos.writeObject(sendable);
    }

    /**
     * Listeners for authentication success
     * @param sessionKey
     * @param username
     */
    public void authSuccess(String sessionKey, String username){
        // Update local variables
        this.sessionKey = Optional.of(sessionKey);
        this.username = Optional.of(username);
        this.authenticated = true;
    }

    /**
     * Listeners for authentication fail
     */
    public void authFail(){
        // Listeners for auth fail
    }

    /**
     * Getter for client ready
     * @return
     */
    public boolean ready(){
        return this.client.ready;
    }

    /**
     * Getter for authenticated
     * @return
     */
    public boolean isAuthenticated(){
        return this.authenticated;
    }

    /**
     * Close current connection
     * @throws IOException
     */
    public void close() throws IOException{
        this.client.close();
    }

    /**
     * Send new game request to server
     */
    public void requestGame(){
        if(this.authenticated){
            // Build up get session string
            Sendable sendable = new Sendable();
            sendable.setUuid();
            sendable.setFunction("request_game");

            Gson gson = new Gson();

            com.knightlore.networking.server.GameRequest request = new com.knightlore.networking.server.GameRequest();

            // Space to implement requesting specific levels
            //request.addLevel(UUID.fromString("47eb096a-a88c-4933-afc2-ed961ce2158e"));


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

    /**
     * Send list game request to server
     */
    public void listGames(){
        if(this.authenticated){
            // Build up get session string
            Sendable sendable = new Sendable();
            sendable.setUuid();
            sendable.setFunction("list_games");

            // Specify handler
            ResponseHandler.waiting.put(sendable.getUuid(), new GameList());

            // Write
            try{
                client.dos.writeObject(sendable);
            }catch(IOException e){
                System.out.println(e);
            }
        }
    }

    /**
     * Send list level request ot server
     */
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

    /**
     * Send highscore request to server
     */
    public void getHighScores(){
        if(this.authenticated){
            // Build up get session string
            Sendable sendable = new Sendable();
            sendable.setUuid();
            sendable.setFunction("list_highscores");

            // Specify handler
            ResponseHandler.waiting.put(sendable.getUuid(), new HighScores());

            try{
                client.dos.writeObject(sendable);
            }catch(IOException e){
                System.out.println(e);
            }
        }
    }

    /**
     * Getter for session key
     * @return
     */
    public Optional<String> getSessionKey() {
        return sessionKey;
    }

    /**
     * Make connection to database
     * @return
     * @throws ConfigItemNotFoundException
     */
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

    /**
     * Send request to upload level to server
     * @param level
     * @param name
     */
    public void sendLevel(Level level, String name){
        Sendable sendable = new Sendable();
        sendable.setFunction("level_upload");

        Gson gson = new Gson();
        LevelUpload lu = new LevelUpload(level, name);

        sendable.setData(gson.toJson(lu));

        try{
            client.dos.writeObject(sendable);
        }catch(IOException e){
            System.out.println(e);
        }
    }
}

package com.knightlore.game.server;

import com.google.gson.Gson;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.game.Game;
import com.knightlore.game.GameState;
import com.knightlore.game.map.MapSet;
import com.knightlore.game.map.TileSet;
import com.knightlore.networking.GameStart;
import com.knightlore.networking.Sendable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class GameServer extends Thread {
    UUID id;
    public int socket;
    String sessionOwner;
    ArrayList<ClientHandler> clients;

    Game model;

    public GameServer(UUID id, int socket, String sessionOwner, Game model){
        this.id = id;
        this.socket = socket;
        this.sessionOwner = sessionOwner;
        this.model = model;
        this.clients = new ArrayList<>();
    }

    // Start new server
    public void run(){
        // Spin up server
        // server is listening on port 5056
        try{
            ServerSocket ss = new ServerSocket(this.socket);

            // Capture new clients
            while (true)
            {
                Socket s = null;
                try
                {
                    // socket object to receive incoming com.knightlore.server.client requests
                    s = ss.accept();

                    System.out.println("A new client is connected : " + s);

                    // Get input and output streams
                    ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
                    ObjectInputStream dis = new ObjectInputStream(s.getInputStream());


                    System.out.println("Assigning new thread for this client");

                    // Make thread for incoming
                    ClientHandler t = new ClientHandler(s, dis, dos, this);

                    // Send to com.knightlore.server.client
                    t.start();

                    // Add to client bank
                    this.clients.add(t);

                }
                catch (Exception e){
                    // If an error occurs, close
                    s.close();
                    System.out.println("An error has occured" + e);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public ArrayList<ClientHandler> registeredClients(){
        ArrayList<ClientHandler> registered = new ArrayList<>();

        for(ClientHandler each : this.clients){
            if(each.registered()){
                registered.add(each);
            }
        }

        return registered;
    }

    public String sessionOwner(){
        return this.sessionOwner;
    }

    public Game getModel(){
        return this.model;
    }

    public void sendToRegistered(Sendable sendable){
        System.out.println("SENDING TO ALL");
        try{
            for(ClientHandler registered : this.registeredClients()){
                registered.dos.writeObject(sendable);
            }
        }catch(IOException e){
            System.out.println("FAILED TO SEND " + e);
        }
    }

    public void sendToRegisteredExceptSelf(Sendable sendable, String ownSessionKey){
        System.out.println("SENDING TO EXCEPT SELF");
        try{
            for(ClientHandler registered : this.registeredClients()){
                if(!registered.sessionKey.get().equals(ownSessionKey)){
                    registered.dos.writeObject(sendable);
                }
            }
        }catch(IOException e){
            System.out.println("FAILED TO SEND " + e);
        }
    }

    public void startGame(){
        // Update model
        this.model.setState(GameState.PLAYING);
        this.model.createNewLevel(new MapSet(new TileSet()).getMap(0));

        // Send
        Gson gson = new Gson();
        Sendable sendable = new Sendable();
        GameStart startGame = new GameStart();
        sendable.setFunction("start_game");
        sendable.setData(gson.toJson(startGame));

        sendToRegistered(sendable);
    }

//    public void setLevel(int index){
//        this.model.setLevel(index);
//
//        // Send update to clients
//        Gson gson = new Gson();
//        Sendable sendable = new Sendable();
//        SetLevel level = new SetLevel(index);
//        sendable.setData(gson.toJson(level));
//
//        // Send to registered clients
//        sendToRegistered(sendable);
//    }

    public UUID getUUID(){
        return this.id;
    }

    public int getPort(){
        return this.socket;
    }

    public ArrayList<String> registeredUUIDs(){
        ArrayList<String> uuids = new ArrayList<>();

        for(ClientHandler client : this.registeredClients()){
            uuids.add(client.sessionKey.get());
        }

        return uuids;
    }
}

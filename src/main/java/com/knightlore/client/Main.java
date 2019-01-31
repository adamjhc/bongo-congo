package com.knightlore.client;


import com.knightlore.client.exceptions.ClientAlreadyAuthenticatedException;
import com.knightlore.client.networking.Authenticator;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.game.server.GameServer;

import java.io.IOException;

public class Main {

    public GameServer gameserver;

    public static void main(String[] args) {

        System.out.println("Public or local");

        // Start local gameserver
//        GameServer gameServer = new GameServer("localhost", 8012);
//        gameServer.start();

        // Begin publicness

        // Create authentication client
        Client authClient = new Client(12123);
        authClient.run();

        // Create Autheicator
        Authenticator auth = new Authenticator(authClient);

        // Attemept auth
        try{
            auth.auth();
        }catch(IOException e){
            e.printStackTrace();
        }catch(ClientAlreadyAuthenticatedException e){
            e.printStackTrace();
        }





    }
}

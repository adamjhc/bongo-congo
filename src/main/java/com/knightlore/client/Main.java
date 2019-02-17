package com.knightlore.client;


import com.knightlore.client.exceptions.ClientAlreadyAuthenticatedException;
import com.knightlore.client.exceptions.ConfigItemNotFoundException;
import com.knightlore.client.networking.ServerConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.game.model.Game;
import com.knightlore.util.Config;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws ConfigItemNotFoundException {
        System.out.println(Config.authServerIp());
        System.out.println(Config.authServerPort());

        // Check we have the right variables
        Optional<Integer> authServerPort = Config.authServerPort();
        Optional<String> authServerIp = Config.authServerIp();

        if(authServerIp.isPresent() && authServerPort.isPresent()){
            Client authClient;

            try{
                authClient = new Client(InetAddress.getByName(authServerIp.get()), authServerPort.get());
                authClient.run();

                ServerConnection.instance = new ServerConnection(authClient);
            }catch(UnknownHostException e){
                System.out.println("Warning: Invalid IP");
                System.exit(1);
            }

        }else{
            throw new ConfigItemNotFoundException();
        }

        try {
            ServerConnection.instance.auth();


        }catch(ClientAlreadyAuthenticatedException e){

        }catch (IOException e){

        }





//        System.out.println("Public or local");
//
//        // Create authentication client
//        Client authClient = new Client(12123);
//        authClient.run();
//
//        // Create Autheicator
//        Authenticator auth = new Authenticator(authClient);
//
//        // Attemept auth
//        try{
//            auth.auth();
//        }catch(IOException e){
//            e.printStackTrace();
//        }catch(ClientAlreadyAuthenticatedException e){
//            e.printStackTrace();
//        }





    }
}

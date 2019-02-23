package com.knightlore;

import com.knightlore.client.Main;
import com.knightlore.client.exceptions.ClientAlreadyAuthenticatedException;
import com.knightlore.client.exceptions.ConfigItemNotFoundException;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.ServerConnection;
import com.knightlore.util.Config;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public class DemoClient {
    public static void main(String[] args) throws ConfigItemNotFoundException{
        // Check we have the right variables
        Optional<Integer> authServerPort = Config.authServerPort();
        Optional<String> authServerIp = Config.authServerIp();
        if(authServerIp.isPresent() && authServerPort.isPresent()){
            com.knightlore.client.networking.backend.Client authClient;
            try{
                authClient = new com.knightlore.client.networking.backend.Client(InetAddress.getByName(authServerIp.get()), authServerPort.get());
                authClient.run();
                ServerConnection.instance = new ServerConnection(authClient);
            }catch(UnknownHostException e){
                System.out.println("Warning: Invalid IP");
                System.exit(1);
            }
        }else{
            throw new ConfigItemNotFoundException();
        }
        while(!ServerConnection.instance.ready()){
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                // Timeout should never interrupt
            }
        }
        try {
            ServerConnection.instance.auth();
        }catch(ClientAlreadyAuthenticatedException e){
        }catch (IOException e){
            System.out.println("Client could not connect ");
        }catch (ConfigItemNotFoundException e){
            System.out.println("ApiKey not provided");
            System.exit(1);
        }
        // Wait for auth
        while(!ServerConnection.instance.isAuthenticated()){
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                // Timeout should never interrupt
            }
            System.out.println("WAITING FOR AUTH");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select option");
        System.out.println("1. Create new game");
        System.out.println("2. Join game");
        System.out.println("3. Close connection");

        int option = Integer.valueOf(scanner.nextLine());
        switch(option){
            case 1:
                // Request game
                ServerConnection.instance.requestGame();
                System.out.println("Press enter to register with game server");
                scanner.nextLine();

                GameConnection.instance.register();
                System.out.println("Press enter to start game");
                scanner.nextLine();
                GameConnection.instance.startGame();
                break;
            case 2:
                // Join game
                ServerConnection.instance.listGames();
                // Select
                System.out.println("Input port to connect to: ");
                String port = scanner.nextLine();
                // Join game
                try{
                    com.knightlore.client.networking.backend.Client gameClient = new com.knightlore.client.networking.backend.Client(InetAddress.getByName(Config.authServerIp().get()), Integer.valueOf(port));
                    gameClient.run();
                    GameConnection.instance = new GameConnection(gameClient, ServerConnection.instance.getSessionKey().get());
                    System.out.println("Press enter to register with game server");
                    scanner.nextLine();
                    GameConnection.instance.register();
                }catch(UnknownHostException e){
                }
                break;

            case 3:
                // Close connection
                try{
                    ServerConnection.instance.close();
                }catch(IOException e){
                    System.out.println("COULD NOT CLOSE");
                }

        }
    }
}
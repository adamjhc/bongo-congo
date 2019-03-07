package com.knightlore.client.networking.backend;


import com.knightlore.client.networking.backend.commandhandler.Factory;
import com.knightlore.networking.Sendable;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReceiver extends Thread{

    ObjectInputStream dis;
    Client client;

    public ClientReceiver(Client client, ObjectInputStream dis){
        this.client = client;
        this.dis = dis;
    }

    @Override
    public void run(){

        Sendable received;

        while (true) {
            try {
                received = (Sendable) dis.readObject();
                System.out.println(received.getFunction());

                if(received.hasUUID() && ResponseHandler.isWaiting(received.getUuid())){
                    // Request is a command response, pass to handler
                    System.out.println("UUID " + received.getUuid());
                    ResponseHandler.handle(received.getUuid(), received);
                    continue;
                }

                // Pass to handlers
                Factory.create(this.client, received);


                System.out.println("CLIENT RECEIVED: " + received);
            }catch (IOException e){
                break;
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }

        System.out.println("Connection lost");
    }
}

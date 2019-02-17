package com.knightlore.client.networking.backend;


import com.knightlore.networking.Sendable;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReceiver extends Thread{

    ObjectInputStream dis;

    public ClientReceiver(ObjectInputStream dis){
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
                }

                // Pass to handlers



                System.out.println("CLIENT RECEIVED: " + received);
            }catch (IOException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}

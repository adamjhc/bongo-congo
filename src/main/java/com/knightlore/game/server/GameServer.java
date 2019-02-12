package com.knightlore.game.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class GameServer extends Thread {
    UUID id;
    public int socket;

    public GameServer(UUID id, int socket){
        this.id = id;
        this.socket = socket;
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

                    // Make thread OMITTED FOR FIRST COMMIT
                    Thread t = new ClientHandler(s, dis, dos);

                    // Send to com.knightlore.server.client
                    t.start();

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
}

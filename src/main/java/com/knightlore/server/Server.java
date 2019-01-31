package com.knightlore.server;

import com.knightlore.server.database.Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


// Main registration/game server handler
public class Server
{
    public static void main(String[] args) throws IOException
    {
        // Perform database connection
        if(!Connection.connect()){
            System.out.println("Connection to db couldn't be made");
            System.exit(1);
        }

        // Spin up server
        ServerSocket ss = new ServerSocket(12123);

        // Capture new clients
        while (true)
        {
            Socket s = null;
            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client has connected : " + s);

                // Get input and output streams
                ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // Make thread
                Thread t = new ClientHandler(s, dis, dos);

                // Start thread
                t.start();
            }
            catch (Exception e){
                // If an error occurs, close
                s.close();
                System.out.println("An error has occurred" + e);
            }
        }
    }
}


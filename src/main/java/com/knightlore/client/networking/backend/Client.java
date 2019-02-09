package com.knightlore.client.networking.backend;// Java implementation for a com.knightlore.server.client
// Save file as Client.java

import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

// Client class
public class Client
{

    public ObjectOutputStream dos;
    public ObjectInputStream dis;
    public ClientReceiver receiver;

    int socket;
    InetAddress ip;

    public Client(InetAddress ip, int socket){
        this.socket = socket;
        this.ip = ip;
    }

    public void run(){
        try {
            // Join socket
            Socket s = new Socket(this.ip, socket);

            // Get input output streams
            this.dos = new ObjectOutputStream(s.getOutputStream());
            this.dis = new ObjectInputStream(s.getInputStream());

            // Start Client Receiver thread
            receiver = new ClientReceiver(this.dis);
            receiver.start();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void close() throws IOException{
        // closing resources
        dis.close();
        dos.close();
    }
}

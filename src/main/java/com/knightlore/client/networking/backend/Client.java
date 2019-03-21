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
    public boolean ready;


    public int socket;
    public InetAddress ip;

    public Client(InetAddress ip, int socket){
        this.socket = socket;
        this.ip = ip;
        this.ready = false;
    }

    public void run(){
        try {
            // Join socket
            System.out.println("Waiting for socket");
            Socket s = new Socket(this.ip, socket);

            System.out.println("socket est");

            // Get input output streams
            this.dos = new ObjectOutputStream(s.getOutputStream());
            this.dis = new ObjectInputStream(s.getInputStream());

            // Start Client Receiver thread
            receiver = new ClientReceiver(this, this.dis);
            receiver.start();

            this.ready = true;
        }catch(Exception e){
            System.out.println("There wan an error establishing the socket connection");
        }
    }

    public void close() throws IOException{
        // closing resources
        dis.close();
        dos.close();
    }
}

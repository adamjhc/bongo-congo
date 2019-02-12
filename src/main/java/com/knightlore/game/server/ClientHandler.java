package com.knightlore.game.server;

import com.knightlore.networking.Sendable;
import com.knightlore.server.database.Authenticate;
import com.knightlore.server.database.SessionGenerator;
import com.knightlore.server.game.commands.Factory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

// Client handler class for game
public class ClientHandler extends Thread{
    // Omitted for first commit

    // Declare input and output streams
    final ObjectInputStream dis;
    final ObjectOutputStream dos;

    SessionGenerator apikey = new SessionGenerator();
    Authenticate authenticate = new Authenticate();
    Optional<String> sessionKey = Optional.empty();
    Factory factory = new Factory();

    // Socket
    final Socket s;

    public ClientHandler(Socket s, ObjectInputStream dis, ObjectOutputStream dos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.sessionKey = Optional.empty();
    }

    @Override
    public void run() {



        try{
            // closing resources
            this.dis.close();
            this.dos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

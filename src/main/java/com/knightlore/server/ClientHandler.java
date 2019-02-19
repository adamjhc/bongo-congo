package com.knightlore.server;

import com.knightlore.client.networking.backend.responsehandlers.server.SessionKey;
import com.knightlore.networking.Sendable;
import com.knightlore.server.commandhandler.Factory;
import com.knightlore.server.commandhandler.GameRequest;
import com.knightlore.server.database.Authenticate;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;

// Client handler class
public class ClientHandler extends Thread
{
    final static Logger logger = Logger.getLogger(ClientHandler.class);

    // Declare input and output streams
    public final ObjectInputStream dis;
    public final ObjectOutputStream dos;

    public Authenticate authenticate = new Authenticate();
    public Optional<String> sessionKey = Optional.empty();

    // Socket
    final Socket s;

    public ClientHandler(Socket s, ObjectInputStream dis, ObjectOutputStream dos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run()
    {
        Sendable sendable;

        // Continuously wait for messages
        while (true)
        {
            try {
                // Retrieve new incoming sendable object
                sendable = (Sendable) dis.readObject();

                // First check for connection close
                if(sendable.getFunction().equals("close_connection")){
                    logger.info("Closing this connection.");
                    this.s.close();
                    logger.info("Connection closed");
                    break;
                }

                // If not, pass to factory
                Factory.create(this, sendable);

            } catch (IOException e) {
                e.printStackTrace();
            }  catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public InetAddress getSocketIP(){
        return this.s.getInetAddress();
    }

    public SessionKey getSessionKey(){
        if(this.sessionKey.isPresent()){
            // Return session key with that value

        }

        return null;
    }
}

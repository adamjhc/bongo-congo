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
import java.util.UUID;

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

    // Server
    private Server server;
    private UUID serverClientReference;

    public ClientHandler(Socket s, ObjectInputStream dis, ObjectOutputStream dos, Server server, UUID serverClientReference)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.server = server;
        this.serverClientReference = serverClientReference;
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

                // If not, pass to factory
                Factory.create(this, sendable);

            } catch (IOException e) {
                break;
            }  catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Close resources

        try
        {
            this.dis.close();
            this.dos.close();

            logger.info("Client connection to game closed session key: " + this.sessionKey.get());

        }catch(IOException e){
            e.printStackTrace();
        }

        // Parent callback
        this.server.removeConnection(this.serverClientReference);

    }

    public InetAddress getSocketIP(){
        return this.s.getInetAddress();
    }


}

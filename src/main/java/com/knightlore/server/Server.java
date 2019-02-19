package com.knightlore.server;

import com.knightlore.server.commandhandler.GameRequest;
import com.knightlore.server.database.Connection;
import com.knightlore.util.Config;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;


// Main registration/game server handler
public class Server
{

    final static Logger logger = Logger.getLogger(Server.class);

    public static void main(String[] args) throws IOException
    {
        BasicConfigurator.configure();

        // Perform database connection
        if(!Connection.connect()){
            logger.warn("Connection to db couldn't be made");
            System.exit(1);
        }

        logger.info("Connection to database established");

        Optional<Integer> authServerPort = Config.authServerPort();

        if(!authServerPort.isPresent()){
            logger.warn("Port not defined");
            System.exit(0);
        }

        // Spin up server
        ServerSocket ss = new ServerSocket(authServerPort.get());

        // Capture new clients
        while (true)
        {
            Socket s = null;
            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();

                logger.info("New client connected to main server : " + s);

                // Get input and output streams
                ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());

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


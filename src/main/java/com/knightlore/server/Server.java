package com.knightlore.server;

import com.knightlore.client.exceptions.ConfigItemNotFoundException;
import com.knightlore.server.database.Connection;
import com.knightlore.util.Config;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 * Main class for running the server Responsible for handling incoming connections and database
 *
 * @author Lewis Relph
 */
public class Server {

  static final Logger logger = Logger.getLogger(Server.class);

  public static Server instance;

  public HashMap<UUID, ClientHandler> connectedClients;

  /** Default constructor */
  public Server() {
    this.connectedClients = new HashMap<>();
  }

  /**
   * Main function, no arguments
   *
   * @param args
   */
  public static void main(String[] args) {
    // Create new instance of server
    instance = new Server();

    // Setup
    instance.setup();

    // Start server
    try {
      instance.start();
    } catch (IOException e) {
      logger.warn("Server exception: " + e);
    } catch (ConfigItemNotFoundException e) {
      logger.warn("A configuration item was not found in the system.env: " + e);
    }
  }

  /** Initial setup of server, database connection & logging */
  public void setup() {
    // Configure logging
    BasicConfigurator.configure();

    // Perform database connection
    if (!Connection.connect()) {
      logger.warn("Connection to db couldn't be made");
      System.exit(1);
    }

    logger.info("Connection to database established");
  }

  public void start() throws IOException, ConfigItemNotFoundException {
    // Retrieve port to use
    Optional<Integer> authServerPort = Config.authServerPort();

    if (!authServerPort.isPresent()) {
      throw new ConfigItemNotFoundException();
    }

    // Start socket
    ServerSocket ss = new ServerSocket(authServerPort.get());

    // Capture new clients
    while (true) {
      Socket s = null;
      try {
        // Socket for new incoming client
        s = ss.accept();

        logger.info("New client connected to main server : " + s);

        // Get input and output streams
        ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
        ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());

        // Generate server client reference
        UUID serverClientReference = UUID.randomUUID();

        // Create thread
        ClientHandler t = new ClientHandler(s, dis, dos, this, serverClientReference);

        // Store client in hashmap
        this.connectedClients.put(serverClientReference, t);

        // Start thread
        t.start();
      } catch (Exception e) {
        // If an error occurs, close
        s.close();
        logger.warn("An error has occurred" + e);
      }
    }
  }

  /**
   * Remove a connection from our connected client bank
   *
   * @param serverClientReference
   */
  public void removeConnection(UUID serverClientReference) {
    // Check exists before removing
    if (this.connectedClients.containsKey(serverClientReference)) {
      this.connectedClients.remove(serverClientReference);
    }
  }
}

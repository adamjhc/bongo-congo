package com.knightlore.server;

import com.knightlore.networking.Sendable;
import com.knightlore.server.commandhandler.Factory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;
import java.util.UUID;

/**
 * Class for handling a single client connection to the main server
 *
 * @author Lewis Relph
 */
public class ClientHandler extends Thread {
  static final Logger logger = Logger.getLogger(ClientHandler.class);

  // Input/Output streams
  public final ObjectInputStream dis;
  public final ObjectOutputStream dos;
  // Socket
  final Socket s;
  // Session key and username
  public Optional<String> sessionKey = Optional.empty();
  public Optional<String> username = Optional.empty();
  // Server callback
  private Server server;

  // Client unique reference for callback
  private UUID serverClientReference;

  /**
   * Default constructor
   *
   * @param s
   * @param dis
   * @param dos
   * @param server
   * @param serverClientReference
   */
  public ClientHandler(
      Socket s,
      ObjectInputStream dis,
      ObjectOutputStream dos,
      Server server,
      UUID serverClientReference) {
    this.s = s;
    this.dis = dis;
    this.dos = dos;
    this.server = server;
    this.serverClientReference = serverClientReference;
  }

  @Override
  public void run() {
    Sendable sendable;

    // Wait for messages
    while (true) {
      try {
        // Retrieve new incoming Sendable object
        sendable = (Sendable) dis.readObject();

        // First check for connection close
        if (sendable.getFunction().equals("close_connection")) {
          logger.info("Closing this connection.");
          this.s.close();
          logger.info("Connection closed");
          break;
        }

        // If not, pass to factory
        Factory.create(this, sendable);

      } catch (IOException e) {
        break;
      } catch (ClassNotFoundException e) {
        logger.warn("Unknown class requested: " + e);
      }
    }

    // Close resources
    try {
      // Close streams
      this.dis.close();
      this.dos.close();

      logger.info("Client connection to game closed session key: " + this.sessionKey.get());

    } catch (IOException e) {
      e.printStackTrace();
    }

    // Parent callback
    this.server.removeConnection(this.serverClientReference);
  }

  /**
   * Getter for socket inetaddress
   *
   * @return socket ip
   */
  public InetAddress getSocketIP() {
    return this.s.getInetAddress();
  }
}

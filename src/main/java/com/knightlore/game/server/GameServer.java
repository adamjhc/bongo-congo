package com.knightlore.game.server;

import com.google.gson.Gson;
import com.knightlore.game.GameModel;
import com.knightlore.game.GameState;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.game.GameStart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Thread to run the actual game server
 *
 * @author Lewis Relph
 */
public class GameServer extends Thread {
  public int socket;
  public PositionUpdateQueueHandler poqhandler;
  UUID id;
  String sessionOwner;
  String name;
  ArrayList<ClientHandler> clients;
  boolean running;
  boolean lobby;
  GameModel model;

  public GameServer(UUID id, int socket, String sessionOwner, GameModel model, String name) {
    this.id = id;
    this.socket = socket;
    this.sessionOwner = sessionOwner;
    this.model = model;
    this.clients = new ArrayList<>();
    this.name = name;
    this.running = true;
    this.lobby = true;
  }

  // Start new server
  @Override
  public void run() {
    // Spin up server
    // server is listening on port 5056
    try (ServerSocket serverSocket = new ServerSocket(this.socket)) {
      // Capture new clients
      while (running) {
        Socket s = null;
        try {
          // socket object to receive incoming com.knightlore.server.client requests
          serverSocket.setSoTimeout(1000);
          s = serverSocket.accept();

          System.out.println("A new client is connected : " + s);

          // Get input and output streams
          ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
          ObjectInputStream dis = new ObjectInputStream(s.getInputStream());

          System.out.println("Assigning new thread for this client");

          // Make thread for incoming
          ClientHandler t = new ClientHandler(s, dis, dos, this);

          // Send to com.knightlore.server.client
          t.start();

          // Add to client bank
          this.clients.add(t);

        } catch (SocketTimeoutException e) {
          // loop back round
        } catch (Exception e) {
          // If an error occurs, close
          s.close();
          System.out.println("An error has occured" + e);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("THREAD CLOSE");
  }

  public ArrayList<ClientHandler> registeredClients() {
    ArrayList<ClientHandler> registered = new ArrayList<>();

    for (ClientHandler each : this.clients) {
      if (each.registered()) {
        registered.add(each);
      }
    }

    return registered;
  }

  public String sessionOwner() {
    return this.sessionOwner;
  }

  public GameModel getModel() {
    return this.model;
  }

  public void sendToRegistered(Sendable sendable) {
    System.out.println("SENDING TO ALL");
    try {
      for (ClientHandler registered : this.registeredClients()) {
        System.out.println("SENDING TO: " + registered.sessionKey.get());
        registered.dos.writeObject(sendable);
      }
    } catch (IOException e) {
      System.out.println("FAILED TO SEND " + e);
    }
  }

  public void sendToRegisteredExceptSelf(Sendable sendable, String ownSessionKey) {
    System.out.println("SENDING TO EXCEPT SELF");
    try {
      for (ClientHandler registered : this.registeredClients()) {
        if (!registered.sessionKey.get().equals(ownSessionKey)) {
          System.out.println("Sending to " + registered.sessionKey.get());
          registered.dos.writeObject(sendable);
        }
      }
    } catch (IOException e) {
      System.out.println("FAILED TO SEND " + e);
    }
  }

  public void startGame() {
    // Update model
    this.model.setState(GameState.PLAYING);

    // Send
    Gson gson = new Gson();
    Sendable sendable = new Sendable();
    GameStart startGame = new GameStart(model);
    sendable.setFunction("start_game");
    sendable.setData(gson.toJson(startGame));

    this.lobby = false;
    sendToRegistered(sendable);
  }

  public UUID getUUID() {
    return this.id;
  }

  public int getPort() {
    return this.socket;
  }

  public String getGameName() {
    return this.name;
  }

  public boolean allReady() {
    boolean ready = true;
    for (ClientHandler each : this.clients) {
      if (!each.ready) {
        ready = false;
      }
    }

    return ready;
  }

  public void close() {
    for (ClientHandler client : this.clients) {
      // Send close
      Sendable sendable = new Sendable();
      sendable.setFunction("game_close");
      client.send(sendable);

      // Close client
      client.close();
    }
    running = false;
    this.interrupt();
  }

  public boolean inLobby() {
    return this.lobby;
  }

  public void removeConnection(String session) {
    this.poqhandler.close();
    ClientHandler clientToRemove = null;

    for (ClientHandler client : registeredClients()) {
      if (client.sessionKey.equals(session)) {
        clientToRemove = client;
      }
    }
    if (clientToRemove != null) registeredClients().remove(clientToRemove);
  }
}

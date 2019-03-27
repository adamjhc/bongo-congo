package com.knightlore.client.networking.backend; // Java implementation for a
// com.knightlore.server.client
// Save file as Client.java

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

// Client class
public class Client {

  public ObjectOutputStream dos;
  public ObjectInputStream dis;
  public ClientReceiver receiver;
  public boolean ready;
  public int port;
  public InetAddress ip;
  private Socket socket;
  private boolean running;

  public Client(InetAddress ip, int port) {
    this.port = port;
    this.ip = ip;
    this.ready = false;
  }

  public boolean run() {
    try {
      // Join socket
      System.out.println("Waiting for socket");

      socket = new Socket(ip, port);
      socket.setReuseAddress(true);
      // Get input output streams
      dos = new ObjectOutputStream(socket.getOutputStream());
      dis = new ObjectInputStream(socket.getInputStream());

      // Start Client Receiver thread
      receiver = new ClientReceiver(this, dis);
      receiver.start();

      this.ready = true;
      running = true;
    } catch (Exception e) {
      System.out.println("There wan an error establishing the socket connection");
      return false;
    }

    return true;
  }

  boolean isRunning() {
    return running;
  }

  public void close() throws IOException {
    // closing resources
    running = false;
    dis.close();
    dos.close();
    socket.close();
  }
}

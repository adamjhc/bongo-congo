package com.knightlore.game.server;

import com.knightlore.game.GameModel;
import com.knightlore.networking.Sendable;
import com.knightlore.game.server.commandhandler.Factory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

// Client handler class for game
public class ClientHandler extends Thread{
    // Declare input and output streams
    final ObjectInputStream dis;
    final ObjectOutputStream dos;

    public Optional<String> sessionKey = Optional.empty();

    // Socket
    final Socket s;
    final GameServer gameServer;

    public ClientHandler(Socket s, ObjectInputStream dis, ObjectOutputStream dos, GameServer gameServer)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.sessionKey = Optional.empty();
        this.gameServer = gameServer;
    }

    @Override
    public void run() {

        Sendable sendable;
        while (true)
        {
            try {
                // Retrieve new incoming sendable object
                sendable = (Sendable) dis.readObject();

                // First check for connection close
                if(sendable.getFunction().equals("close_connection")){
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // Next check for session
                if(!this.sessionKey.isPresent()){
                    // Assume this message is the session key

                }

                // If not, pass to factory
                // TODO implement factory
                Factory.create(this, sendable);

            } catch (IOException e) {
                break;
            }  catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


        try{
            // closing resources
            this.dis.close();
            this.dos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void send(Sendable sendable){
        try{
            this.dos.writeObject(sendable);
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public boolean isOwner(){
        return this.gameServer.sessionOwner().equals(this.sessionKey.get());
    }

    public GameServer server(){
        return this.gameServer;
    }

    public GameModel model(){
        return this.gameServer.getModel();
    }

    public boolean registered(){
        return this.sessionKey.isPresent();
    }

}

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

    private boolean attemptRegister(Sendable request){
        // No session key received, must register!
//        String session = "";
//        if(request.getData().has("session")){
//            try{
//                session = request.getData().getString("session");
//            }catch (JSONException e){
//                // Pass
//            }
//        }
//
//        // Check if token is currently active in db
//        Model sessionToken = SessionToken.instance.createNewInstance();
//        sessionToken.where(new Condition("token", "=", session));
//
//        if(sessionToken.count() > 0){
//            sessionKey = Optional.of(session);
//            return true;
//        }

        return false;
    }

    @Override
    public void run() {
        Object received;

//        while (true) {
//            try {
//                // New packet from client
//                received = dis.readObject();
//
//                if(received.getFunction().equals("exit")){
//                    break;
//                }
//
//                System.out.println("Game received: " + received.getFunction());
//
//                Sendable response = new Sendable();
//
//                if(!sessionKey.isPresent()){
//                    if(attemptRegister(received)){
//                        response.success = true;
//                    }else{
//                        response.success = false;
//                    }
//                    dos.writeObject(response);
//                    continue;
//                }
//
//                // Retrieve json
//
//
//                try{
//                    JSONObject jsonObject = new JSONObject(received);
//                    String function = jsonObject.getString("function");
//
//                    if(!sessionKey.isPresent()){
//                        // Check com.knightlore.server.client is allowed in
//                        String session = jsonObject.getString("session");
//
//                        // Compare to active sessions
//                        Model sessionToken = SessionToken.instance.createNewInstance();
//
//                        sessionToken.where(new Condition("token", "=", session));
//
//                        response = new JSONObject();
//
//                        if(sessionToken.count() > 0){
//                            sessionKey = Optional.of(jsonObject.getString("session"));
//                            response.put("success", "true");
//                        }else {
//                            response.put("success", "false");
//                        }
//
//                        System.out.println("DNE");
//
//                        dos.writeUTF(response.toString());
//                        continue;
//                    }
//
//                    if(jsonObject.getString("function").equals("bye")){
//                        break;
//                    }
//
//                    Factory.create(function, new JSONObject(received), dos);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch(IOException e){
//            e.printStackTrace();
//        }
//   }


        try{
            // closing resources
            this.dis.close();
            this.dos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

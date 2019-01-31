package com.knightlore.server;

import com.knightlore.networking.Sendable;
import com.knightlore.server.commandhandler.Factory;
import com.knightlore.server.database.Authenticate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

// Client handler class
public class ClientHandler extends Thread
{

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

        // Continuously get clients
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

                // If not, pass to factory
                Factory.create(this, sendable);

                // Pick correct handler
//                try{
//
//
//
//                    switch (function) {
//                        // Generate session based on authentication token
//                        case "get_session_token":
//                            Optional<String> token = apikey.getToken(jsonObject.getString("key"));
//
//
//                            response = new JSONObject();
//
//                            if(token.isPresent()){
//                                this.sessionKey = token;
//                                response.put("success", "true");
//                                response.put("token", token.get());
//                            }else{
//                                response.put("success", "false");
//                            }
//
//                            dos.writeUTF(response.toString());
//                            break;
//
//
//                        case "check_auth":
//                            response = new JSONObject();
//
//                            if(this.sessionKey.isPresent()){
//                                response.put("success", "true");
//                            }else{
//                                response.put("success", "false");
//                            }
//
//                            dos.writeUTF(response.toString());
//                            break;
//                    }
//
//                }catch(JSONException e){
//                    System.out.println("INVALID REQUEST");
//                    System.out.println(e);
//                }
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
}

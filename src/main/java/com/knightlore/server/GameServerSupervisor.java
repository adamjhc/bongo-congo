package com.knightlore.server;

import com.knightlore.game.GameState;
import com.knightlore.game.server.GameServer;
import com.knightlore.server.commandhandler.ListGames;
import com.knightlore.server.game.GameRepository;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class GameServerSupervisor extends Thread{

    public GameServer server;
    final static Logger logger = Logger.getLogger(GameServerSupervisor.class);

    public GameServerSupervisor(GameServer server){
        this.server = server;
    }

    public void run(){
        while(true){
            if(server.getModel().getState() == GameState.FINISHED){
                logger.info("Game complete detected, detaching from server");

                // Close server
                server.close();

                // Update repository
                GameRepository.instance.removeServer(server.getUUID());
                break;
            }


            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                //
            }
        }
    }
}

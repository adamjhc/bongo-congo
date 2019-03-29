package com.knightlore.game.server;

import com.knightlore.game.GameModel;

/**
 * Thread for updating the enemy positions in the model periodically
 *
 * @author Lewis Relph
 */
public class EnemyPositionUpdateManager extends Thread {

  GameModel model;
  GameServer server;

  public EnemyPositionUpdateManager(GameModel model, GameServer server) {
    this.model = model;
    this.server = server;
  }

  public void run() {
    /*Gson gson = new Gson();
            while(true){
                Sendable sendable = new Sendable();
                sendable.setFunction("enemy_location_update");
                EnemyLocationUpdate update = new EnemyLocationUpdate();

                for(Enemy current : model.getCurrentLevel().getEnemies()){
                    update.addEnemy(current.getId(), new EnemyLocationUpdateObject(current.getPosition(), current.getDirection(), current.getCurrentState()));
                }

                sendable.setData(gson.toJson(update));
                System.out.println("SENDING enemylocupdate");
                server.sendToRegistered(sendable);

                try{
                    TimeUnit.MILLISECONDS.sleep(55);
                }catch(InterruptedException e){

                }

            }
    */
  }
}

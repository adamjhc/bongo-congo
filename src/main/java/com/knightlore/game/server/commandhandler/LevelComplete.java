package com.knightlore.game.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.game.GameState;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.game.PositionUpdate;
import com.knightlore.networking.game.PositionUpdateChunk;
import com.knightlore.server.game.GameRepository;

public class LevelComplete extends Command {

  public void run(ClientHandler handler, Sendable sendable) {
    System.out.println("Level complete called");

    Gson gson = new Gson();
    com.knightlore.networking.game.LevelComplete lc = gson.fromJson(sendable.getData(), com.knightlore.networking.game.LevelComplete.class);

    Player player = handler.model().getPlayers().get(handler.sessionKey.get());
    player.setScore(lc.score);
    player.setPlayerState(PlayerState.FINISHED);

    // Check for all complete
    boolean allFinished = true;
    for(Player current: handler.model().getPlayers().values()){
      if(!(current.getPlayerState() == PlayerState.FINISHED || current.getPlayerState() == PlayerState.DEAD)){
        allFinished = false;
        break;
      }
    }

    if(allFinished){
      // Send out
//      Sendable update = new Sendable();
//      sendable.setFunction("position_update_chunk");
//      PositionUpdateChunk chunk = new PositionUpdateChunk();
//      chunk.add(new PositionUpdate(player.getPosition(), handler.sessionKey.get(), player.getDirection(), player.getPlayerState(), player.getScore()));
//      update.setData(gson.toJson(chunk));
//      handler.server().sendToRegisteredExceptSelf(update, handler.sessionKey.get());

      if(handler.model().lastLevel()){
        // Finish game
        Sendable gameComplete = new Sendable();
        handler.model().setState(GameState.FINISHED);
        gameComplete.setFunction("game_complete");
        handler.server().sendToRegistered(gameComplete);

        // Stop
        System.out.println("GAME COMPLETE");
        GameRepository.instance.removeServer(handler.server().getUUID());
      }else{
        Sendable levelComplete = new Sendable();
        levelComplete.setFunction("level_complete");
        handler.server().sendToRegistered(levelComplete);
        handler.model().nextLevel();
      }
    }

  }
}

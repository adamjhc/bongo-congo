package com.knightlore.server.supervisortasks;

import com.knightlore.game.server.GameServer;

/**
 * Template for supervisor task
 *
 * @author Lewis Relph
 */
public interface SupervisorTask {

    public void run(GameServer server);
}

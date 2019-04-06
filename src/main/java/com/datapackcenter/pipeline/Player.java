package com.datapackcenter.pipeline;

/*
* Player interface for information on players, criteria management etc.
*/


public interface Player {
    public boolean isPlayerOnline();
    //internal use, returns if player is online
    public String getPlayerUUID();
    //internal use, returns the UUID of the requested player
    public String getPlayerUsername();
    //internal use, returns the username of the requested player, due to changeability of usernames use should be avoided.
}

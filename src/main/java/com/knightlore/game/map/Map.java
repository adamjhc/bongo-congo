package com.knightlore.game.map;

public class Map {


    Tile[][][] tiles;

    int sizeX;
    int sizeY;
    int sizeZ;

    public Map(int sizeX, int sizeY, int sizeZ){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;

        // Build array List
        Tile[][][] currentX = new Tile[sizeX][][];

        for(int x = 0; x < sizeX; x++){
            Tile[][] currentY = new Tile[sizeY][];

            for(int y = 0; y < sizeY; y++){
                Tile[] currentZ = new Tile[sizeZ];

                for(int z = 0; z < sizeZ; z++){
                    Tile currentTile = new Tile();
                    currentTile.tileX = x;
                    currentTile.tileY = y;
                    currentTile.tileZ = z;
                    currentZ[z] = currentTile;
                }

                currentY[y] = currentZ;
            }

            currentX[x] = currentY;
        }

        this.tiles = currentX;
    }

    public void setTiles(Tile[][][] tiles) {
        this.tiles = tiles;
    }

    public void setTile(Location location, Tile tile){
        // TODO sanity location check
        this.tiles[location.x - 1][location.y - 1][location.z] = tile;
    }

    public Tile getTile(Location location){
        // TODO sanity location check
        return this.tiles[location.x + 1][location.y + 1][location.z];
    }

}

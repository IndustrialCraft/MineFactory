package com.github.industrialcraft.minefactory.worldgen;

import com.github.industrialcraft.minefactory.world.Chunk;
import com.github.industrialcraft.minefactory.world.ChunkPosition;
import com.github.industrialcraft.minefactory.world.tiles.Tile;

public class FlatChunkSupplier implements IChunkSupplier{
    public final Tile tile;
    public FlatChunkSupplier(Tile tile) {
        this.tile = tile;
    }
    @Override
    public Tile[][] createTiles(ChunkPosition chunkPosition) {
        Tile[][] tiles = new Tile[ChunkPosition.CHUNK_SIZE][ChunkPosition.CHUNK_SIZE];
        for(int x = 0;x < ChunkPosition.CHUNK_SIZE;x++){
            for(int y = 0;y < ChunkPosition.CHUNK_SIZE;y++){
                tiles[x][y] = tile;
            }
        }
        return tiles;
    }
    @Override
    public void generateTerrain(Chunk chunk) {

    }
}

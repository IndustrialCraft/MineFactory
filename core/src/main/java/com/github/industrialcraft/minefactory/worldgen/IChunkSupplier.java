package com.github.industrialcraft.minefactory.worldgen;

import com.github.industrialcraft.minefactory.world.Chunk;
import com.github.industrialcraft.minefactory.world.ChunkPosition;
import com.github.industrialcraft.minefactory.world.tiles.Tile;

public interface IChunkSupplier {
    Tile[][] createTiles(ChunkPosition chunkPosition);
    void generateTerrain(Chunk chunk);
}

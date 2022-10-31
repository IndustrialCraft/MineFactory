package com.github.industrialcraft.minefactory.worldgen;

import com.github.industrialcraft.inventorysystem.ItemStack;
import com.github.industrialcraft.minefactory.inventory.MFItem;
import com.github.industrialcraft.minefactory.world.Chunk;
import com.github.industrialcraft.minefactory.world.ChunkPosition;
import com.github.industrialcraft.minefactory.world.blocks.BlockDef;
import com.github.industrialcraft.minefactory.world.entities.ItemEntityDef;
import com.github.industrialcraft.minefactory.world.entities.WorldEntity;
import com.github.industrialcraft.minefactory.world.tiles.Tile;

import java.util.concurrent.ThreadLocalRandom;

public class RandomChunkSupplier implements IChunkSupplier{
    public final Tile[] randomTiles;
    public final int blockChance;
    public final BlockDef blockDef;
    public final MFItem item;
    public RandomChunkSupplier(int blockChance, BlockDef blockDef, MFItem item, Tile... randomTiles) {
        this.item = item;
        this.randomTiles = randomTiles;
        this.blockChance = blockChance;
        this.blockDef = blockDef;
    }
    @Override
    public Tile[][] createTiles(ChunkPosition chunkPosition) {
        Tile[][] tiles = new Tile[ChunkPosition.CHUNK_SIZE][ChunkPosition.CHUNK_SIZE];
        for(int x = 0;x < ChunkPosition.CHUNK_SIZE;x++){
            for(int y = 0;y < ChunkPosition.CHUNK_SIZE;y++){
                tiles[x][y] = randomTiles[ThreadLocalRandom.current().nextInt(randomTiles.length)];
            }
        }
        return tiles;
    }

    @Override
    public void generateTerrain(Chunk chunk) {
        for(int x = 0;x < ChunkPosition.CHUNK_SIZE;x++){
            for(int y = 0;y < ChunkPosition.CHUNK_SIZE;y++) {
                if(ThreadLocalRandom.current().nextInt(100) <= blockChance)
                    chunk.setBlock(chunk.chunkPosition.getPosAtOffset(x, y), blockDef);
            }
        }
        new WorldEntity(ItemEntityDef.ITEM_ENTITY_DEF, chunk.chunkPosition.getPosAtOffset(7, 7), chunk).inventory.addItem(new ItemStack(item, 1));
    }
}

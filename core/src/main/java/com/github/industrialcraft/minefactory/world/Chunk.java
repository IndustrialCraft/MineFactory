package com.github.industrialcraft.minefactory.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.industrialcraft.minefactory.util.PositionNotInsideChunkException;
import com.github.industrialcraft.minefactory.world.blocks.BlockDef;
import com.github.industrialcraft.minefactory.world.blocks.WorldBlock;
import com.github.industrialcraft.minefactory.world.entities.WorldEntity;
import com.github.industrialcraft.minefactory.world.tiles.Tile;
import com.github.industrialcraft.minefactory.worldgen.IChunkSupplier;

import java.util.*;

public class Chunk {
    public static final float TIME_TO_UNLOAD = 20;

    public final ChunkPosition chunkPosition;
    public final World world;
    private final Tile[][] tiles;
    private final WorldBlock[][] blocks;
    private final List<WorldEntity> entitiesToAdd;
    private final Set<WorldEntity> entities;
    private float unloadTimer;
    public Chunk(ChunkPosition chunkPosition, World world, IChunkSupplier chunkSupplier){
        this.chunkPosition = chunkPosition;
        this.world = world;
        this.tiles = chunkSupplier.createTiles(chunkPosition);
        this.blocks = new WorldBlock[ChunkPosition.CHUNK_SIZE][ChunkPosition.CHUNK_SIZE];
        this.entities = new HashSet<>();
        this.entitiesToAdd = new ArrayList<>();
        this.unloadTimer = TIME_TO_UNLOAD;
        chunkSupplier.generateTerrain(this);
    }
    public Set<WorldEntity> getAllEntities(){
        return Collections.unmodifiableSet(this.entities);
    }
    public WorldBlock setBlock(Position position, BlockDef blockDef){
        if(!position.toChunkPos().equals(chunkPosition))
            throw new PositionNotInsideChunkException();
        int xInChunk = position.getXInChunk();
        int yInChunk = position.getYInChunk();
        if(blockDef == null){
            blocks[xInChunk][yInChunk] = null;
            return null;
        } else {
            WorldBlock worldBlock = new WorldBlock(blockDef, position, this);
            blocks[xInChunk][yInChunk] = worldBlock;
            return worldBlock;
        }
    }
    public WorldBlock getBlock(Position position){
        if(!position.toChunkPos().equals(chunkPosition))
            throw new PositionNotInsideChunkException();
        return blocks[position.getXInChunk()][position.getYInChunk()];
    }
    public void resetUnloadTimer(){
        if(isUnloaded())
            return;
        this.unloadTimer = TIME_TO_UNLOAD;
    }
    public boolean isUnloaded(){
        return this.unloadTimer <= 0;
    }
    public void addEntity(WorldEntity entity){
        this.entitiesToAdd.add(entity);
    }
    public void tick(){
        this.unloadTimer -= Gdx.graphics.getDeltaTime();
        this.entities.addAll(entitiesToAdd);
        entitiesToAdd.clear();
        entities.removeIf(entity -> entity.isRemoved() || entity.getChunk() != this);
    }
    public void renderTiles(SpriteBatch batch){
        for(int x = 0;x < ChunkPosition.CHUNK_SIZE;x++){
            for(int y = 0;y < ChunkPosition.CHUNK_SIZE;y++){
                tiles[x][y].draw(batch, (chunkPosition.x*ChunkPosition.CHUNK_SIZE) + x, (chunkPosition.y*ChunkPosition.CHUNK_SIZE) + y);
            }
        }
    }
    public void renderBlocks(SpriteBatch batch){
        for(int x = 0;x < ChunkPosition.CHUNK_SIZE;x++){
            for(int y = 0;y < ChunkPosition.CHUNK_SIZE;y++){
                WorldBlock block = blocks[x][y];
                if(block != null)
                    block.blockDef.draw(batch, (chunkPosition.x*ChunkPosition.CHUNK_SIZE) + x, (chunkPosition.y*ChunkPosition.CHUNK_SIZE) + y);
            }
        }
    }
    public void renderEntities(SpriteBatch batch){
        for(WorldEntity entity : entities){
            entity.entityDef.render(batch, entity);
        }
    }
}

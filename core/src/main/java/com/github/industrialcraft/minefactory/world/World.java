package com.github.industrialcraft.minefactory.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.industrialcraft.minefactory.world.entities.WorldEntity;
import com.github.industrialcraft.minefactory.worldgen.IChunkSupplier;

import java.util.*;

public class World {
    private final Map<ChunkPosition,Chunk> chunks;
    private final IChunkSupplier chunkSupplier;
    public World(IChunkSupplier chunkSupplier){
        this.chunkSupplier = chunkSupplier;
        this.chunks = new HashMap<>();
    }
    public void tick(WorldEntity player){
        chunks.values().removeIf(chunk -> chunk.isUnloaded());
        chunks.values().stream().forEach(Chunk::tick);
        ChunkPosition playerChunk = player.getPosition().toChunkPos();
        for(int x = -5;x < 5;x++){
            for(int y = -5;y < 5;y++){
                load(new ChunkPosition(playerChunk.x+x,playerChunk.y+y)).resetUnloadTimer();
            }
        }
    }
    public Chunk load(ChunkPosition chunkPosition){
        Chunk chunk = chunks.get(chunkPosition);
        if(chunk != null)
            return chunk;
        Chunk newChunk = new Chunk(chunkPosition, this, chunkSupplier);
        this.chunks.put(chunkPosition, newChunk);
        return newChunk;
    }
    public Collection<Chunk> getAllChunks(){
        return Collections.unmodifiableCollection(chunks.values());
    }
    public Position getRespawnPosition(){
        return new Position(0, 0);
    }
    public Chunk getOrNull(ChunkPosition chunkPosition){
        return this.chunks.get(chunkPosition);
    }
    public void render(SpriteBatch batch){
        chunks.values().stream().forEach(chunk -> chunk.renderTiles(batch));
        chunks.values().stream().forEach(chunk -> chunk.renderBlocks(batch));
        chunks.values().stream().forEach(chunk -> chunk.renderEntities(batch));
    }
}

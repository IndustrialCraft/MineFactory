package com.github.industrialcraft.minefactory.world.blocks;

import com.github.industrialcraft.minefactory.util.PositionNotInsideChunkException;
import com.github.industrialcraft.minefactory.world.Chunk;
import com.github.industrialcraft.minefactory.world.Position;
import com.github.industrialcraft.minefactory.world.World;

public class WorldBlock {
    public final BlockDef blockDef;
    public final Position position;
    public final Chunk chunk;
    public WorldBlock(BlockDef blockDef, Position position, Chunk chunk) {
        this.blockDef = blockDef;
        this.position = position;
        this.chunk = chunk;
        if(!position.toChunkPos().equals(chunk.chunkPosition))
            throw new PositionNotInsideChunkException();
    }
    public boolean isPresent(){
        if(chunk.isUnloaded())
            return false;
        return chunk.getBlock(position) == this;
    }
}

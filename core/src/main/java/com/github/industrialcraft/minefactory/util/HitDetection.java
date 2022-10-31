package com.github.industrialcraft.minefactory.util;

import com.github.industrialcraft.minefactory.world.Chunk;
import com.github.industrialcraft.minefactory.world.Position;
import com.github.industrialcraft.minefactory.world.World;
import com.github.industrialcraft.minefactory.world.entities.WorldEntity;

public class HitDetection {
    public static Hit tryHit(Position hitPosition, World world){
        Position posRound = hitPosition.roundDown();
        for(Chunk chunk : world.getAllChunks()){
            for(WorldEntity entity : chunk.getAllEntities()){
                if(entity.isRemoved())
                    continue;
                if(entity.getCenterPosition().roundDown().equals(posRound))
                    return new EntityHit(entity);
            }
        }
        return new TileHit(posRound);
    }
    public static class Hit{

    }
    public static class TileHit extends Hit{
        public final Position tilePosition;
        public TileHit(Position tilePosition) {
            this.tilePosition = tilePosition;
        }
    }
    public static class EntityHit extends Hit{
        public final WorldEntity entity;
        public EntityHit(WorldEntity entity) {
            this.entity = entity;
        }
    }
}

package com.github.industrialcraft.minefactory.world;

import com.github.industrialcraft.minefactory.util.EDamageType;
import com.github.industrialcraft.minefactory.util.EDirection;

import java.util.Objects;

public class Position {
    public static final int PIXELS_PER_TILE = 32;

    public final float x;
    public final float y;
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Position withX(float x){
        return new Position(x, y);
    }
    public Position withY(float y){
        return new Position(x, y);
    }
    public Position add(float x, float y){
        return new Position(this.x + x, this.y + y);
    }
    public Position withOffset(EDirection direction){
        if(direction == null)
            return this;
        return this.add(direction.xOffset, direction.yOffset);
    }
    public ChunkPosition toChunkPos(){
        return new ChunkPosition((int) Math.floor(x/ChunkPosition.CHUNK_SIZE), (int) Math.floor(y/ChunkPosition.CHUNK_SIZE));
    }
    public int getXInChunk(){
        return (((int)x)%ChunkPosition.CHUNK_SIZE)+(x<0?ChunkPosition.CHUNK_SIZE-1:0);
    }
    public int getYInChunk(){
        return (((int)y)%ChunkPosition.CHUNK_SIZE)+(y<0?ChunkPosition.CHUNK_SIZE-1:0);
    }
    public Position roundDown(){
        return new Position((float) Math.floor(x), (float) Math.floor(y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Float.compare(position.x, x) == 0 && Float.compare(position.y, y) == 0;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

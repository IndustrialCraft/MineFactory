package com.github.industrialcraft.minefactory.world;

import java.util.Objects;

public class ChunkPosition {
    public static final int CHUNK_SIZE = 16;

    public final int x;
    public final int y;
    public ChunkPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Position getPosAtOffset(int x, int y){
        if(x < 0 || x > 15)
            throw new IllegalArgumentException("x must be in range 0..15");
        if(y < 0 || y > 15)
            throw new IllegalArgumentException("y must be in range 0..15");
        return new Position((this.x*CHUNK_SIZE)+x, (this.y*CHUNK_SIZE)+y);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChunkPosition that = (ChunkPosition) o;
        return x == that.x && y == that.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "ChunkPosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

package com.github.industrialcraft.minefactory.util;

public enum EDirection {
    UP(0, 1),
    LEFT(-1, 0),
    DOWN(0, -1),
    RIGHT(1, 0),
    UP_RIGHT(1, 1),
    DOWN_RIGHT(1, -1),
    DOWN_LEFT(-1, -1),
    UP_LEFT(-1, 1);
    public final int xOffset;
    public final int yOffset;
    EDirection(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    public static EDirection byOffsets(int xOffset, int yOffset){
        for(EDirection direction : values()){
            if(direction.xOffset == xOffset && direction.yOffset == yOffset)
                return direction;
        }
        return null;
    }
}

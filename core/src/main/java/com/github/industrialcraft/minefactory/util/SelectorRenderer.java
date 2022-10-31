package com.github.industrialcraft.minefactory.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.industrialcraft.minefactory.world.Position;
import com.github.industrialcraft.minefactory.world.entities.WorldEntity;

public class SelectorRenderer {
    public final TextureRegion selector;
    public final SpriteBatch batch;
    public SelectorRenderer(TextureRegion selector, SpriteBatch batch) {
        this.selector = selector;
        this.batch = batch;
    }
    public void render(Position tile, Color color){
        batch.setColor(color);
        batch.draw(selector, tile.x*Position.PIXELS_PER_TILE, tile.y*Position.PIXELS_PER_TILE);
        batch.setColor(1, 1, 1, 1);
    }
    public void render(WorldEntity entity, Color color){
        batch.setColor(color);
        batch.draw(selector, entity.getPosition().x*Position.PIXELS_PER_TILE, entity.getPosition().y*Position.PIXELS_PER_TILE, entity.entityDef.hitboxW*Position.PIXELS_PER_TILE, entity.entityDef.hitboxH*Position.PIXELS_PER_TILE);
        batch.setColor(1, 1, 1, 1);
    }
    public void render(HitDetection.Hit hit, Color color){
        if(hit instanceof HitDetection.TileHit tileHit){
            render(tileHit.tilePosition, color);
        }
        if(hit instanceof HitDetection.EntityHit entityHit){
            render(entityHit.entity, color);
        }
    }
}

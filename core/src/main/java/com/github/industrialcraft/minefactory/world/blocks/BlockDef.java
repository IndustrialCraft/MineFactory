package com.github.industrialcraft.minefactory.world.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.industrialcraft.identifier.Identifier;
import com.github.industrialcraft.minefactory.world.Position;
import com.google.gson.JsonObject;

import java.util.Objects;

public class BlockDef {
    public final Identifier identifier;
    public final TextureRegion texture;
    public BlockDef(Identifier identifier, TextureRegion texture) {
        this.identifier = identifier;
        this.texture = texture;
    }
    public BlockDef(JsonObject json){
        this.identifier = Identifier.parse(json.get("id").getAsString());
        this.texture = new TextureRegion(new Texture(json.get("texture").getAsString()));
    }
    public void draw(SpriteBatch batch, int x, int y){
        batch.draw(texture, x*Position.PIXELS_PER_TILE, y*Position.PIXELS_PER_TILE, Position.PIXELS_PER_TILE, Position.PIXELS_PER_TILE);
    }
}

package com.github.industrialcraft.minefactory.world.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.industrialcraft.identifier.Identifier;
import com.github.industrialcraft.minefactory.world.Position;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tile {
    public final Identifier identifier;
    public final List<TextureRegion> textures;
    public Tile(Identifier identifier, List<TextureRegion> textures) {
        this.identifier = identifier;
        this.textures = textures;
    }
    public Tile(JsonObject json){
        this.identifier = Identifier.parse(json.get("id").getAsString());
        this.textures = new ArrayList<>();
        for(JsonElement textureName : json.get("textures").getAsJsonArray()){
            textures.add(new TextureRegion(new Texture(textureName.getAsString())));
        }
    }
    public void draw(SpriteBatch batch, int x, int y){
        batch.draw(textures.get(Math.abs(Objects.hash(x, y)%textures.size())), x* Position.PIXELS_PER_TILE, y*Position.PIXELS_PER_TILE, Position.PIXELS_PER_TILE, Position.PIXELS_PER_TILE);
    }
}

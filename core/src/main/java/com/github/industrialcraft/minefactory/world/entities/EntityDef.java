package com.github.industrialcraft.minefactory.world.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.industrialcraft.identifier.Identifier;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class EntityDef {
    public final Identifier identifier;
    public final float maxHealth;
    public final boolean loadsTerrain;
    public final int inventorySize;
    public final float hitboxW;
    public final float hitboxH;
    public EntityDef(Identifier identifier, float maxHealth, boolean loadsTerrain, int inventorySize, float hitboxW, float hitboxH) {
        this.identifier = identifier;
        this.maxHealth = maxHealth;
        this.loadsTerrain = loadsTerrain;
        this.inventorySize = inventorySize;
        this.hitboxW = hitboxW;
        this.hitboxH = hitboxH;
    }
    public EntityDef(JsonObject json){
        this.identifier = Identifier.parse(json.get("id").getAsString());
        this.maxHealth = json.get("maxHealth").getAsFloat();
        this.hitboxW = json.get("hitboxW").getAsFloat();
        this.hitboxH = json.get("hitboxH").getAsFloat();
        JsonElement inventorySize = json.get("inventorySize");
        this.inventorySize = inventorySize!=null?inventorySize.getAsInt():0;
        this.loadsTerrain = false;
    }
    public void render(SpriteBatch batch, WorldEntity entity){

    }
    public void interact(WorldEntity entity, WorldEntity player){

    }
}

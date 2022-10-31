package com.github.industrialcraft.minefactory.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.industrialcraft.identifier.Identifier;
import com.github.industrialcraft.inventorysystem.IItem;
import com.github.industrialcraft.inventorysystem.ItemData;
import com.github.industrialcraft.inventorysystem.ItemStack;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MFItem implements IItem {
    public final Identifier identifier;
    public final String name;
    public final String description;
    public final int stackSize;
    public final int fuelValue;
    public final int maxDurability;
    public final int storageCapacity;
    public final EStorageType storageType;
    public final EToolType toolType;
    public final TextureRegion texture;
    public MFItem(Identifier identifier, String name, String description, int stackSize, int fuelValue, int maxDurability, int storageCapacity, EStorageType storageType, EToolType toolType, TextureRegion texture) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.stackSize = stackSize;
        this.fuelValue = fuelValue;
        this.maxDurability = maxDurability;
        this.storageCapacity = storageCapacity;
        this.storageType = storageType;
        this.toolType = toolType;
        this.texture = texture;
    }
    public MFItem(JsonObject json){
        this.identifier = Identifier.parse(json.get("id").getAsString());
        this.name = json.get("name").getAsString();
        this.description = json.get("description").getAsString();
        this.stackSize = json.get("stackSize").getAsInt();
        JsonElement fuelValue = json.get("fuelValue");
        this.fuelValue = fuelValue!=null?fuelValue.getAsInt():0;
        JsonElement maxDurabilityValue = json.get("maxDurability");
        this.maxDurability = maxDurabilityValue!=null?maxDurabilityValue.getAsInt():0;
        this.storageType = parseStorageType(json.get("storageType"));
        this.toolType = parseToolType(json.get("toolType"));
        if((toolType == null) != (maxDurability == 0))
            throw new IllegalStateException("Only tool must have durability");
        if(toolType != null && toolType != null)
            throw new IllegalStateException("item cannot be storage and tool at the same time");
        JsonElement storageCapacityValue = json.get("storageCapacity");
        this.storageCapacity = storageCapacityValue!=null?storageCapacityValue.getAsInt():0;
        if((storageType == null) != (storageCapacity == 0))
            throw new IllegalStateException("Only storage item must have capacity");
        this.texture = new TextureRegion(new Texture(json.get("texture").getAsString()));
    }
    public TextureRegion getTexture(){
        return texture;
    }
    public EStorageType parseStorageType(JsonElement json){
        if(json == null)
            return null;
        return EStorageType.valueOf(json.getAsString().toUpperCase());
    }
    public EToolType parseToolType(JsonElement json){
        if(json == null)
            return null;
        return EToolType.valueOf(json.getAsString().toUpperCase());
    }

    @Override
    public int getStackSize() {
        return stackSize;
    }
    @Override
    public ItemData createData(ItemStack is) {
        if(toolType != null)
            return new ToolItemData(is, this);
        return IItem.super.createData(is);
    }
}

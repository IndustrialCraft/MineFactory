package com.github.industrialcraft.minefactory.inventory;

import com.badlogic.gdx.files.FileHandle;
import com.github.industrialcraft.identifier.Identifier;
import com.github.industrialcraft.minefactory.world.tiles.Tile;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRegistry {
    private final Map<Identifier,MFItem> items;
    public ItemRegistry() {
        this.items = new HashMap<>();
    }
    public boolean loadFromFolder(FileHandle handle){
        List<MFItem> parsedItems = new ArrayList<>();
        boolean failed = false;
        for(FileHandle item : handle.list()){
            try {
                MFItem loadedItem = new MFItem((JsonObject) JsonParser.parseReader(item.reader()));
                parsedItems.add(loadedItem);
            } catch (Exception e){
                e.printStackTrace();
                failed = true;
            }
        }
        if(failed)
            return false;
        for(MFItem item : parsedItems){
            if(items.containsKey(item.identifier)){
                System.out.println("item with id " + item.identifier + " already registered");
                continue;
            }
            items.put(item.identifier, item);
        }
        return true;
    }
    public MFItem get(Identifier identifier){
        return items.get(identifier);
    }
}

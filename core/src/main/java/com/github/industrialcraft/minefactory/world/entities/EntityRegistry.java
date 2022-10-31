package com.github.industrialcraft.minefactory.world.entities;

import com.badlogic.gdx.files.FileHandle;
import com.github.industrialcraft.identifier.Identifier;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityRegistry {
    private final Map<Identifier, EntityDef> entities;
    public EntityRegistry() {
        this.entities = new HashMap<>();
    }
    public boolean loadFromFolder(FileHandle handle){
        List<EntityDef> parsedEntities = new ArrayList<>();
        boolean failed = false;
        for(FileHandle entity : handle.list()){
            try {
                EntityDef loadedEntity = new EntityDef((JsonObject) JsonParser.parseReader(entity.reader()));
                parsedEntities.add(loadedEntity);
            } catch (Exception e){
                e.printStackTrace();
                failed = true;
            }
        }
        if(failed)
            return false;
        for(EntityDef entity : parsedEntities){
            register(entity);
        }
        return true;
    }
    public void register(EntityDef entity){
        if(entities.containsKey(entity.identifier)){
            System.out.println("entity with id " + entity.identifier + " already registered");
            return;
        }
        entities.put(entity.identifier, entity);
    }
}

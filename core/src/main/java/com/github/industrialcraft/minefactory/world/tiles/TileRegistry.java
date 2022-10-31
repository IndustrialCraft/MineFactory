package com.github.industrialcraft.minefactory.world.tiles;

import com.badlogic.gdx.files.FileHandle;
import com.github.industrialcraft.identifier.Identifier;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileRegistry {
    private final Map<Identifier, Tile> tiles;
    public TileRegistry() {
        this.tiles = new HashMap<>();
    }
    public boolean loadFromFolder(FileHandle handle){
        List<Tile> parsedTiles = new ArrayList<>();
        boolean failed = false;
        for(FileHandle tile : handle.list()){
            try {
                Tile loadedTile = new Tile((JsonObject) JsonParser.parseReader(tile.reader()));
                parsedTiles.add(loadedTile);
            } catch (Exception e){
                e.printStackTrace();
                failed = true;
            }
        }
        if(failed)
            return false;
        for(Tile tile : parsedTiles){
            if(tiles.containsKey(tile.identifier)){
                System.out.println("tile with id " + tile.identifier + " already registered");
                continue;
            }
            tiles.put(tile.identifier, tile);
        }
        return true;
    }
    public Tile get(Identifier identifier){
        return tiles.get(identifier);
    }
}

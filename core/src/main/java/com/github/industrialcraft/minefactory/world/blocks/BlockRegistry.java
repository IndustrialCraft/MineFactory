package com.github.industrialcraft.minefactory.world.blocks;

import com.badlogic.gdx.files.FileHandle;
import com.github.industrialcraft.identifier.Identifier;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockRegistry {
    private final Map<Identifier, BlockDef> blocks;
    public BlockRegistry() {
        this.blocks = new HashMap<>();
    }
    public boolean loadFromFolder(FileHandle handle){
        List<BlockDef> parsedBlocks = new ArrayList<>();
        boolean failed = false;
        for(FileHandle block : handle.list()){
            try {
                BlockDef loadedBlock = new BlockDef((JsonObject) JsonParser.parseReader(block.reader()));
                parsedBlocks.add(loadedBlock);
            } catch (Exception e){
                e.printStackTrace();
                failed = true;
            }
        }
        if(failed)
            return false;
        for(BlockDef block : parsedBlocks){
            if(blocks.containsKey(block.identifier)){
                System.out.println("block with id " + block.identifier + " already registered");
                continue;
            }
            blocks.put(block.identifier, block);
        }
        return true;
    }
    public BlockDef get(Identifier identifier){
        return blocks.get(identifier);
    }
}

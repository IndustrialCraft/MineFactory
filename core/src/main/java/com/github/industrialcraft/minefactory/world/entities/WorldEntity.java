package com.github.industrialcraft.minefactory.world.entities;

import com.github.industrialcraft.inventorysystem.Inventory;
import com.github.industrialcraft.inventorysystem.ItemStack;
import com.github.industrialcraft.minefactory.util.EDamageType;
import com.github.industrialcraft.minefactory.util.EHealingType;
import com.github.industrialcraft.minefactory.util.PositionNotInsideChunkException;
import com.github.industrialcraft.minefactory.world.Chunk;
import com.github.industrialcraft.minefactory.world.Position;
import com.github.industrialcraft.minefactory.world.World;

import java.util.UUID;

public class WorldEntity {
    public final UUID id;
    public final EntityDef entityDef;
    public final Inventory inventory;
    private Position position;
    private Chunk chunk;
    private boolean removed;
    private float health;
    private int handSlot;
    public WorldEntity(EntityDef entityDef, Position position, World world) {
        this.id = UUID.randomUUID();
        this.entityDef = entityDef;
        this.removed = false;
        this.health = entityDef.maxHealth;
        this.inventory = entityDef.inventorySize==0?null:new Inventory(entityDef.inventorySize, (inventory1, is) -> {
            new WorldEntity(ItemEntityDef.ITEM_ENTITY_DEF, getPosition(), this.chunk).inventory.addItem(is);
        }, this);
        if(!teleport(position, world))
            remove();
        this.handSlot = 0;
    }
    public WorldEntity(EntityDef entityDef, Position position, Chunk chunk) {
        this.id = UUID.randomUUID();
        this.entityDef = entityDef;
        this.removed = false;
        this.health = entityDef.maxHealth;
        this.inventory = entityDef.inventorySize==0?null:new Inventory(entityDef.inventorySize, (inventory1, is) -> {
            new WorldEntity(ItemEntityDef.ITEM_ENTITY_DEF, getPosition(), this.chunk).inventory.addItem(is);
        }, this);
        if(!teleport(position, chunk))
            remove();
        this.handSlot = 0;
    }
    public int getHandSlot() {
        return handSlot;
    }
    public void setHandSlot(int handSlot) {
        if(inventory == null)
            return;
        this.handSlot = (handSlot%inventory.getSize())+(handSlot<0?inventory.getSize():0);
    }
    public ItemStack getHandItem(){
        if(inventory == null)
            return null;
        return inventory.getAt(handSlot);
    }
    public Position getCenterPosition(){
        return getPosition().add(entityDef.hitboxW/2f, entityDef.hitboxH/2f);
    }
    public Position getPosition() {
        return position;
    }
    public Chunk getChunk() {
        return chunk;
    }
    public float getHealth() {
        return health;
    }
    public float damage(float damage, EDamageType damageType){
        if(damage <= 0)
            return 0;
        float damageToTake = damage*1;
        this.health -= damageToTake;
        if(this.health < 0){
            this.health = 0;
            remove();
        }
        return damageToTake;
    }
    public float heal(float healAmt, EHealingType healingType){
        if(removed)
            return 0;
        if(healAmt <= 0)
            return 0;
        float amountToHeal = healAmt*1;
        this.health += amountToHeal;
        if(this.health > entityDef.maxHealth)
            this.health = entityDef.maxHealth;
        return amountToHeal;
    }
    public boolean teleport(Position position, Chunk chunk){
        if(!position.toChunkPos().equals(chunk.chunkPosition))
            throw new PositionNotInsideChunkException();
        if(removed)
            return false;
        if(chunk == null)
            return false;
        this.position = position;
        this.chunk = chunk;
        chunk.addEntity(this);
        return true;
    }
    public boolean teleport(Position position, World world){
        if(removed)
            return false;
        Chunk chunk = entityDef.loadsTerrain?world.load(position.toChunkPos()):world.getOrNull(position.toChunkPos());
        if(chunk == null)
            return false;
        this.position = position;
        this.chunk = chunk;
        chunk.addEntity(this);
        return true;
    }
    public boolean teleport(Position position){
        if(removed)
            return false;
        Chunk chunk = entityDef.loadsTerrain?this.chunk.world.load(position.toChunkPos()):this.chunk.world.getOrNull(position.toChunkPos());
        if(chunk == null)
            return false;
        this.position = position;
        this.chunk = chunk;
        chunk.addEntity(this);
        return true;
    }
    public boolean isRemoved() {
        return removed;
    }
    public void remove() {
        if(entityDef instanceof PlayerEntityDef){
            this.health = entityDef.maxHealth;
            inventory.dropAll();
            teleport(chunk.world.getRespawnPosition());
            return;
        }
        this.removed = true;
    }
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

package com.github.industrialcraft.minefactory.world.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.industrialcraft.identifier.Identifier;
import com.github.industrialcraft.inventorysystem.ItemStack;
import com.github.industrialcraft.minefactory.inventory.MFItem;
import com.github.industrialcraft.minefactory.world.Position;

public class ItemEntityDef extends EntityDef{
    public static final ItemEntityDef ITEM_ENTITY_DEF = new ItemEntityDef();
    private ItemEntityDef() {
        super(Identifier.of("mf", "item"), 10, false, 1, 0.5f, 0.5f);
    }

    @Override
    public void render(SpriteBatch batch, WorldEntity entity) {
        ItemStack is = entity.inventory.getAt(0);
        if(is == null){
            entity.remove();
            return;
        }
        batch.draw(((MFItem)is.getItem()).getTexture(), entity.getPosition().x*Position.PIXELS_PER_TILE, entity.getPosition().y*Position.PIXELS_PER_TILE, 0.5f*Position.PIXELS_PER_TILE, 0.5f*Position.PIXELS_PER_TILE);
    }
    @Override
    public void interact(WorldEntity entity, WorldEntity player) {
        player.inventory.addItem(entity.inventory.getAt(0));
        entity.inventory.clear();
    }
}

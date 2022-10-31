package com.github.industrialcraft.minefactory.inventory;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.industrialcraft.inventorysystem.Inventory;
import com.github.industrialcraft.inventorysystem.ItemStack;

public class InventoryRenderer {
    public static final int SLOT_SIZE = 60;
    public static final int ITEM_COUNT_OFFSET = 7;

    public final SpriteBatch batch;
    public final TextureRegion slotTexture;
    public final TextureRegion selectedSlotTexture;
    public final BitmapFont font;
    public final GlyphLayout glyphLayout;
    public InventoryRenderer(SpriteBatch batch, TextureRegion slotTexture, TextureRegion selectedSlotTexture, BitmapFont font) {
        this.batch = batch;
        this.slotTexture = slotTexture;
        this.selectedSlotTexture = selectedSlotTexture;
        this.font = font;
        this.glyphLayout = new GlyphLayout();
    }
    public void render(Inventory inventory, int x, int y, int slotsPerRow, int selectedSlot){
        for(int i = 0;i < inventory.getSize();i++){
            int slotX = ((i%slotsPerRow)*SLOT_SIZE) + x;
            int slotY = ((i/slotsPerRow)*SLOT_SIZE) + y;
            if(i == selectedSlot)
                batch.draw(selectedSlotTexture, slotX, slotY, SLOT_SIZE, SLOT_SIZE);
            else
                batch.draw(slotTexture, slotX, slotY, SLOT_SIZE, SLOT_SIZE);
            ItemStack stack = inventory.getAt(i);
            if(stack != null){
                MFItem item = (MFItem) stack.getItem();
                batch.draw(item.getTexture(), slotX, slotY, SLOT_SIZE, SLOT_SIZE);
                if(stack.getCount() > 1) {
                    String itemCountText = ""+stack.getCount();
                    glyphLayout.setText(font, itemCountText);
                    font.draw(batch, itemCountText, slotX + (SLOT_SIZE - glyphLayout.width) - ITEM_COUNT_OFFSET, slotY + glyphLayout.height + ITEM_COUNT_OFFSET);
                }
            }
        }
    }
}

package com.github.industrialcraft.minefactory.inventory;

import com.github.industrialcraft.inventorysystem.ItemData;
import com.github.industrialcraft.inventorysystem.ItemStack;

public class ToolItemData extends ItemData {
    private final MFItem item;
    private int durability;
    public ToolItemData(ItemStack is, MFItem item) {
        super(is);
        this.item = item;
        this.durability = item.maxDurability;
    }
    public EToolType getToolType(){
        return item.toolType;
    }
    public int getDurability() {
        return durability;
    }
    public void setDurability(int durability) {
        if(this.durability < 0)
            return;
        if(durability > item.maxDurability)
            durability = item.maxDurability;
        if(durability < 0) {
            durability = 0;
            getItemStack().setCount(0);
        }
        this.durability = durability;
    }
}

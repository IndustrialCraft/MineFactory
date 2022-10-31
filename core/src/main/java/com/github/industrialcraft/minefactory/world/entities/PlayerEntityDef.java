package com.github.industrialcraft.minefactory.world.entities;

import com.github.industrialcraft.identifier.Identifier;

public class PlayerEntityDef extends EntityDef{
    public static final PlayerEntityDef PLAYER_ENTITY_DEF = new PlayerEntityDef();
    private PlayerEntityDef() {
        super(Identifier.of("mf", "player"), 100, true, 9, 0.5f, 0.5f);
    }
}

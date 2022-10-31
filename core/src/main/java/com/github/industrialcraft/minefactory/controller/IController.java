package com.github.industrialcraft.minefactory.controller;

import com.badlogic.gdx.InputProcessor;
import com.github.industrialcraft.minefactory.MineFactoryMain;
import com.github.industrialcraft.minefactory.util.EDirection;
import com.github.industrialcraft.minefactory.world.Position;
import com.github.industrialcraft.minefactory.world.entities.WorldEntity;

public interface IController extends InputProcessor {
    float getHorizontal();
    float getVertical();
    Position getSelectedTile(MineFactoryMain main);
    boolean isADown();
    boolean isBDown();
    int getSlot(int currentSlot);
    void render();
    @Override
    default boolean keyDown(int keycode) {
        return false;
    }
    @Override
    default boolean keyUp(int keycode) {
        return false;
    }
    @Override
    default boolean keyTyped(char character) {
        return false;
    }
    @Override
    default boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    default boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    default boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    @Override
    default boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override
    default boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

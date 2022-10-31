package com.github.industrialcraft.minefactory.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.github.industrialcraft.minefactory.MineFactoryMain;
import com.github.industrialcraft.minefactory.util.EDirection;
import com.github.industrialcraft.minefactory.world.Position;
import com.github.industrialcraft.minefactory.world.entities.WorldEntity;

import java.util.Map;
import java.util.regex.Matcher;

public class KeyboardController implements IController{
    private static final int SCROLL_SENSITIVITY = 1;
    //private EDirection lastHeading;
    private int scrollX;
    private int scrollY;
    public KeyboardController() {
        //this.lastHeading = EDirection.UP;
    }
    @Override
    public float getHorizontal() {
        return (Gdx.input.isKeyPressed(Input.Keys.D)?1:0)-(Gdx.input.isKeyPressed(Input.Keys.A)?1:0);
    }
    @Override
    public float getVertical() {
        return (Gdx.input.isKeyPressed(Input.Keys.W)?1:0)-(Gdx.input.isKeyPressed(Input.Keys.S)?1:0);
    }
    @Override
    public Position getSelectedTile(MineFactoryMain main) {
        Vector3 pos = main.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Position(pos.x/32, pos.y/32);
    }
    @Override
    public boolean isADown() {
        return Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
    }
    @Override
    public boolean isBDown() {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }

    @Override
    public int getSlot(int currentSlot) {
        int amtScrolled = scrollY/SCROLL_SENSITIVITY;
        scrollY -= amtScrolled*SCROLL_SENSITIVITY;
        return currentSlot+amtScrolled;
    }
    @Override
    public boolean scrolled(float amountX, float amountY) {
        this.scrollX += amountX;
        this.scrollY += amountY;
        return true;
    }
    @Override
    public void render() {
        /*int horizontalInteger = Float.compare(getHorizontal(), 0);
        int verticalInteger = Float.compare(getVertical(), 0);
        EDirection direction = EDirection.byOffsets(horizontalInteger, verticalInteger);
        if(direction != null)
            this.lastHeading = direction;*/
    }

}

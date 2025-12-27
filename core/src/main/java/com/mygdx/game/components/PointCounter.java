package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PointCounter {
    private BitmapFont font;
    private float x, y;

    public PointCounter(float x, float y) {
        this.x = x;
        this.y = y;

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(2.5f);
    }

    public void draw(SpriteBatch batch, int points) {
        font.draw(batch, "Score: " + points, x, y);
    }

    public void dispose() { font.dispose(); }
}

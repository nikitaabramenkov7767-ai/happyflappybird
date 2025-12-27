package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.MyGdxGame;

public class MovingBackground {
    Texture texture;
    float texture1X, texture2X;
    float speed = 2.5f;

    public MovingBackground() {
        texture1X = 0;
        texture2X = MyGdxGame.VIRTUAL_WIDTH;
        texture = new Texture("game_bg.png");
    }

    public void move() {
        texture1X -= speed;
        texture2X -= speed;

        if (texture1X <= -MyGdxGame.VIRTUAL_WIDTH) {
            texture1X = texture2X + MyGdxGame.VIRTUAL_WIDTH;
        }
        if (texture2X <= -MyGdxGame.VIRTUAL_WIDTH) {
            texture2X = texture1X + MyGdxGame.VIRTUAL_WIDTH;
        }
    }

    public void draw(Batch batch) {
        batch.draw(texture, texture1X, 0,
            MyGdxGame.VIRTUAL_WIDTH + 2,
            MyGdxGame.VIRTUAL_HEIGHT);
        batch.draw(texture, texture2X, 0,
            MyGdxGame.VIRTUAL_WIDTH + 2,
            MyGdxGame.VIRTUAL_HEIGHT);
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}

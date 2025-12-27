package com.mygdx.game.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

public class Bird {
    private Texture[] frames;
    private int currentFrame = 0;
    private float animationTimer = 0;

    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;

    private final float BIRD_WIDTH = 220f;
    private final float BIRD_HEIGHT = 160f;

    public Bird(int x, int y) {
        frames = new Texture[3];
        frames[0] = new Texture("birdTiles/bird0.png");
        frames[1] = new Texture("birdTiles/bird1.png");
        frames[2] = new Texture("birdTiles/bird2.png");

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        bounds = new Rectangle(x, y, BIRD_WIDTH, BIRD_HEIGHT);
    }

    public void fly(float delta) {
        animationTimer += delta;
        if (animationTimer > 0.1f) {
            currentFrame = (currentFrame + 1) % 3;
            animationTimer = 0;
        }

        if (position.y > 0) velocity.add(0, -25);
        velocity.scl(delta);
        position.add(0, velocity.y);

        if (position.y < 0) position.y = 0;

        velocity.scl(1 / delta);
        bounds.setPosition(position.x, position.y);
    }

    public void onClick() {
        velocity.y = 550;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(frames[currentFrame], position.x, position.y, BIRD_WIDTH, BIRD_HEIGHT);
    }

    public boolean isInField() {
        return position.y > 0 && position.y < MyGdxGame.VIRTUAL_HEIGHT;
    }

    public float getX() { return position.x; }
    public float getY() { return position.y; }
    public float getWidth() { return BIRD_WIDTH; }
    public float getHeight() { return BIRD_HEIGHT; }
    public Rectangle getBounds() { return bounds; }

    public void dispose() {
        for (Texture t : frames) {
            if (t != null) t.dispose();
        }
    }
}

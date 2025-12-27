package com.mygdx.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import java.util.Random;

public class Tube {
    Texture textureUpperTube;
    Texture textureDownTube;

    float width = 200;
    float height = 700;

    float x;
    float upperTubeY;
    float downTubeY;

    float gapY;
    float gapHeight = 400;

    float distanceBetweenTubes;
    boolean isPointReceived;
    Random random;

    static final float SCR_WIDTH = MyGdxGame.VIRTUAL_WIDTH;
    static final float SCR_HEIGHT = MyGdxGame.VIRTUAL_HEIGHT;
    static final int padding = 100;

    public Tube(int tubeCount, int tubeIdx) {
        random = new Random();
        recalculateGapPosition();

        distanceBetweenTubes = (SCR_WIDTH + width) / (tubeCount - 1);
        x = distanceBetweenTubes * tubeIdx + SCR_WIDTH;

        isPointReceived = false;
        textureUpperTube = new Texture("tubes/tube_flipped.png");
        textureDownTube = new Texture("tubes/tube.png");
    }

    private void recalculateGapPosition() {
        gapY = gapHeight / 2 + padding +
            random.nextInt((int)(SCR_HEIGHT - 2 * (padding + gapHeight / 2)));

        upperTubeY = gapY + gapHeight / 2;
        downTubeY = gapY - gapHeight / 2 - height;
    }

    public void move(float speedModifier) {
        x -= 5f * speedModifier;

        if (x < -width) {
            isPointReceived = false;
            x += distanceBetweenTubes * 4;
            recalculateGapPosition();
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(textureUpperTube, x, upperTubeY, width, height);
        batch.draw(textureDownTube, x, downTubeY, width, height);
    }

    public boolean isHit(Bird bird) {
        if (bird.getY() <= gapY - gapHeight / 2 &&
            bird.getX() + bird.getWidth() >= x &&
            bird.getX() <= x + width)
            return true;

        if (bird.getY() + bird.getHeight() >= gapY + gapHeight / 2 &&
            bird.getX() + bird.getWidth() >= x &&
            bird.getX() <= x + width)
            return true;

        return false;
    }

    public boolean needAddPoint(Bird bird) {
        return bird.getX() > x + width && !isPointReceived;
    }

    public void setPointReceived() {
        isPointReceived = true;
    }

    public float getX() { return x; }
    public void dispose() {
        if (textureUpperTube != null) textureUpperTube.dispose();
        if (textureDownTube != null) textureDownTube.dispose();
    }
}

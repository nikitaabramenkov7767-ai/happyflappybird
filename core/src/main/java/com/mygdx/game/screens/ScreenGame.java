package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.characters.Bird;
import com.mygdx.game.characters.Tube;

public class ScreenGame implements Screen {
    private final MyGdxGame game;
    private Bird bird;
    private Array<Tube> tubes;
    private Texture background;
    private BitmapFont font;
    private int score;

    private float gameTimer = 0;
    private float speedModifier = 1.0f;

    private static final int TUBE_COUNT = 4;

    public ScreenGame(MyGdxGame game) {
        this.game = game;
        this.background = new Texture("game_bg.png");
        this.bird = new Bird(100, (int)MyGdxGame.VIRTUAL_HEIGHT / 2);
        this.tubes = new Array<Tube>();
        this.score = 0;

        this.font = new BitmapFont();
        this.font.getData().setScale(3f);

        for (int i = 0; i < TUBE_COUNT; i++) {
            tubes.add(new Tube(TUBE_COUNT, i));
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);

        for (Tube tube : tubes) {
            tube.draw(game.batch);
        }

        bird.draw(game.batch);
        font.draw(game.batch, "Score: " + score, MyGdxGame.VIRTUAL_WIDTH - 250, MyGdxGame.VIRTUAL_HEIGHT - 50);
        game.batch.end();
    }

    private void update(float delta) {
        if (Gdx.input.justTouched()) bird.onClick();
        bird.fly(delta);

        gameTimer += delta;
        if (gameTimer > 5f) {
            speedModifier += 0.15f;
            gameTimer = 0;
            if (speedModifier > 3.0f) speedModifier = 3.0f;
        }

        for (Tube tube : tubes) {
            tube.move(speedModifier);

            if (tube.needAddPoint(bird)) {
                score++;
                tube.setPointReceived();
            }

            if (tube.isHit(bird)) {
                game.setScreen(new ScreenRestart(game, score));
            }
        }

        if (!bird.isInField()) {
            game.setScreen(new ScreenRestart(game, score));
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        font.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) { game.viewport.update(width, height); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}

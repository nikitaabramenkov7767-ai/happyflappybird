package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

public class ScreenMenu implements Screen {
    private MyGdxGame game;
    private Stage stage;
    private Skin skin;
    private Texture background;

    public ScreenMenu(final MyGdxGame game) {
        this.game = game;
        this.background = new Texture("restart_bg.png");

        this.stage = new Stage(game.viewport, game.batch);

        createSimpleTextSkin();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton btnStart = new TextButton("START GAME", skin);
        btnStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenGame(game));
            }
        });

        TextButton btnExit = new TextButton("EXIT", skin);
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(btnStart).padBottom(50);
        table.row();
        table.add(btnExit);
    }

    private void createSimpleTextSkin() {
        skin = new Skin();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(4f); // Размер шрифта
        skin.add("default-font", font);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default-font");

        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.downFontColor = Color.GRAY;

        skin.add("default", textButtonStyle);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        background.dispose();
    }

    @Override public void pause() {}
    @Override public void resume() {}
}

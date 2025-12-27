package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

public class ScreenRestart implements Screen {
    private MyGdxGame game;
    private Stage stage;
    private Skin skin;
    private Texture background;

    public ScreenRestart(final MyGdxGame game, int score) {
        this.game = game;
        this.background = new Texture("restart_bg.png");
        this.stage = new Stage(game.viewport, game.batch);

        Gdx.input.setInputProcessor(stage);

        Preferences prefs = Gdx.app.getPreferences("FlappySettings");
        int highScore = prefs.getInteger("highscore", 0);
        if (score > highScore) {
            prefs.putInteger("highscore", score);
            prefs.flush();
            highScore = score;
        }

        skin = new Skin();
        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(1, 1, 1, 0));
        pixmap.fill();
        skin.add("pixel", new Texture(pixmap));
        pixmap.dispose();

        Label.LabelStyle titleStyle = new Label.LabelStyle(font, Color.WHITE);

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = font;
        btnStyle.fontColor = Color.WHITE;
        btnStyle.downFontColor = Color.YELLOW;
        btnStyle.up = skin.newDrawable("pixel");

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label labelOver = new Label("GAME OVER", titleStyle);
        labelOver.setFontScale(5f);

        Label labelScore = new Label("SCORE: " + score, titleStyle);
        labelScore.setFontScale(3f);

        Label labelHigh = new Label("BEST SCORE: " + highScore, titleStyle);
        labelHigh.setFontScale(3f);

        TextButton btnRestart = new TextButton("RESTART", btnStyle);
        btnRestart.getLabel().setFontScale(3f);
        btnRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenGame(game));
            }
        });

        TextButton btnMenu = new TextButton("MAIN MENU", btnStyle);
        btnMenu.getLabel().setFontScale(3f);
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenMenu(game));
            }
        });

        table.add(labelOver).padBottom(40);
        table.row();
        table.add(labelScore).padBottom(10);
        table.row();
        table.add(labelHigh).padBottom(100);
        table.row();

        table.add(btnRestart).padBottom(0).width(450).height(90);
        table.row();

        table.add(btnMenu).width(450).height(90);
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

    @Override public void show() { Gdx.input.setInputProcessor(stage); }
    @Override public void hide() { Gdx.input.setInputProcessor(null); }
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void dispose() { stage.dispose(); background.dispose(); skin.dispose(); }
    @Override public void pause() {}
    @Override public void resume() {}
}

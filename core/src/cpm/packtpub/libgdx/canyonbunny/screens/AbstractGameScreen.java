package cpm.packtpub.libgdx.canyonbunny.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

import cpm.packtpub.libgdx.canyonbunny.game.Assets;

/**
 * Created by Felix on 01.08.2015.
 */
public abstract class AbstractGameScreen implements Screen {
    protected Game game;

    public AbstractGameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }
}